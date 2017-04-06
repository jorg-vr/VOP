package spring.model;

import model.account.Action;
import model.account.Permission;
import model.account.Resource;

import java.util.ArrayList;
import java.util.Collection;

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
}
