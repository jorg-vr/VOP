package model.identity;


import java.util.UUID;

public class Company extends Identity implements java.io.Serializable {

    private String name;

    private String btwNumber;

    private String bankAccountNumber;

    private CompanyType companyType;

    public Company() {
    }

    public Company(UUID uuid, Address address, String email, int phoneNumber, String name, String btwNumber, String bankAccountNumber, CompanyType companyType) {
        super(uuid, address, email, phoneNumber);
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
