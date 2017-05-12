package model.account;

import model.history.EditableObject;
import model.history.LogResource;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

import static util.UUIDUtil.UUIDToNumberString;

/**
 * Represents a Role (what the user can access)
 * Created by jorg on 3/2/17.
 */
public class Role implements EditableObject, java.io.Serializable {

    /**
     * The name of the role, should be unique
     */
    private String name;


    private Map<Resource, Permission> rights;

    /**
     * Unique id to represent the object
     */
    private UUID uuid;

    /**
     * Default constructor
     */
    public Role() {
        rights = new HashMap<>();
    }

    /**
     * Constructor
     *
     * @param uuid the uuid of the role
     */
    public Role(UUID uuid) {
        this.uuid = uuid;
    }

    /**
     * Constructor
     *
     * @param name the name of the role
     */
    public Role(String name) {
        this.name = name;
        rights = new HashMap<>();
    }

    /**
     * Removes all the permissions of the Role
     */
    public void clearPermissions() {
        rights = new HashMap<>();
    }

    /**
     * Adds an permission to a given resource
     *
     * @param resource the resource
     * @param action   the action
     */
    public void setAccess(Resource resource, Action action) {
        rights.putIfAbsent(resource, new Permission(resource));
        rights.get(resource).addAction(action);
    }

    /**
     * Returns true if this role hass access to the given resource with the given action
     *
     * @param resource the resource
     * @param action   the action
     * @return true if the mapping between resource and action exists, false otherwise
     */
    public boolean hasAccess(Resource resource, Action action) {
        return rights.containsKey(resource) && rights.get(resource).getActions().contains(action);
    }

    /**
     * Gets the name
     *
     * @return the name
     */
    public String getName() {
        return name;
    }

    /**
     * Sets the name
     *
     * @param name
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * Gets the rights
     *
     * @return the rights
     */
    public Map<Resource, Permission> getRights() {
        return rights;
    }

    /**
     * Sets the rights
     *
     * @param rights the rights
     */
    public void setRights(Map<Resource, Permission> rights) {
        this.rights = rights;
    }

    /**
     * Gets the uuid
     *
     * @return the uuid
     */
    @Override
    public UUID getUuid() {
        return uuid;
    }

    /**
     * Sets the uuid
     *
     * @param uuid the uuid
     */
    public void setUuid(UUID uuid) {
        this.uuid = uuid;
    }


    /**
     * Copies the object and sets its fields
     *
     * @return the copied object
     */
    @Override
    public EditableObject copy() {
        Role role = new Role();
        role.setRights(new HashMap<>(rights));
        role.setName(name);
        return role;
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

        return this.uuid != null && getUuid().equals(role.getUuid());

    }

    @Override
    public int hashCode() {
        if (uuid != null) {
            return getUuid().hashCode();
        }
        return super.hashCode();
    }

    @Override
    public String toString() {
        return UUIDToNumberString(uuid);
    }
}
