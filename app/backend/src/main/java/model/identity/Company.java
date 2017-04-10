package model.identity;


import java.util.UUID;

public class Company extends Identity implements java.io.Serializable {

    private String name;

    private String btwNumber;

    private String bankAccountNumber;

    private CompanyType companyType;

    public Company() {
    }

    public Company(Address address, String phoneNumber, String name, String btwNumber, CompanyType companyType) {
        super(address, phoneNumber);
        this.name = name;
        this.btwNumber = btwNumber;
        this.bankAccountNumber = bankAccountNumber;
        this.companyType = companyType;
    }

    public Company(UUID id, Address address, String phoneNumber, String name, String btwNumber, CompanyType companyType) {
        super(id, address, phoneNumber);
        this.name = name;
        this.btwNumber = btwNumber;
        this.bankAccountNumber = bankAccountNumber;
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

    public String getBankAccountNumber() {
        return bankAccountNumber;
    }

    public void setBankAccountNumber(String bankAccountNumber) {
        this.bankAccountNumber = bankAccountNumber;
    }

    public CompanyType getCompanyType() {
        return companyType;
    }

    public void setCompanyType(CompanyType companyType) {
        this.companyType = companyType;
    }
}
