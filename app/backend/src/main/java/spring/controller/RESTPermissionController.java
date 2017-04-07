package spring.controller;

import controller.RoleController;
import controller.exceptions.UnAuthorizedException;
import dao.interfaces.DataAccessException;
import model.account.Action;
import model.account.Resource;
import model.account.Role;
import org.springframework.web.bind.annotation.*;
import spring.exceptions.InvalidInputException;
import spring.exceptions.NotAuthorizedException;
import spring.model.RESTPermission;
import spring.model.RESTSchema;

import javax.servlet.http.HttpServletRequest;
import java.util.*;

@RestController
public class RESTPermissionController extends RESTSimpleController {

    @RequestMapping(value = "/auth/permissions", method = RequestMethod.GET)
    public RESTSchema<RESTPermission> getAllPermissions(HttpServletRequest request,
                                                        Integer page, Integer limit,
                                                        @RequestHeader(value = "AuthToken") String token,
                                                        @RequestHeader(value = "Function") String function) {
        return new RESTSchema<>(RESTPermission.getAllRESTPermissions().values(), page, limit, request);
    }

    @RequestMapping(value = "/auth/roles/{id}/permissions", method = RequestMethod.GET)
    public RESTSchema<RESTPermission> get(@PathVariable String id,
                                          HttpServletRequest request,
                                          Integer page, Integer limit,
                                          @RequestHeader(value = "AuthToken") String token,
                                          @RequestHeader(value = "Function") String function) {
        UUID uuid = UUIDUtil.toUUID(id);
        try (RoleController roleController = new RoleController(verifyToken(token, function))) {
            Role role = roleController.get(uuid);
            Collection<RESTPermission> restPermissions = RESTPermission.translate(role.getRights().values());
            return new RESTSchema<>(restPermissions, page, limit, request);
        } catch (DataAccessException e) {
            throw new InvalidInputException("Role does not exist");
        } catch (UnAuthorizedException e) {
            throw new NotAuthorizedException();
        }
    }

    @RequestMapping(value = "/auth/roles/{id}/permissions", method = RequestMethod.PUT)
    public void put(@PathVariable String id,
                    @RequestBody List<Long> permissions,
                    @RequestHeader(value = "AuthToken") String token,
                    @RequestHeader(value = "Function") String function) {
        UUID uuid = UUIDUtil.toUUID(id);
        try (RoleController roleController = new RoleController(verifyToken(token, function))) {
            try {
                Role role = roleController.get(uuid);
                setPermissions(role, permissions);
                roleController.update(role);
            } catch (DataAccessException e) {
                throw new InvalidInputException("Role does not exist");
            } catch (UnAuthorizedException e) {
                throw new NotAuthorizedException();
            }
        }
    }

    /**
     * Set the Permissions of a Role based on the ids.
     * @param role Role whose Permissions should be set. Note that is is set and not add.
     * @param ids ids of all the RESTPermissions that should be set on the Role.
     *            Note that a Role has Permissions and not RESTPermissions,
     *            so a translation to Permissions happens in this method.
     * @throws InvalidInputException gets thrown when there is an invalid RESTPermission id
     */
    private void setPermissions(Role role, List<Long> ids) {
        role.clearPermissions();
        Map<Long, RESTPermission> allPermissions = RESTPermission.getAllRESTPermissions();
        for (Long id : ids) {
            if (!allPermissions.containsKey(id)) {
                throw new InvalidInputException("There is no permission with id" + id);
            }

            RESTPermission restPermission = allPermissions.get(id);
            Resource resource = Resource.valueOf(restPermission.getResource());
            Action action = Action.valueOf(restPermission.getAction());

            role.setAccess(resource, action);
        }
    }
}
