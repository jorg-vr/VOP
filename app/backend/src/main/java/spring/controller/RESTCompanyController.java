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
 * This controller is responsible for handling the HTTP requests of the URL /companies.
 * Currently, the following HTTP requests are supported:
 *  1) GET /companies
 *  2) GET /companies/{id}
 *  3) POST /companies
 *  4) PUT /companies/{id}
 *  5) DELETE /companies/{id}
 *
 *  This controller is responsible for translating the RESTModels to the backend specific models and calling the appropriate methods
 *  of the spring independent controllers,  located in the controller package.
 *  It is also responsible for translating the backend specific exceptions to HTPP repsonse codes.
 *
 *  For more information about what the HTTP requests do, see the API specification
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
        return new RESTSchema<>(result, page, limit, PATH_COMPANY + "?");
    }

    @RequestMapping(method = RequestMethod.POST)
    public RESTCompany post(@RequestBody RESTCompany restCompany) {
        RESTCompany updatedCompany;

        try {
            Customer customer = controller.create(RESTToModelAddress(restCompany.getAddress()),
                    restCompany.getPhoneNumber(),
                    restCompany.getName(),
                    restCompany.getVatNumber());
            updatedCompany = modelToRESTCompany(customer);
        } catch (DataAccessException e) {
            throw new InvalidInputException(e);
        }
        return updatedCompany;
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
        RESTCompany createdCompany;
        try {
            Customer customer = controller.update(uuid,
                    RESTToModelAddress(restCompany.getAddress()),
                    restCompany.getPhoneNumber(),
                    restCompany.getName(),
                    restCompany.getVatNumber());
            createdCompany = modelToRESTCompany(customer);
        } catch (DataAccessException e) {
            e.printStackTrace();
            throw new InvalidInputException();
        }
        return createdCompany;
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

    /**
     * This method translates the Customer object to a RESTCompany object.
     * @param customer should not be null
     */
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

    /**
     * This method translates the RESTAddress object to an Address object.
     * @return if address is null, null will be returned
     */
    private RESTAddress modelToRESTAddress(Address address) {
        if (address == null) {
            return null;
        }
        return new RESTAddress(address.getCountry(),
                address.getTown(),
                address.getStreet(),
                address.getStreetNumber(),
                address.getPostalCode());
    }

    /**
     * This method translates the Addres object to a RESTAddress object.
     * @return if address is null, null will be returned
     */
    private Address RESTToModelAddress(RESTAddress restAddress) {
        if (restAddress == null) {
            return null;
        }
        return new Address(restAddress.getStreet(),
                restAddress.getHouseNumber(),
                restAddress.getCity(),
                restAddress.getPostalCode(),
                restAddress.getCountry());
    }
}
