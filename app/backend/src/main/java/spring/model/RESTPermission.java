package spring.model;

import model.account.Action;
import model.account.Resource;

/**
 * Created by Billie Devolder on 6/04/2017.
 */
public class RESTPermission {

    private long id;

    private String resource;

    private String action;

    public RESTPermission(long id, Resource resource, Action action) {
        this.id = id ;
        this.resource = resource.name();
        this.action = action.name();
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
