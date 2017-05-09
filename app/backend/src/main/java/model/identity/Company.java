package model.identity;


import model.history.EditableObject;
import model.history.LogResource;

import java.util.UUID;

/**
 * Class representing a general company
 */
public class Company extends Identity implements java.io.Serializable {

    /**
     * Used to determine how often an invoice has to be send to the company
     */
    private Periodicity invoicePeriodicity;

    /**
     * Used to determine how often a statement has to be send to the company
     */
    private Periodicity statementPeriodicity;

    /**
     * The company name
     */
    private String name;

    /**
     * The VAT number
     */
    private String btwNumber;

    /**
     * The company type
     */
    private CompanyType companyType;

    /**
     * Default constructor
     */
    public Company() {
    }

    /**
     * Constructor
     * @param uuid the uuid
     */
    public Company(UUID uuid) {
        super(uuid);
    }

    /**
     * Constructor
     * @param address the address
     * @param phoneNumber the phone number
     * @param name the name
     * @param btwNumber the VAT number
     * @param companyType the company type
     */
    public Company(Address address, String phoneNumber, String name, String btwNumber, CompanyType companyType) {
        super(address, phoneNumber);
        this.name = name;
        this.btwNumber = btwNumber;
        this.companyType = companyType;
    }

    /**
     * Constructor
     * @param address the address
     * @param phoneNumber the phone number
     * @param name the company name
     * @param btwNumber the VAT-number
     * @param companyType the company type
     * @param invoicePeriodicity the invoice periodicity
     * @param statementPeriodicity the statement periodicity
     */
    public Company(Address address, String phoneNumber, String name, String btwNumber, CompanyType companyType, Periodicity invoicePeriodicity, Periodicity statementPeriodicity) {
        super(address, phoneNumber);
        this.name = name;
        this.btwNumber = btwNumber;
        this.companyType = companyType;
        this.invoicePeriodicity = invoicePeriodicity;
        this.statementPeriodicity = statementPeriodicity;
    }

    /**
     * Constructor
     * @param id the uuid
     * @param address the address
     * @param phoneNumber the phone number
     * @param name the company name
     * @param btwNumber the VAT-number
     * @param companyType the company type
     */
    public Company(UUID id, Address address, String phoneNumber, String name, String btwNumber, CompanyType companyType) {
        super(id, address, phoneNumber);
        this.name = name;
        this.btwNumber = btwNumber;
        this.companyType = companyType;
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
     * Gets the VAT-number
     * @return the VAT-number
     */
    public String getBtwNumber() {
        return btwNumber;
    }

    /**
     * Sets the VAT-number
     * @param btwNumber the VAT-number
     */
    public void setBtwNumber(String btwNumber) {
        this.btwNumber = btwNumber;
    }

    /**
     * Gets the company type
     * @return the company type
     */
    public CompanyType getCompanyType() {
        return companyType;
    }

    /**
     * Sets the company type
     * @param companyType the company type
     */
    public void setCompanyType(CompanyType companyType) {
        this.companyType = companyType;
    }

    /**
     * Gets the statement periodicity
     * @return the statement periodicity
     */
    public Periodicity getStatementPeriodicity() {
        return statementPeriodicity;
    }

    /**
     * Sets the statement periodicity
     * @param statementPeriodicity the statement periodicity
     */
    public void setStatementPeriodicity(Periodicity statementPeriodicity) {
        this.statementPeriodicity = statementPeriodicity;
    }

    /**
     * Gets the invoice periodicity
     * @return the invoice periodicity
     */
    public Periodicity getInvoicePeriodicity() {
        return invoicePeriodicity;
    }

    /**
     * Sets the invoice periodicity
     * @param invoicePeriodicity the invoice periodicity
     */
    public void setInvoicePeriodicity(Periodicity invoicePeriodicity) {
        this.invoicePeriodicity = invoicePeriodicity;
    }

    @Override
    public LogResource getLogResource() {
        return LogResource.COMPANY;
    }

    /**
     * Copies the object
     * @return the copy
     */
    @Override
    public EditableObject copy() {
        Company company = new Company();
        company.setName(getName());
        company.setCompanyType(getCompanyType());
        company.setAddress((Address)getAddress().copy());
        company.setInvoicePeriodicity(getInvoicePeriodicity());
        company.setStatementPeriodicity(getStatementPeriodicity());
        company.setBtwNumber(getBtwNumber());
        company.setUuid(getUuid());
        company.setPhoneNumber(getPhoneNumber());
        return company;
    }
}
