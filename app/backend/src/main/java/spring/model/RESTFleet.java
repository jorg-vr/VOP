package spring.model;

import controller.ControllerManager;
import controller.CustomerController;
import controller.exceptions.UnAuthorizedException;
import dao.exceptions.ConstraintViolationException;
import dao.exceptions.DataAccessException;
import dao.exceptions.ObjectNotFoundException;
import model.fleet.Fleet;
import spring.exceptions.ErrorCode;
import util.UUIDUtil;
import spring.exceptions.InvalidInputException;
import spring.exceptions.NotAuthorizedException;

import static util.MyProperties.PATH_FLEETS;
import static util.MyProperties.getProperty;

/**
 * This is a bean class as specified in the API specification
 */
public class RESTFleet extends RESTAbstractModel<Fleet> {

    private String company;
    private String name;

    public RESTFleet() {
    }

    public RESTFleet(Fleet fleet) {
        super(fleet.getUuid(), getProperty(PATH_FLEETS));
        company = UUIDUtil.UUIDToNumberString(fleet.getOwner().getUuid());
        name = fleet.getName();
    }

    public RESTFleet(String id, String company, String name, String createdAt, String updatedAt, String lastUpdatedBy, String url) {
        setId(id);
        this.company = company;
        this.name = name;
    }

    public Fleet translate(ControllerManager manager) throws UnAuthorizedException, DataAccessException, ConstraintViolationException {
        Fleet fleet = new Fleet();
        fleet.setName(getName());
        try {
            CustomerController controller = manager.getCustomerController();
            fleet.setOwner(controller.get(UUIDUtil.toUUID(getCompany())));
        } catch (ObjectNotFoundException e) {
            throw new ConstraintViolationException("company", ErrorCode.NOT_FOUND.toString());
        }
        fleet.setUuid(UUIDUtil.toUUID(getId()));
        return fleet;
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
