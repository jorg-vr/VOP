package model.identity;

import java.util.UUID;


public class LeasingCompany extends Company implements java.io.Serializable {

    public LeasingCompany() {
    }

    public LeasingCompany(UUID id, Address address, String email, String phoneNumber, String name, String btwNumber, String bankAccountNumber, CompanyType companyType) {
        super(id, address, email, phoneNumber, name, btwNumber, bankAccountNumber, companyType);
    }
}