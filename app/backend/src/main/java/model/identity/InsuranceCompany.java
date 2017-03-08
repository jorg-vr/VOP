package model.identity;

import model.insurance.Insurance;

import java.util.Collection;
import java.util.UUID;


public class InsuranceCompany extends Company implements java.io.Serializable {

    private Collection<Insurance> insurances;

    public InsuranceCompany() {
    }

    public InsuranceCompany(UUID uuid, Address address, String email, int phoneNumber, String name, String btwNumber, String bankAccountNumber, CompanyType companyType) {
        super(uuid, address, email, phoneNumber, name, btwNumber, bankAccountNumber, companyType);
    }

    public boolean addInsurance(Insurance insurance){
        if(insurances.contains(insurance)){
            return false;
        }
        insurances.add(insurance);
        return true;
    }

    public Collection<Insurance> getInsurances() {
        return insurances;
    }

    public void setInsurances(Collection<Insurance> insurances) {
        this.insurances = insurances;
    }
}
