package spring.model;

import controller.ControllerManager;
import controller.CustomerController;
import controller.RoleController;
import controller.UserController;
import controller.exceptions.UnAuthorizedException;
import dao.interfaces.DataAccessException;
import model.account.Function;
import model.account.Role;
import model.identity.Company;
import util.UUIDUtil;
import spring.exceptions.InvalidInputException;
import spring.exceptions.NotAuthorizedException;

import static util.MyProperties.PATH_FUNCTIONS;
import static util.MyProperties.getProperty;

/**
 * Created by Jarre on 6-4-2017.
 */
public class RESTFunction extends RESTAbstractModel<Function> {

    private String company;
    private String companyName;
    private String role;
    private String roleName;
    private String user;

    public RESTFunction() {

    }

    public RESTFunction(Function function) {
        super(function.getUuid(), getProperty(PATH_FUNCTIONS));
        Company company = function.getCompany();
        if (company != null) {
            this.company = UUIDUtil.UUIDToNumberString(company.getUuid());
            this.companyName = company.getName();
        }
        Role role = function.getRole();
        this.role = UUIDUtil.UUIDToNumberString(role.getUuid());
        this.roleName = role.getName();
        this.user = UUIDUtil.UUIDToNumberString(function.getUser().getUuid());
    }

    public RESTFunction(String id, String company, String role, String user, String updatedAt, String lastUpdatedBy, String url) {
        setId(id);
        this.company = company;
        this.role = role;
        this.user = user;
    }

    @Override
    public Function translate(ControllerManager manager) {
        Function function = new Function();
        function.setUuid(UUIDUtil.toUUID(getId()));
        try {
            try {
                CustomerController customerController = manager.getCustomerController();
                function.setCompany(customerController.get(UUIDUtil.toUUID(getCompany())));
            } catch (DataAccessException e) {
                throw new InvalidInputException("company");
            }
            try {
                RoleController roleController = manager.getRoleController();
                function.setRole(roleController.get(UUIDUtil.toUUID(getRole())));
            } catch (DataAccessException e) {
                throw new InvalidInputException("role");
            }
            try {
                UserController userController = manager.getUserController();
                function.setUser(userController.get(UUIDUtil.toUUID(getUser())));
            } catch (DataAccessException e) {
                throw new InvalidInputException("user");
            }
        } catch (UnAuthorizedException e) {
            throw new NotAuthorizedException();
        }
        return function;
    }

    public String getCompany() {
        return company;
    }

    public void setCompany(String company) {
        this.company = company;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public String getUser() {
        return user;
    }

    public void setUser(String user) {
        this.user = user;
    }

    public String getCompanyName() {
        return companyName;
    }

    public void setCompanyName(String companyName) {
        this.companyName = companyName;
    }

    public String getRoleName() {
        return roleName;
    }

    public void setRoleName(String roleName) {
        this.roleName = roleName;
    }
}
