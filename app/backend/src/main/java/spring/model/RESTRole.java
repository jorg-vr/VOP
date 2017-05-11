package spring.model;


import controller.ControllerManager;
import model.account.Role;
import util.UUIDUtil;

import static util.MyProperties.*;

/**
 * This is a bean class as specified in the API specification
 */
public class RESTRole extends RESTAbstractModel<Role> {

    private String name;
    private String permissions;

    public RESTRole() {
    }

    public RESTRole(Role role) {
        super(role.getUuid(), getProperty(PATH_AUTH) + "/" + getProperty(PATH_ROLES));
        this.name = role.getName();
    }

    public Role translate(ControllerManager manager) {
        Role role = new Role();
        role.setName(name);
        role.setUuid(UUIDUtil.toUUID(getId()));
        return role;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPermissions() {
        return getUrl() + "/" + getProperty(PATH_PERMISSIONS);
    }

    public void setPermissions(String permissions) {
        this.permissions = permissions;
    }
}
