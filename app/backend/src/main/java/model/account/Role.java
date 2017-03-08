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
    private Map<Accessible,AccessLevel>  rights;
    private UUID uuid;

    public Role() {
    }

    public Role(String name) {
        this.name = name;
        rights=new HashMap<>();
        uuid=UUID.randomUUID();
    }

    private Role(String name, Map<Accessible, AccessLevel> rights, UUID uuid) {
        this.name = name;
        this.rights = rights;
        this.uuid = uuid;
    }

    public void setAccess(Accessible ai,AccessLevel al){
        rights.put(ai,al);
    }

    public boolean hasAccess(Accessible ai,AccessLevel al){
        return rights.containsKey(ai) &&rights.get(ai).equals(al);
    }
    public boolean canRead(Accessible ai){
        return hasAccess(ai,AccessLevel.READ);
    }

    public boolean canWrite(Accessible ai){
        return hasAccess(ai,AccessLevel.WRITE);
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Map<Accessible, AccessLevel> getRights() {
        return rights;
    }

    public void setRights(Map<Accessible, AccessLevel> rights) {
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
