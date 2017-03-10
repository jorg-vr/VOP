package model.account;

import model.identity.Company;

import java.time.LocalDate;

/**
 * Created by sam on 3/10/17.
 */
public class Function {
    private Company company;
    private Role role;
    private Account account;
    private LocalDate startDate;
    private LocalDate endDate;


    public Function(Company company, Role role, Account account, LocalDate startDate, LocalDate endDate) {
        this.company = company;
        this.role = role;
        this.account = account;
        this.startDate = startDate;
        this.endDate = endDate;
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

    public LocalDate getStartDate() {
        return startDate;
    }

    public void setStartDate(LocalDate startDate) {
        this.startDate = startDate;
    }

    public LocalDate getEndDate() {
        return endDate;
    }

    public void setEndDate(LocalDate endDate) {
        this.endDate = endDate;
    }
}
