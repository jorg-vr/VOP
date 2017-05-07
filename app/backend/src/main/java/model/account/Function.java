package model.account;

import model.history.EditableObject;
import model.history.LogResource;
import model.identity.Company;

import java.time.LocalDateTime;
import java.util.UUID;

/**
 * Created by sam on 3/10/17.
 */
public class Function implements EditableObject<Function>, java.io.Serializable {
    private Company company;
    private Role role;
    private User user;
    private String name;
    private LocalDateTime startDate;
    private LocalDateTime endDate;
    private UUID uuid;

    public Function() {
    }

    public Function(Company company, Role role, User user, LocalDateTime startDate, LocalDateTime endDate) {
        this.company = company;
        this.role = role;
        this.user = user;
        this.startDate = startDate;
        this.endDate = endDate;
    }

    public Function(Company company, Role role, User user, LocalDateTime startDate, LocalDateTime endDate, UUID uuid) {
        this(company, role, user, startDate, endDate);
        this.uuid = uuid;
    }


    public Company getCompany() {
        return company;
    }

    public void setCompany(Company company) {
        this.company = company;
    }

    public Role getRole() {
        return role;
    }

    public void setRole(Role role) {
        this.role = role;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
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

    public void setUuid(UUID uuid) {
        this.uuid = uuid;
    }

    @Override
    public UUID getUuid() {
        return uuid;
    }

    @Override
    public Function copy() {
        return null;
    }

    @Override
    public LogResource getLogResource() {
        return LogResource.FUNCTION;
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
        return getUuid().hashCode();
    }
}
