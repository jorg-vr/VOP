package spring.controller;

import controller.AbstractController;
import controller.CompanyController;
import controller.CustomerController;
import controller.exceptions.UnAuthorizedException;
import controller.insurance.ContractController;
import controller.insurance.VehicleInsuranceController;
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
import spring.exceptions.ServerErrorException;
import spring.model.*;
import spring.model.insurance.RESTContract;
import spring.model.insurance.RESTVehicleInsurance;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

/**
 * This controller is responsible for handling the HTTP requests of the URL /companies.
 * Currently, the following HTTP requests are supported:
 * 1) GET /companies
 * 2) GET /companies/{id}
 * 3) POST /companies
 * 4) PUT /companies/{id}
 * 5) DELETE /companies/{id}
 * <p>
 * This controller is responsible for translating the RESTModels to the backend specific models and calling the appropriate methods
 * of the spring independent controllers,  located in the controller package.
 * It is also responsible for translating the backend specific exceptions to HTPP repsonse codes.
 * <p>
 * For more information about what the HTTP requests do, see the API specification
 */
@RestController
@RequestMapping("/${path.companies}")
public class RESTCompanyController extends RESTAbstractController<RESTCompany, Company> {


    public RESTCompanyController() {
        super(CompanyController::new, RESTCompany::new);
    }


    @RequestMapping(method = RequestMethod.GET)
    public RESTSchema<RESTCompany> get(HttpServletRequest request,
                                       Integer page, Integer limit,
                                       @RequestParam(required = false) String nameContains,
                                       @RequestParam(required = false) String country,
                                       @RequestParam(required = false) String city,
                                       @RequestParam(required = false) String postalCode,
                                       @RequestHeader(value = "Authorization") String token,
                                       @RequestHeader(value = "Function") String function) throws UnAuthorizedException {
        try (CompanyController controller = new CompanyController(verifyToken(token, function))) {
            Collection<RESTCompany> restModels = controller.getAll()
                    .stream()
                    .map(RESTCompany::new)
                    .collect(Collectors.toList());
            return new RESTSchema<>(restModels, page, limit, request);
        } catch (DataAccessException e) {
            throw new ServerErrorException("contracts could not be retrieved. This is a server error");
        }
    }


}
