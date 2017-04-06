package spring.controller;

import model.account.Action;
import model.account.Resource;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import spring.model.RESTPermission;
import spring.model.RESTSchema;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

@RestController
public class RESTPermissionController {

    private static Map<Long, RESTPermission> restPermissions = new HashMap<>();
    static {
        long id = 0;
        for (Resource resource: Resource.values()) {
            for (Action action : Action.values()) {
                restPermissions.put(id, new RESTPermission(id, resource, action));
                id++;
            }
        }
    }

    @RequestMapping(value = "/auth/permissions", method = RequestMethod.GET)
    public RESTSchema<RESTPermission> getAllPermissions(HttpServletRequest request) {
        return new RESTSchema<>(restPermissions.values(), null, null, request);
    }
}
