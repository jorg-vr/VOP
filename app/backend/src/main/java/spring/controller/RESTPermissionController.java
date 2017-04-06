package spring.controller;

import controller.AbstractController;
import controller.RoleController;
import controller.exceptions.UnAuthorizedException;
import dao.interfaces.DataAccessException;
import model.account.Action;
import model.account.Permission;
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

    private static Map<Long, RESTPermission> allPermissions = new HashMap<>();

    static {
        for (Resource resource : Resource.values()) {
            for (Action action : Action.values()) {
                RESTPermission permission = new RESTPermission(resource, action);
                allPermissions.put(permission.getId(), permission);
            }
        }
    }

    @RequestMapping(value = "/auth/permissions", method = RequestMethod.GET)
    public RESTSchema<RESTPermission> getAllPermissions(HttpServletRequest request,
                                                        Integer page, Integer limit,
                                                        @RequestHeader(value = "AuthToken") String token,
                                                        @RequestHeader(value = "Function") String function) {
        return new RESTSchema<>(allPermissions.values(), page, limit, request);
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
                translatePermissions(role, permissions);
                roleController.update(role);
            } catch (DataAccessException e) {
                throw new InvalidInputException("Role does not exist");
            } catch (UnAuthorizedException e) {
                throw new NotAuthorizedException();
            }
        }
    }

    private void translatePermissions(Role role, List<Long> idList) {
        for (Long id : idList) {
            if (!allPermissions.containsKey(id)) {
                throw new InvalidInputException("There is no permission with id" + id);
            }

            //TODO clear permissions first
            RESTPermission restPermission = allPermissions.get(id);
            Resource resource = Resource.valueOf(restPermission.getResource());
            Action action = Action.valueOf(restPermission.getAction());

            role.setAccess(resource, action);
        }
    }
}
