package spring.controller;

import controller.ControllerManager;
import controller.RoleController;
import controller.exceptions.UnAuthorizedException;
import dao.exceptions.ConstraintViolationException;
import dao.exceptions.DataAccessException;
import dao.exceptions.ObjectNotFoundException;
import model.account.Action;
import model.account.Resource;
import model.account.Role;
import org.springframework.web.bind.annotation.*;
import spring.exceptions.InvalidInputException;
import spring.model.AuthenticationToken;
import spring.model.RESTPermission;
import spring.model.RESTSchema;
import util.UUIDUtil;

import javax.servlet.http.HttpServletRequest;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.stream.Collectors;

import static util.UUIDUtil.toUUID;

/**
 * Requests that are implemented in this class:
 * 1) GET /auth/permissions
 * 2) GET /auth/roles/{id}/permissions
 * 3) PUT /auth/roles/{id}/permissions
 */
@RestController
public class RESTPermissionController {

    @RequestMapping(value = "/${path.auth}/${path.permissions}", method = RequestMethod.GET)
    public RESTSchema<RESTPermission> getAllPermissions(HttpServletRequest request,
                                                        Integer page, Integer limit,
                                                        String resource, String action,
                                                        @RequestHeader(value = "Authorization") String token,
                                                        @RequestHeader(value = "Function") String function) {
        Collection<RESTPermission> allPermissions = RESTPermission.getAllRESTPermissions().values();
        Collection<RESTPermission> filtered = filter(allPermissions, resource, action);
        return new RESTSchema<>(filtered, page, limit, request);
    }

    @RequestMapping(value = "/${path.auth}/${path.roles}/{id}/${path.permissions}", method = RequestMethod.GET)
    public RESTSchema<RESTPermission> get(@PathVariable String id,
                                          HttpServletRequest request,
                                          Integer page, Integer limit,
                                          String resource, String action,
                                          @RequestHeader(value = "Authorization") String token,
                                          @RequestHeader(value = "Function") String function) throws UnAuthorizedException, ObjectNotFoundException, DataAccessException {
        UUID uuid = UUIDUtil.toUUID(id);
        UUID user = new AuthenticationToken(token).getAccountId();
        try (ControllerManager manager = new ControllerManager(user, toUUID(function))) {
            RoleController controller = manager.getRoleController();

            Role role = controller.get(uuid);
            Collection<RESTPermission> restPermissions = RESTPermission.translate(role.getRights().values());
            Collection<RESTPermission> filtered = filter(restPermissions, resource, action);

            return new RESTSchema<>(filtered, page, limit, request);
        }
    }

    @RequestMapping(value = "/${path.auth}/${path.roles}/{id}/${path.permissions}", method = RequestMethod.PUT)
    public void put(@PathVariable String id,
                    @RequestBody List<Long> permissions,
                    @RequestHeader(value = "Authorization") String token,
                    @RequestHeader(value = "Function") String function) throws UnAuthorizedException, ObjectNotFoundException, ConstraintViolationException, DataAccessException {
        UUID uuid = UUIDUtil.toUUID(id);
        UUID user = new AuthenticationToken(token).getAccountId();
        try (ControllerManager manager = new ControllerManager(user, toUUID(function))) {
            RoleController controller = manager.getRoleController();

            Role role = controller.get(uuid);
            setPermissions(role, permissions);
            controller.update(role);
        }
    }


    /**
     * @param permissions collection of permissions that should be filtered. This method does NOT alter this collection in any way
     * @param resource    null if should not be filtered on resource. The filtering is case-insensitive contains
     * @param action      null if should not be filtered on action. The filtering is case-insensitive contains
     * @return a new collection with all the RESTPermissions of permissions that pass the filters
     */
    public Collection<RESTPermission> filter(Collection<RESTPermission> permissions, String resource, String action) {
        return permissions
                .stream()
                .filter(p -> resource == null || p.getResource().toLowerCase().contains(resource.toLowerCase()))
                .filter(a -> action == null || a.getAction().toLowerCase().contains(action.toLowerCase()))
                .collect(Collectors.toList());
    }

    /**
     * Set the Permissions of a Role based on the ids.
     *
     * @param role Role whose Permissions should be set. Note that is is set and not add.
     * @param ids  ids of all the RESTPermissions that should be set on the Role.
     *             Note that a Role has Permissions and not RESTPermissions,
     *             so a translation to Permissions happens in this method.
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
