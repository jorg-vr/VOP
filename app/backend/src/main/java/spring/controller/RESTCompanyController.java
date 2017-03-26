package spring.controller;

import controller.CustomerController;
import dao.interfaces.CustomerDAO;
import dao.interfaces.DataAccessException;
import dao.interfaces.Filter;
import model.identity.Address;
import model.identity.Company;
import org.springframework.web.bind.annotation.*;
import spring.exceptions.InvalidInputException;
import spring.exceptions.NotFoundException;
import spring.model.RESTAddress;
import spring.model.RESTCompany;
import spring.model.RESTSchema;

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
            for (Company company : controller.getAll(filters.toArray(new Filter[filters.size()]))) {
                result.add(new RESTCompany(company));
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
            Company company = controller.create(RESTToModelAddress(restCompany.getAddress()),
                    restCompany.getPhoneNumber(),
                    restCompany.getName(),
                    restCompany.getVatNumber());
            updatedCompany = new RESTCompany(company);
        } catch (DataAccessException e) {
            throw new InvalidInputException(e);
        }
        return updatedCompany;
    }

    @RequestMapping(method = RequestMethod.GET, value = "{id}")
    public RESTCompany getId(@PathVariable("id") String id) {
        UUID uuid = UUIDUtil.toUUID(id);
        try {
            return new RESTCompany(controller.get(uuid));
        } catch (DataAccessException e) {
            throw new NotFoundException();
        }
    }

    @RequestMapping(method = RequestMethod.PUT, value = "{id}")
    public RESTCompany putId(@PathVariable("id") String id, @RequestBody RESTCompany restCompany) {
        UUID uuid = UUIDUtil.toUUID(id);
        RESTCompany createdCompany;
        try {
            Company company = controller.update(uuid,
                    RESTToModelAddress(restCompany.getAddress()),
                    restCompany.getPhoneNumber(),
                    restCompany.getName(),
                    restCompany.getVatNumber());
            createdCompany = new RESTCompany(company);
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
     * This method translates the Addres object to a RESTAddress object.
     * @return if address is null, null will be returned
     */
    private Address RESTToModelAddress(RESTAddress restAddress) {
        if (restAddress == null) {
            return null;
        }
        return restAddress.translate();
    }
}
