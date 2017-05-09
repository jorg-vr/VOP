package model.account;

import model.history.EditableObject;
import model.identity.Company;

import java.time.LocalDateTime;
import java.util.UUID;

/**
 * Represents the relationship of a User, his Company and a given Role
 * Created by sam on 3/10/17.
 */
public class Function implements EditableObject, java.io.Serializable {
    /**
     * The company where the user works, should not be null
     */
    private Company company;

    /**
     * The role of the function, should not be null
     */
    private Role role;

    /**
     * Bidirectional relationship of User and Function, should not be null
     */
    private User user;

    /**
     * Name of the function
     */
    private String name;

    /**
     * The start date and end date of the function, if the date of today is not between those dates, the given function
     * should not apply
     */
    private LocalDateTime startDate;
    private LocalDateTime endDate;

    /**
     * Unique ID to represent this object
     */
    private UUID uuid;

    /**
     * Default constructor
     */
    public Function() {
    }

    /**
     * Constructor
     * @param company Company of the user
     * @param role The role
     * @param user The user
     * @param startDate The start date
     * @param endDate The end date
     */
    public Function(Company company, Role role, User user, LocalDateTime startDate, LocalDateTime endDate) {
        this.company = company;
        this.role = role;
        this.user = user;
        this.startDate = startDate;
        this.endDate = endDate;
    }

    /**
     * Gets the company
     * @return the company
     */
    public Company getCompany() {
        return company;
    }

    /**
     * Sets the company
     * @param company the company
     */
    public void setCompany(Company company) {
        this.company = company;
    }

    /**
     * Gets the role
     * @return the role
     */
    public Role getRole() {
        return role;
    }

    /**
     * Sets the role
     * @param role the role
     */
    public void setRole(Role role) {
        this.role = role;
    }

    /**
     * Gets the name
     * @return the name
     */
    public String getName() {
        return name;
    }

    /**
     * Sets the name
     * @param name the name
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * Gets the user
     * @return the user
     */
    public User getUser() {
        return user;
    }

    /**
     * Sets the user
     * @param user the user
     */
    public void setUser(User user) {
        this.user = user;
    }

    /**
     * Gets the start date
     * @return the start date
     */
    public LocalDateTime getStartDate() {
        return startDate;
    }

    /**
     * Sets the start date
     * @param startDate the start date
     */
    public void setStartDate(LocalDateTime startDate) {
        this.startDate = startDate;
    }

    /**
     * Gets the end date
     * @return the end date
     */
    public LocalDateTime getEndDate() {
        return endDate;
    }

    /**
     * Sets the end date
     * @param endDate the end date
     */
    public void setEndDate(LocalDateTime endDate) {
        this.endDate = endDate;
    }

    /**
     * Sets the id
     * @param uuid the id
     */
    public void setUuid(UUID uuid) {
        this.uuid = uuid;
    }

    /**
     * Gets the id
     * @return the id
     */
    @Override
    public UUID getUuid() {
        return uuid;
    }

    /**
     * Copies this object and sets all its fields
     * @return the copied object
     */
    @Override
    public EditableObject copy() {
        return null;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Function function = (Function) o;

        return getUuid().equals(function.getUuid());
    }

    @Override
    public int hashCode() {
        if(this.uuid!=null) {
            return getUuid().hashCode();
        }
        return super.hashCode();
    }
}
