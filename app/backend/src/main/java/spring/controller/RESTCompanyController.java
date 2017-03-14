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
import spring.model.RESTSchema;
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

    public static final String PATH_COMPANY = "/companies";

    private CustomerController controller = new CustomerController();

    @RequestMapping(method = RequestMethod.GET)
    public RESTSchema<RESTCompany> get(Integer page, Integer limit,
                                       @RequestParam(required = false) String nameContains,
                                       @RequestParam(required = false) String country,
                                       @RequestParam(required = false) String city,
                                       @RequestParam(required = false) String postalCode) {

        CustomerDAO customerDAO = (CustomerDAO) controller.getDao(); //TODO getId rid of cast
        List<Filter> filters = new ArrayList<>();
        if (nameContains != null) {
            filters.add(customerDAO.byName(nameContains));
        }
        //filters.add(customerDAO.byAddress(new Address(null, null, city, postalCode, country))); TODO fix this
        Collection<RESTCompany> result = new ArrayList<>();
        try {
            for (Customer customer : controller.getAll(filters.toArray(new Filter[filters.size()]))) {
                result.add(modelToRESTCompany(customer));
            }

        } catch (DataAccessException e) {
            //API doesn't contain error
        }
        return new RESTSchema<>(result, page, limit, PATH_COMPANY);
    }

    @RequestMapping(method = RequestMethod.POST)
    public RESTCompany post(@RequestBody RESTCompany restCompany) {
        try {
            Customer customer = controller.create(RESTToModelAddress(restCompany.getAddress()),
                    restCompany.getPhoneNumber(),
                    restCompany.getName(),
                    restCompany.getVatNumber());
            restCompany = modelToRESTCompany(customer);
        } catch (DataAccessException e) {
            throw new InvalidInputException(e);
        }
        return restCompany;
    }

    @RequestMapping(method = RequestMethod.GET, value = "{id}")
    public RESTCompany getId(@PathVariable("id") String id) {
        UUID uuid = UUIDUtil.toUUID(id);
        try {
            return modelToRESTCompany(controller.get(uuid));
        } catch (DataAccessException e) {
            throw new NotFoundException();
        }
    }

    @RequestMapping(method = RequestMethod.PUT, value = "{id}")
    public RESTCompany putId(@PathVariable("id") String id, @RequestBody RESTCompany restCompany) {
        UUID uuid = UUIDUtil.toUUID(id);
        try {
            Customer customer = controller.update(uuid,
                    RESTToModelAddress(restCompany.getAddress()),
                    restCompany.getPhoneNumber(),
                    restCompany.getName(),
                    restCompany.getVatNumber());
            restCompany = modelToRESTCompany(customer);
        } catch (DataAccessException e) {
            e.printStackTrace();
        }
        return restCompany;
    }

    @RequestMapping(method = RequestMethod.DELETE, value = "{id}")
    public void deleteId(@PathVariable("id") String id) {
        UUID uuid = UUIDUtil.toUUID(id);
        try {
            controller.archive(uuid);
        } catch (DataAccessException e) {
            throw new NotFoundException();
        }
    }

    private RESTCompany modelToRESTCompany(Customer customer) {
        String id = UUIDUtil.UUIDToNumberString(customer.getUuid());
        return new RESTCompany(id,
                customer.getName(),
                customer.getBtwNumber(),
                customer.getPhoneNumber(),
                modelToRESTAddress(customer.getAddress()),
                null,
                null,
                null,
                PATH_COMPANY + "/" + id);
    }

    private RESTAddress modelToRESTAddress(Address address) {
        if(address==null){
            return null;
        }
        return new RESTAddress(address.getCountry(),
                address.getTown(),
                address.getStreet(),
                address.getStreetNumber(),
                address.getPostalCode());
    }

    private Address RESTToModelAddress(RESTAddress restAddress) {
        if(restAddress==null){
            return null;
        }
        return new Address(restAddress.getStreet(),
                restAddress.getHouseNumber(),
                restAddress.getCity(),
                restAddress.getPostalCode(),
                restAddress.getCountry());
    }
}
