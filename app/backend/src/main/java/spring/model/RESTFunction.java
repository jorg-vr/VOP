package spring.model;

import controller.CustomerController;
import controller.RoleController;
import controller.UserController;
import controller.exceptions.UnAuthorizedException;
import dao.interfaces.DataAccessException;
import model.account.Function;
import model.account.Role;
import model.identity.Company;
import spring.controller.UUIDUtil;
import spring.exceptions.InvalidInputException;
import spring.exceptions.NotAuthorizedException;

/**
 * Created by Jarre on 6-4-2017.
 */
public class RESTFunction extends RESTAbstractModel<Function> {

    private final static String PATH_FUNCTIONS = "/functions";

    private String company;
    private String companyName;
    private String role;
    private String roleName;
    private String user;

    public RESTFunction(){

    }

    public RESTFunction(Function function){
        super(function.getUuid(), PATH_FUNCTIONS);
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

    public RESTFunction(String id, String company, String role, String user, String updatedAt, String lastUpdatedBy, String url){
        setId(id);
        this.company = company;
        this.role = role;
        this.user = user;
    }

    @Override
    public Function translate(Function authorityFunction) {
        Function function = new Function();
        try {
            try (CustomerController customerController = new CustomerController(authorityFunction)) {
                function.setCompany(customerController.get(UUIDUtil.toUUID(getCompany())));
            } catch (DataAccessException e) {
                throw new InvalidInputException("company");
            }
            try (RoleController roleController = new RoleController(authorityFunction)) {
                function.setRole(roleController.get(UUIDUtil.toUUID(getRole())));
            } catch (DataAccessException e) {
                throw new InvalidInputException("role");
            }
            try (UserController userController = new UserController(authorityFunction)) {
                function.setUser(userController.get(UUIDUtil.toUUID(getUser())));
            } catch (DataAccessException e) {
                throw new InvalidInputException("user");
            }
        } catch (UnAuthorizedException e){
            throw new NotAuthorizedException();
        }
        function.setUuid(UUIDUtil.toUUID(getId()));
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
