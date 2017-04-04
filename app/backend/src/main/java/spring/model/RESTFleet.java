package spring.model;

import model.fleet.Fleet;
import spring.controller.UUIDUtil;

/**
 * This is a bean class as specified in the API specification
 */
public class RESTFleet extends RESTAbstractModel {

    private static final String PATH_FLEETS = "/fleets";

    private String company;
    private String name;

    public RESTFleet() {
    }

    public RESTFleet(Fleet fleet) {
        super(fleet.getUuid(), PATH_FLEETS);
        company = UUIDUtil.UUIDToNumberString(fleet.getOwner().getUuid());
        name = fleet.getName();
    }

    public RESTFleet(String id, String company, String name, String createdAt, String updatedAt, String lastUpdatedBy, String url) {
        setId(id);
        this.company = company;
        this.name = name;
    }

    public String getCompany() {
        return company;
    }

    public void setCompany(String company) {
        this.company = company;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
