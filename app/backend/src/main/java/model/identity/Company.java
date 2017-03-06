package src.main.java.model.identity;


public class Company extends Identity{

    private String name;

    private int btwNumber;

    private int bankAccountNumber;

    private CompanyType companyType;

    public Company(int id, Address address, String email, int phoneNumber, String name, int btwNumber, int bankAccountNumber, CompanyType companyType) {
        super(id, address, email, phoneNumber);
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

    public int getBtwNumber() {
        return btwNumber;
    }

    public void setBtwNumber(int btwNumber) {
        this.btwNumber = btwNumber;
    }

    public int getBankAccountNumber() {
        return bankAccountNumber;
    }

    public void setBankAccountNumber(int bankAccountNumber) {
        this.bankAccountNumber = bankAccountNumber;
    }

    public CompanyType getCompanyType() {
        return companyType;
    }

    public void setCompanyType(CompanyType companyType) {
        this.companyType = companyType;
    }
}
