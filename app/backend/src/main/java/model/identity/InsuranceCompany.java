package src.main.java.model.identity;

import src.main.java.model.insurance.Insurance;

import java.util.Collection;


public class InsuranceCompany extends Company implements java.io.Serializable {

    private Collection<Insurance> insurances;

    public InsuranceCompany() {
    }

    public InsuranceCompany(int id, Address address, String email, int phoneNumber, String name, int btwNumber, int bankAccountNumber, CompanyType companyType) {
        super(id, address, email, phoneNumber, name, btwNumber, bankAccountNumber, companyType);
    }

    public void addInsurance(Insurance insurance){

    }

    public Collection<Insurance> getInsurances() {
        return insurances;
    }

    public void setInsurances(Collection<Insurance> insurances) {
        this.insurances = insurances;
    }
}
