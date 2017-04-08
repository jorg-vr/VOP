package spring.controller;

import controller.AbstractController;
import controller.CustomerController;
import controller.exceptions.UnAuthorizedException;
import dao.interfaces.CustomerDAO;
import dao.interfaces.DataAccessException;
import dao.interfaces.Filter;
import model.identity.Address;
import model.identity.Company;
import model.identity.Customer;
import org.springframework.web.bind.annotation.*;
import spring.exceptions.InvalidInputException;
import spring.exceptions.NotAuthorizedException;
import spring.exceptions.NotFoundException;
import spring.model.*;

import javax.servlet.http.HttpServletRequest;
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
@RequestMapping("/${path.companies}")
public class RESTCompanyController extends RESTAbstractController<RESTCompany,Customer> {


    public RESTCompanyController() {
        super( CustomerController::new, RESTCompany::new);
    }


    @RequestMapping(method = RequestMethod.GET)
    public RESTSchema<RESTCompany> get(HttpServletRequest request,
                                       Integer page, Integer limit,
                                       @RequestParam(required = false) String nameContains,
                                       @RequestParam(required = false) String country,
                                       @RequestParam(required = false) String city,
                                       @RequestParam(required = false) String postalCode,
                                       @RequestHeader(value="Authorization") String token,
                                       @RequestHeader(value="Function") String function) {


        try(CustomerController controller= new CustomerController(verifyToken(token,function))) {
            CustomerDAO customerDAO = (CustomerDAO) controller.getDao(); //TODO getId rid of cast
            List<Filter> filters = new ArrayList<>();
            if (nameContains != null) {
                filters.add(customerDAO.byName(nameContains));
            }
            //filters.add(customerDAO.byAddress(new Address(null, null, city, postalCode, country))); TODO fix this
            Collection<RESTCompany> result = new ArrayList<>();
            for (Company company : controller.getAll(filters.toArray(new Filter[filters.size()]))) {
                result.add(new RESTCompany(company));
            }

            return new RESTSchema<>(result, page, limit, request);
        } catch (DataAccessException e) {
            //API doesn't contain error
            throw new RuntimeException(e);
        } catch (UnAuthorizedException e) {
            throw new NotAuthorizedException();
        }


    }



}
