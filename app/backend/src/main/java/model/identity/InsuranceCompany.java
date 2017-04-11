package model.identity;

import model.insurance.Insurance;
import model.insurance.Surety;

import java.util.Collection;
import java.util.UUID;


public class InsuranceCompany extends Company implements java.io.Serializable {

    private Collection<Insurance> insurances;

    public InsuranceCompany() {
    }

    public InsuranceCompany(Address address, String phoneNumber, String name, String btwNumber, Collection<Insurance> insurances) {
        super(address, phoneNumber, name, btwNumber, CompanyType.INSURANCE_COMPANY);
        this.insurances = insurances;
    }

    public InsuranceCompany(Address address, String phoneNumber, String name, String btwNumber, CompanyType companyType, Collection<Insurance> insurances) {
        super(address, phoneNumber, name, btwNumber, companyType);
        this.insurances = insurances;
    }

    public InsuranceCompany(UUID id, Address address, String phoneNumber, String name, String btwNumber, CompanyType companyType) {
        super(id, address, phoneNumber, name, btwNumber, companyType);

    }

    public Collection<Insurance> getInsurances() {
        return insurances;
    }

    public void setInsurances(Collection<Insurance> insurances) {
        this.insurances = insurances;
    }
}
