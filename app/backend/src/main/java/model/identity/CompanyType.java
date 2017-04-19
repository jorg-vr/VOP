package model.identity;


import model.Factory;

public enum CompanyType {

    CUSTOMER(Customer::new),
    LEASING_COMPANY(LeasingCompany::new),
    INSURANCE_COMPANY(InsuranceCompany::new);

    private Factory<Company> factory;

    CompanyType(Factory<Company> factory) {
        this.factory = factory;
    }

    public Factory<Company> getFactory() {
        return factory;
    }
}
