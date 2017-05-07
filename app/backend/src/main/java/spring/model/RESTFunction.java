package spring.model;

import controller.ControllerManager;
import controller.CustomerController;
import controller.RoleController;
import controller.UserController;
import controller.exceptions.UnAuthorizedException;
import dao.exceptions.ConstraintViolationException;
import dao.exceptions.DataAccessException;
import dao.exceptions.ObjectNotFoundException;
import model.account.Function;
import model.account.Role;
import model.identity.Company;
import spring.exceptions.ErrorCode;
import util.UUIDUtil;

import java.util.HashMap;
import java.util.Map;

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
    public Function translate(ControllerManager manager) throws DataAccessException, UnAuthorizedException, ConstraintViolationException {
        Function function = new Function();
        function.setUuid(UUIDUtil.toUUID(getId()));

        Map<String, String> violations = new HashMap<>();
        try {
            CustomerController customerController = manager.getCustomerController();
            function.setCompany(customerController.get(UUIDUtil.toUUID(getCompany())));
        } catch (ObjectNotFoundException e) {
            violations.put("company", ErrorCode.NOT_FOUND.toString());
        }
        try {
            RoleController roleController = manager.getRoleController();
            function.setRole(roleController.get(UUIDUtil.toUUID(getRole())));
        } catch (ObjectNotFoundException e) {
            violations.put("role", ErrorCode.NOT_FOUND.toString());
        }
        try {
            UserController userController = manager.getUserController();
            function.setUser(userController.get(UUIDUtil.toUUID(getUser())));
        } catch (ObjectNotFoundException e) {
            violations.put("user", ErrorCode.NOT_FOUND.toString());
        }
        if (violations.size() > 0) {
            throw new ConstraintViolationException(violations);
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
