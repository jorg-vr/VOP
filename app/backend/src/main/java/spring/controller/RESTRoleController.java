package spring.controller;

import controller.AbstractController;
import controller.AccountController;
import controller.CustomerController;
import controller.FunctionController;
import controller.exceptions.UnAuthorizedException;
import dao.interfaces.DataAccessException;
import model.account.Account;
import model.account.Function;
import model.identity.Company;
import org.springframework.web.bind.annotation.*;
import spring.exceptions.InvalidInputException;
import spring.exceptions.NotAuthorizedException;
import spring.exceptions.NotFoundException;
import spring.model.RESTModelFactory;
import spring.model.RESTRole;
import spring.model.RESTSchema;

import java.util.*;

import static spring.controller.UUIDUtil.UUIDToNumberString;
import static spring.controller.UUIDUtil.toUUID;

/**
 * This controller is responsible for handling the HTTP requests of the URL /roles.
 * Currently, the following HTTP requests are supported:
 * 1) GET /roles
 * 2) GET /roles/{id}
 * 3) POST /roles
 * 4) PUT /roles/{id}
 * 5) DELETE /roles/{id}
 * <p>
 * This controller is responsible for translating the RESTModels to the backend specific models and calling the appropriate methods
 * of the spring independent controllers,  located in the controller package.
 * It is also responsible for translating the backend specific exceptions to HTPP repsonse codes.
 * <p>
 * For more information about what the HTTP requests do, see the API specification
 */
@RestController
@RequestMapping("/roles")
public class RESTRoleController extends RESTAbstractController<RESTRole,Function>{

    public static final String PATH_ROLE = "/roles";

    private FunctionController controller = new FunctionController();
    private CustomerController customerController = new CustomerController();
    private AccountController accountController = new AccountController();

    public RESTRoleController() {
        super(new FunctionController(), RESTRole::new);
    }

    @RequestMapping(method = RequestMethod.GET)
    public RESTSchema<RESTRole> get(@RequestParam(required = false) String company,
                                    @RequestParam(required = false) String user,
                                    @RequestParam(required = false) Boolean active,
                                    @RequestParam(required = false) Integer page,
                                    @RequestParam(required = false) Integer limit) {
        Collection<RESTRole> roles = new ArrayList<>();
        try {
            UUID companyUuid = UUIDUtil.toUUID(company);
            UUID accountUuid = UUIDUtil.toUUID(user);

            Company customer = companyUuid != null ? customerController.get(companyUuid) : null;
            Account account = accountUuid != null ? accountController.get(accountUuid) : null;

            Collection<Function> functions = controller.getFiltered(customer, account, active);
            for (Function function : functions) {
                RESTRole restRole = new RESTRole(function);
                roles.add(restRole);
            }

        } catch (DataAccessException e) {
            e.printStackTrace();
        } catch (UnAuthorizedException e) {
            throw new NotAuthorizedException();
        }
        return new RESTSchema<>(roles, page, limit, PATH_ROLE + "?");
    }





}
