package model.account;

import model.history.EditableObject;
import model.history.LogResource;

import java.util.*;

/**
 * Created by jorg on 3/2/17.
 */
public class Role implements EditableObject, java.io.Serializable {

    private String name;
    private Map<Resource, Permission> rights;
    private UUID uuid;

    public Role() {
        rights = new HashMap<>();
    }

    public Role(String name) {
        this.name = name;
        rights = new HashMap<>();
    }

    private Role(String name, Map<Resource, Permission> rights, UUID uuid) {
        this.name = name;
        this.rights = rights;
        this.uuid = uuid;
    }

    /**
     * Removes all the persmissions of the Role
     */
    public void clearPermissions() {
        rights = new HashMap<>();
    }

    public void setAccess(Resource resource, Action action) {
        rights.putIfAbsent(resource, new Permission(resource));
        rights.get(resource).addAction(action);
    }

    public boolean hasAccess(Resource resource, Action action) {
        return rights.containsKey(resource) && rights.get(resource).getActions().contains(action);
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Map<Resource, Permission> getRights() {
        return rights;
    }

    public void setRights(Map<Resource, Permission> rights) {
        this.rights = rights;
    }

    @Override
    public UUID getUuid() {
        return uuid;
    }

    public void setUuid(UUID uuid) {
        this.uuid = uuid;
    }

    @Override
    public Role copy() {
        return new Role(name, rights, uuid);
    }

    @Override
    public LogResource getLogResource() {
        return LogResource.ROLE;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || !(o instanceof Role)) return false;

        Role role = (Role) o;

        return getUuid().equals(role.getUuid());

    }

    @Override
    public int hashCode() {
        return getUuid().hashCode();
    }
}
