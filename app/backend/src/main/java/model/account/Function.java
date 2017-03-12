package model.account;

import model.history.EditableObject;
import model.identity.Company;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.UUID;

/**
 * Created by sam on 3/10/17.
 */
public class Function implements EditableObject{
    private Company company;
    private Role role;
    private Account account;
    private LocalDateTime startDate;
    private LocalDateTime endDate;
    private UUID uuid;

    public Function(){}

    public Function(Company company, Role role, Account account, LocalDateTime startDate, LocalDateTime endDate) {
        this.company = company;
        this.role = role;
        this.account = account;
        this.startDate = startDate;
        this.endDate = endDate;
        this.uuid = UUID.randomUUID();
    }

    public Function(Company company, Role role, Account account, LocalDateTime startDate, LocalDateTime endDate, UUID uuid) {
        this(company,role,account,startDate,endDate);
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

    public Account getAccount() {
        return account;
    }

    public void setAccount(Account account) {
        this.account = account;
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
    public EditableObject copy() {
        return null;
    }
}
