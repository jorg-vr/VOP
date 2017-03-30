package model.account;

import model.history.EditableObject;

import java.util.*;

/**
 * Created by jorg on 3/2/17.
 *
 */
public class Role implements EditableObject, java.io.Serializable {
    private String name;
    private Map<Resource,Set<Action>>  rights;
    private UUID uuid;

    public Role() {
        rights=new HashMap<>();
    }

    public Role(String name) {
        this.name = name;
        rights=new HashMap<>();
        uuid=UUID.randomUUID();
    }

    private Role(String name, Map<Resource, Set<Action>> rights, UUID uuid) {
        this.name = name;
        this.rights = rights;
        this.uuid = uuid;
    }

    public void setAccess(Resource resource, Action action){
        rights.putIfAbsent(resource,new HashSet<>());
        rights.get(resource).add(action);
    }

    public boolean hasAccess(Resource resource, Action action){
        return rights.containsKey(resource) &&rights.get(resource).contains(action);
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Map<Resource, Set<Action>> getRights() {
        return rights;
    }

    public void setRights(Map<Resource, Set<Action>> rights) {
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
    public EditableObject copy() {
        return new Role(name,rights,uuid);
    }
}
