package model.account;

import model.history.EditableObject;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

/**
 * Created by jorg on 3/2/17.
 *
 */
public class Role implements EditableObject, java.io.Serializable {
    private String name;
    private Map<Resource,Action>  rights;
    private UUID uuid;

    public Role() {
    }

    public Role(String name) {
        this.name = name;
        rights=new HashMap<>();
        uuid=UUID.randomUUID();
    }

    private Role(String name, Map<Resource, Action> rights, UUID uuid) {
        this.name = name;
        this.rights = rights;
        this.uuid = uuid;
    }

    public void setAccess(Resource ai, Action al){
        rights.put(ai,al);
    }

    public boolean hasAccess(Resource ai, Action al){
        return rights.containsKey(ai) &&rights.get(ai).equals(al);
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Map<Resource, Action> getRights() {
        return rights;
    }

    public void setRights(Map<Resource, Action> rights) {
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
