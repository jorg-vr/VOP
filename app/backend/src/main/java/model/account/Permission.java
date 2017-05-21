package model.account;

import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

/**
 * Class for hibernate use (not possible to have a HashMap as value in a HashMap)
 * Created by sam on 4/4/17.
 */
public class Permission {
    /**
     * The resource, should not be null
     */
    private Resource resource;

    /**
     * The collection of permitted actions
     */
    private Set<Action> actions;

    /**
     * The unique id
     */
    private UUID uuid;

    /**
     * Default Constructor
     */
    public Permission() {
    }

    /**
     * Constructor
     * @param resource the resource
     */
    public Permission(Resource resource) {
        this.resource = resource;
    }

    /**
     * Gets the id
     * @return the id
     */
    public UUID getUuid() {
        return uuid;
    }

    /**
     * Sets the id
     * @param uuid the id
     */
    public void setUuid(UUID uuid) {
        this.uuid = uuid;
    }

    /**
     * Gets the resource
     * @return the resource
     */
    public Resource getResource() {
        return resource;
    }

    /**
     * Sets the resource
     * @param resource the resource
     */
    public void setResource(Resource resource) {
        this.resource = resource;
    }

    /**
     * Gets the permitted actions
     * @return the permitted actions
     */
    public Set<Action> getActions() {
        return actions;
    }

    /**
     * Sets the actions
     * @param actions the actions
     */
    public void setActions(Set<Action> actions) {
        this.actions = actions;
    }

    /**
     * Adds a permitted action of the resource
     * @param action the new action
     */
    public void addAction(Action action) {
        if (actions == null) {
            actions = new HashSet<>();
        }
        actions.add(action);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Permission that = (Permission) o;

        return getUuid() != null ? getUuid().equals(that.getUuid()) : that.getUuid() == null;
    }

    @Override
    public int hashCode() {
        return getUuid() != null ? getUuid().hashCode() : 0;
    }
}

