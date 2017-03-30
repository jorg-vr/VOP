package spring.model;

import controller.CustomerController;
import dao.interfaces.DataAccessException;
import model.fleet.Fleet;
import model.identity.Customer;
import spring.controller.UUIDUtil;
import spring.exceptions.InvalidInputException;

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

    public Fleet translate(){
        Fleet fleet=new Fleet();
        fleet.setName(getName());
        try {
            fleet.setOwner(new CustomerController().get(UUIDUtil.toUUID(getCompany())));
        } catch (DataAccessException e) {
            throw new InvalidInputException("company");
        }
        fleet.setUuid(UUIDUtil.toUUID(getId()));
        return  fleet;
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
