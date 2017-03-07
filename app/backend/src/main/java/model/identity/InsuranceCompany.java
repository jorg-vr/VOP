package model.identity;

import model.insurance.Insurance;

import java.util.Collection;


public class InsuranceCompany extends Company {

    private Collection<Insurance> insurances;

    public InsuranceCompany(String id, Address address, String email, int phoneNumber, String name, String btwNumber, String bankAccountNumber, CompanyType companyType) {
        super(id, address, email, phoneNumber, name, btwNumber, bankAccountNumber, companyType);
    }

    /**
     *
     * @param insurance
     */
    public void addInsurance(Insurance insurance){

    }
}
