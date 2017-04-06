package model.account;

import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

/**
 * Created by sam on 4/4/17.
 */
public class Permission {
    private Resource resource;
    private Set<Action> actions;
    private UUID uuid;

    public Permission(){}
    public Permission(Resource resource){
        this.resource = resource;
    }

    public UUID getUuid() {
        return uuid;
    }

    public void setUuid(UUID uuid) {
        this.uuid = uuid;
    }

    public Resource getResource() {
        return resource;
    }

    public void setResource(Resource resource) {
        this.resource = resource;
    }

    public Set<Action> getActions() {
        return actions;
    }

    public void setActions(Set<Action> actions) {
        this.actions = actions;
    }

    public void addAction(Action action){
        if(actions == null){
            actions = new HashSet<>();
        }
        actions.add(action);
    }
}

