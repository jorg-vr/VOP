package spring.model;


import controller.AccountController;
import controller.CustomerController;
import controller.RoleController;
import dao.interfaces.DataAccessException;
import model.account.Function;
import spring.controller.UUIDUtil;
import spring.exceptions.InvalidInputException;

import java.time.LocalDateTime;

import static spring.controller.UUIDUtil.UUIDToNumberString;

/**
 * This is a bean class as specified in the API specification
 */
public class RESTRole extends RESTAbstractModel {



    public static final String PATH_ROLE = "/roles";


    private String company;

    private String name;

    private String permissions;

    private String user;

    private LocalDateTime startDate;

    private LocalDateTime endDate;

    private LocalDateTime createdAt;

    private LocalDateTime updatedAt;

    private String url;

    public RESTRole() {
    }

    public RESTRole(Function function){
         super(function.getUuid(),PATH_ROLE);
         setPermissions(UUIDToNumberString(function.getRole().getUuid()));
         setUser(UUIDToNumberString(function.getAccount().getUuid()));
         setCompany(UUIDToNumberString(function.getCompany().getUuid()));
         setStartDate(function.getStartDate());
         setEndDate(function.getEndDate());
         setName(function.getName());
     }

    public Function translate(){
        Function function=new Function();
        function.setUuid(UUIDUtil.toUUID(getId()));
        try {
            function.setAccount(new AccountController().get(UUIDUtil.toUUID(getUser())));
        } catch (DataAccessException e) {
            throw new InvalidInputException("user");
        }
        try {
            function.setCompany(new CustomerController().get(UUIDUtil.toUUID(getCompany())));
        } catch (DataAccessException e) {
            throw new InvalidInputException("company");
        }
        try {
            function.setRole(new RoleController().get(UUIDUtil.toUUID(getPermissions())));
        } catch (DataAccessException e) {
            throw new InvalidInputException("name");
        }
        function.setName(getName());
        return function;
    }


    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCompany() {
        return company;
    }

    public void setCompany(String company) {
        this.company = company;
    }

    public String getPermissions() {
        return permissions;
    }

    public void setPermissions(String permissions) {
        this.permissions = permissions;
    }

    public String getUser() {
        return user;
    }

    public void setUser(String user) {
        this.user = user;
    }

    public LocalDateTime getStartDate() {
        return startDate;
    }

    public void setStartDate(LocalDateTime startDate) {
        this.startDate = startDate;
    }

    public LocalDateTime getEndDate() {
        return endDate;
    }

    public void setEndDate(LocalDateTime endDate) {
        this.endDate = endDate;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }

    public LocalDateTime getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(LocalDateTime updatedAt) {
        this.updatedAt = updatedAt;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

}
