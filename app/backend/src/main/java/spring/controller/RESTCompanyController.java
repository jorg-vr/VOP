package spring.controller;

import controller.CustomerController;
import dao.interfaces.CustomerDAO;
import dao.interfaces.DataAccessException;
import dao.interfaces.Filter;
import dao.interfaces.VehicleDAO;
import model.fleet.Vehicle;
import model.identity.Address;
import model.identity.Customer;
import org.springframework.web.bind.annotation.*;
import spring.Exceptions.InvalidInputException;
import spring.Exceptions.NotFoundException;
import spring.model.RESTAddress;
import spring.model.RESTCompany;
import spring.model.RESTVehicle;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.UUID;

/**
 * Created by jorg on 3/6/17.
 */
@RestController
@RequestMapping("/companies")
public class RESTCompanyController {

    private CustomerController controller=new CustomerController();
    /***
     * Not yet implemented
     * @return
     */
    @RequestMapping(method = RequestMethod.GET)
    public Collection<RESTCompany> getAllCompanies(@RequestParam(required=false) String nameContains,
                                                  @RequestParam(required=false) String country,
                                                  @RequestParam(required=false) String city,
                                                  @RequestParam(required=false) String postalCode) {

        CustomerDAO customerDAO= (CustomerDAO) controller.getDao(); //TODO get rid of cast
        List<Filter> filters=new ArrayList<>();
        if (nameContains!=null){filters.add(customerDAO.byName(nameContains));}
        filters.add(customerDAO.byAddress(new Address(null,null,city,postalCode,country)));
        Collection<RESTCompany> result=new ArrayList<>();
        try {
            for(Customer customer : controller.getAll( filters.toArray(new Filter[filters.size()]))){
                result.add(modelToRESTCompany(customer));
            }

        } catch (DataAccessException e) {
            //API doesn't contain error
        }
        return result;
    }

    @RequestMapping(method = RequestMethod.POST)
    public void postCompanies(@RequestBody RESTCompany restCompany) {
        try {
            controller.create(RESTToModelAddress(restCompany.getAddress()),
                    restCompany.getPhoneNumber(),
                    restCompany.getName(),
                    restCompany.getVatNumber());
        } catch (DataAccessException e) {
            throw new InvalidInputException(e);
        }
    }

    @RequestMapping(method = RequestMethod.GET , value = "{id}")
    public RESTCompany getCompany(@PathVariable("id") String id) {

        try {
            return modelToRESTCompany(controller.get(UUID.fromString(id)));

        } catch (DataAccessException e) {
            throw new NotFoundException();
        }
    }

    private RESTCompany modelToRESTCompany(Customer customer){
        return new RESTCompany(customer.getUuid().toString(),
                customer.getName(),
                customer.getBtwNumber(),
                customer.getPhoneNumber(),
                modelToRESTAddress(customer.getAddress()),
                null,
                null,
                null,
                "/companies/"+ customer.getUuid().toString());
    }

    private RESTAddress modelToRESTAddress(Address address){
        return new RESTAddress(address.getCountry(),
                address.getTown(),
                address.getStreet(),
                address.getStreetNumber(),
                address.getPostalCode());
    }

    private  Address RESTToModelAddress(RESTAddress restAddress){
        return new Address(restAddress.getStreet(),
                restAddress.getHouseNumber(),
                restAddress.getCity(),
                restAddress.getPostalCode(),
                restAddress.getCountry());
    }
}
