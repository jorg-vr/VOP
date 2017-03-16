package spring.controller;

import controller.FunctionController;
import dao.interfaces.DataAccessException;
import model.account.Function;
import org.springframework.web.bind.annotation.*;
import spring.Exceptions.InvalidInputException;
import spring.Exceptions.NotFoundException;
import spring.Exceptions.NotImplementedException;
import spring.model.RESTRole;
import spring.model.RESTSchema;

import java.time.LocalDate;
import java.util.*;

import static spring.controller.UUIDUtil.UUIDToNumberString;

/**
 * This controller is responsible for handling the HTTP requests of the URL /roles.
 * Currently, the following HTTP requests are supported:
 *  1) GET /roles
 *  2) GET /roles/{id}
 *  3) POST /roles
 *  4) PUT /roles/{id}
 *  5) DELETE /roles/{id}
 *
 *  This controller is responsible for translating the RESTModels to the backend specific models and calling the appropriate methods
 *  of the spring independent controllers,  located in the controller package.
 *  It is also responsible for translating the backend specific exceptions to HTPP repsonse codes.
 *
 *  For more information about what the HTTP requests do, see the API specification
 */
@RestController
@RequestMapping("/roles")
public class RESTRoleController {

    public static final String PATH_ROLE = "/roles";

    private FunctionController controller = new FunctionController();

    @RequestMapping(method = RequestMethod.GET)
    public RESTSchema<RESTRole> get(Integer page, Integer limit) {
        Collection<RESTRole> roles = new ArrayList<>();
        try {
            Collection<Function> functions = controller.getAll();
            for (Function function : functions) {
                RESTRole restRole = modelToRest(function);
                roles.add(restRole);
            }
        } catch (DataAccessException e) {
            e.printStackTrace();
        }
        return new RESTSchema<>(roles, page, limit, PATH_ROLE+"?");
    }

    @RequestMapping(method = RequestMethod.POST)
    public RESTRole post(@RequestBody RESTRole role) {
        UUID companyUUID = UUIDUtil.toUUID(role.getCompanyId());
        UUID userUUID = UUIDUtil.toUUID(role.getUserId());
        RESTRole createdRole;

        try {
            Function function = controller.create(companyUUID, role.getFunction(), userUUID, role.getStartDate(), role.getEndDate());
            createdRole = modelToRest(function);
        } catch (DataAccessException e) {
            throw new InvalidInputException(e);
        }
        return createdRole;
    }

    @RequestMapping(value = "{id}", method = RequestMethod.GET)
    public RESTRole getId(@PathVariable("id") String id) {
        UUID uuid = UUIDUtil.toUUID(id);
        try {
            Function function = controller.get(uuid);
            return modelToRest(function);
        } catch (Exception e) {
            throw new NotFoundException();
        }
    }

    @RequestMapping(value = "{id}", method = RequestMethod.PUT)
    public RESTRole putId(@PathVariable("id") String id, @RequestBody RESTRole role) {
        System.out.println("ids: "+id+"  "+role.getCompanyId()+"  "+role.getUserId());
        UUID uuid = UUIDUtil.toUUID(id);
        UUID companyUuid = UUIDUtil.toUUID(role.getCompanyId());
        UUID userUuid = UUIDUtil.toUUID(role.getUserId());
        System.out.println("ids: "+uuid+"  "+userUuid+"  "+companyUuid);
        try {
            Function function = controller.update(uuid,
                    companyUuid,
                    role.getFunction(),
                    userUuid,
                    role.getStartDate(),
                    role.getEndDate());
            return modelToRest(function);
        } catch (DataAccessException e) {
            throw new NotFoundException();
        }
    }

    @RequestMapping(value = "{id}", method = RequestMethod.DELETE)
    public void deleteId(@PathVariable("id") String id) {
        UUID uuid = UUIDUtil.toUUID(id);
        try {
            controller.archive(uuid);
        } catch (DataAccessException e) {
            throw new NotFoundException();
        }
    }

    /**
     * Transforms a function object to a restrole object
     *
     * @param function
     * @return restroleobject with the fields of the function object
     */
    private RESTRole modelToRest(Function function) {
        String id = UUIDToNumberString(function.getUuid());
        String userId = UUIDToNumberString(function.getAccount().getUuid());
        RESTRole role = new RESTRole();
        role.setFunction("");
        role.setId(id);
        role.setUserId(userId);
        role.setCompanyId(UUIDUtil.UUIDToNumberString(function.getCompany().getUuid()));
        role.setStartDate(function.getStartDate());
        role.setEndDate(function.getEndDate());
        //role.setUpdatedAt(); TODO milestone?
        //role.setCreatedAt();
        role.setUrl(PATH_ROLE + "/" + id);
        return role;
    }

}
