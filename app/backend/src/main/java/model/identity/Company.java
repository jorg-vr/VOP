package model.identity;


import model.history.EditableObject;
import model.history.LogResource;

import java.util.UUID;

public class Company extends Identity implements java.io.Serializable {


    private Periodicity invoicePeriodicity;

    private Periodicity statementPeriodicity;

    private String name;

    private String btwNumber;

    private CompanyType companyType;

    public Company() {
    }

    public Company(UUID uuid) {
        super(uuid);
    }

    public Company(Address address, String phoneNumber, String name, String btwNumber, CompanyType companyType) {
        super(address, phoneNumber);
        this.name = name;
        this.btwNumber = btwNumber;
        this.companyType = companyType;
    }

    public Company(Address address, String phoneNumber, String name, String btwNumber, CompanyType companyType, Periodicity invoicePeriodicity, Periodicity statementPeriodicity) {
        super(address, phoneNumber);
        this.name = name;
        this.btwNumber = btwNumber;
        this.companyType = companyType;
        this.invoicePeriodicity = invoicePeriodicity;
        this.statementPeriodicity = statementPeriodicity;
    }

    public Company(UUID id, Address address, String phoneNumber, String name, String btwNumber, CompanyType companyType) {
        super(id, address, phoneNumber);
        this.name = name;
        this.btwNumber = btwNumber;
        this.companyType = companyType;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getBtwNumber() {
        return btwNumber;
    }

    public void setBtwNumber(String btwNumber) {
        this.btwNumber = btwNumber;
    }

    public CompanyType getCompanyType() {
        return companyType;
    }

    public void setCompanyType(CompanyType companyType) {
        this.companyType = companyType;
    }

    public Periodicity getStatementPeriodicity() {
        return statementPeriodicity;
    }

    public void setStatementPeriodicity(Periodicity statementPeriodicity) {
        this.statementPeriodicity = statementPeriodicity;
    }

    public Periodicity getInvoicePeriodicity() {
        return invoicePeriodicity;
    }

    public void setInvoicePeriodicity(Periodicity invoicePeriodicity) {
        this.invoicePeriodicity = invoicePeriodicity;
    }

    @Override
    public EditableObject copy() {
        return null;
    }

    @Override
    public LogResource getLogResource() {
        return LogResource.COMPANY;
    }
}
