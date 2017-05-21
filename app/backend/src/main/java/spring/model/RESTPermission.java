package spring.model;

import model.account.Action;
import model.account.Permission;
import model.account.Resource;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by Billie Devolder on 6/04/2017.
 */
public class RESTPermission {

    private long id;
    private String resource;
    private String action;

    public RESTPermission(Resource resource, Action action) {
        this.id = (resource.ordinal()) * 987152 + action.ordinal();
        this.resource = resource.name();
        this.action = action.name();
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getResource() {
        return resource;
    }

    public void setResource(String resource) {
        this.resource = resource;
    }

    public String getAction() {
        return action;
    }

    public void setAction(String action) {
        this.action = action;
    }

    /**
     * Translate a list of Permissions into a list of RESTPermissions.
     * A Permission can have multiple actions but a RESTPermission can have only 1,
     * so we have to create multiple RESTPermissions of 1 Permission.
     * These RESTPermissions have the same resource field, but a different action field.
     * @param permissions a collection of Permissions.
     * @return a collection of RESTPermissions
     */
    public static Collection<RESTPermission> translate(Collection<Permission> permissions) {
        Collection<RESTPermission> restPermissions = new ArrayList<>();
        for (Permission permission: permissions) {
            Resource resource = permission.getResource();
            for (Action action: permission.getActions()) {
                RESTPermission restPermission = new RESTPermission(resource, action);
                restPermissions.add(restPermission);
            }
        }
        return restPermissions;
    }

    /**
     * @return  a Map that contains all possible combinations of RESTPermissions.
     *          The map projects an id of a RESTPermission to the object itself.
     */
    public static Map<Long, RESTPermission> getAllRESTPermissions() {
        Map<Long, RESTPermission> allPermissions = new HashMap<>();
        for (Resource resource : Resource.values()) {
            for (Action action : Action.values()) {
                RESTPermission permission = new RESTPermission(resource, action);
                allPermissions.put(permission.getId(), permission);
            }
        }
        return allPermissions;
    }

}
