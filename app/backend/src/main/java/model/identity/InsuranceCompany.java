package model.identity;

import model.history.EditableObject;
import model.history.LogResource;

import model.insurance.Contract;
import model.insurance.Surety;

import java.util.ArrayList;
import java.util.Collection;
import java.util.UUID;


public class InsuranceCompany extends Company implements java.io.Serializable {

    /**
     * All the contracts customers have with the InsuranceCompany
     */
    private Collection<Contract> contracts;

    /**
     * All the sureties the InsuranceCompany has
     */
    private Collection<Surety> sureties = new ArrayList<>();

    /**
     * Default constructor
     */
    public InsuranceCompany() {
        this.contracts = new ArrayList<>();
        super.setCompanyType(CompanyType.INSURANCE_COMPANY);
    }

    /**
     * Constructor
     * @param address the address
     * @param phoneNumber the phone number
     * @param name the company name
     * @param btwNumber the VAT-number
     */
    public InsuranceCompany(Address address, String phoneNumber, String name, String btwNumber) {
        super(address, phoneNumber, name, btwNumber, CompanyType.INSURANCE_COMPANY);
        this.contracts = new ArrayList<>();
    }

    /**
     * Constructor
     * @param address the address
     * @param phoneNumber the phonenumber
     * @param name the name
     * @param btwNumber the VAT-number
     * @param contracts the contracts
     */
    public InsuranceCompany(Address address, String phoneNumber, String name, String btwNumber, Collection<Contract> contracts) {
        super(address, phoneNumber, name, btwNumber, CompanyType.INSURANCE_COMPANY);
        this.contracts = contracts;
    }

    /**
     * Constructor
     * @param address the address
     * @param phoneNumber the phone number
     * @param name the name
     * @param btwNumber the VAT-number
     * @param companyType the company type
     * @param contracts the contracts
     */
    public InsuranceCompany(Address address, String phoneNumber, String name, String btwNumber, CompanyType companyType, Collection<Contract> contracts) {
        super(address, phoneNumber, name, btwNumber, companyType);
        this.contracts = contracts;
    }

    /**
     * Constructor
     * @param id the id
     * @param address the address
     * @param phoneNumber the phoneNumber
     * @param name the name
     * @param btwNumber the VAT-number
     * @param companyType the company type
     */
    public InsuranceCompany(UUID id, Address address, String phoneNumber, String name, String btwNumber, CompanyType companyType) {
        super(id, address, phoneNumber, name, btwNumber, companyType);

    }

    @Override
    public LogResource getLogResource() {
        return LogResource.INSURANCE_COMPANY;
    }

    /**
     * Get the contracts
     * @return the contracts
     */
    public Collection<Contract> getContracts() {
        return contracts;
    }

    /**
     * Sets the contracts
     * @param contracts the contracts
     */
    public void setContracts(Collection<Contract> contracts) {
        this.contracts = contracts;
    }

    /**
     * Gets the sureties
     * @return the sureties
     */
    public Collection<Surety> getSureties() {
        return sureties;
    }

    /**
     * Sets the sureties
     * @param sureties the sureties
     */
    public void setSureties(Collection<Surety> sureties) {
        this.sureties = sureties;
    }

    /**
     * Copies the object
     * @return the copy
     */
    @Override
    public EditableObject copy() {
        InsuranceCompany company = new InsuranceCompany();
        company.setName(getName());
        company.setCompanyType(getCompanyType());
        company.setAddress((Address)getAddress().copy());
        company.setInvoicePeriodicity(getInvoicePeriodicity());
        company.setStatementPeriodicity(getStatementPeriodicity());
        company.setBtwNumber(getBtwNumber());
        company.setUuid(getUuid());
        company.setPhoneNumber(getPhoneNumber());

        company.setContracts(new ArrayList<>(contracts));
        company.setSureties(new ArrayList<>(sureties));

        return company;
    }
}
