package model.account;

import model.history.EditableObject;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

/**
 * Created by jorg on 3/2/17.
 *
 */
public class Role implements EditableObject {
    private String name;
    private Map<Accessible,AccessLevel>  rights;
    private UUID uuid;

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

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
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
    @Override
    public UUID getUUID() {
        return uuid;
    }

    @Override
    public EditableObject copy() {
        return new Role(name,rights,uuid);
    }
}
