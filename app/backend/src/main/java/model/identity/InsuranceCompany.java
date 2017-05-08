package model.identity;

import model.insurance.Contract;
import model.insurance.Surety;

import java.util.ArrayList;
import java.util.Collection;
import java.util.UUID;


public class InsuranceCompany extends Company implements java.io.Serializable {

    private Collection<Contract> contracts;
    private Collection<Surety> sureties = new ArrayList<>();

    public InsuranceCompany() {
        this.contracts = new ArrayList<>();
        super.setCompanyType(CompanyType.INSURANCE_COMPANY);
    }

    public InsuranceCompany(Address address, String phoneNumber, String name, String btwNumber) {
        super(address, phoneNumber, name, btwNumber, CompanyType.INSURANCE_COMPANY);
        this.contracts = new ArrayList<>();
    }

    public InsuranceCompany(Address address, String phoneNumber, String name, String btwNumber, Collection<Contract> contracts) {
        super(address, phoneNumber, name, btwNumber, CompanyType.INSURANCE_COMPANY);
        this.contracts = contracts;
    }

    public InsuranceCompany(Address address, String phoneNumber, String name, String btwNumber, CompanyType companyType, Collection<Contract> contracts) {
        super(address, phoneNumber, name, btwNumber, companyType);
        this.contracts = contracts;
    }

    public InsuranceCompany(UUID id, Address address, String phoneNumber, String name, String btwNumber, CompanyType companyType) {
        super(id, address, phoneNumber, name, btwNumber, companyType);

    }

    public Collection<Contract> getContracts() {
        return contracts;
    }

    public void setContracts(Collection<Contract> contracts) {
        this.contracts = contracts;
    }

    public Collection<Surety> getSureties() {
        return sureties;
    }

    public void setSureties(Collection<Surety> sureties) {
        this.sureties = sureties;
    }
}
