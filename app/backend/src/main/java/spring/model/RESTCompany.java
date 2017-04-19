package spring.model;

import model.Factory;
import model.account.Function;
import model.identity.*;
import util.UUIDUtil;

import java.util.HashMap;
import java.util.Map;

import static util.MyProperties.PATH_COMPANIES;
import static util.MyProperties.getProperty;

/**
 * This is a bean class as specified in the API specification
 */
public class RESTCompany extends RESTAbstractModel<Company> {

    private String name;
    private String vatNumber;
    private String phoneNumber;
    private RESTAddress address;
    private String type;

    public RESTCompany() {
    }

    /**
     * @param company the company that the fields of this object should be based on
     */
    public RESTCompany(Company company) {
        super(company.getUuid(), getProperty(PATH_COMPANIES));
        this.name = company.getName();
        this.vatNumber = company.getBtwNumber();
        this.phoneNumber = company.getPhoneNumber();
        Address addr = company.getAddress();
        if (addr != null) {
            this.address = new RESTAddress(addr);
        }
        if (company.getCompanyType() != null) {
            this.type = company.getCompanyType().toString();
        }
    }

    public RESTCompany(String id, String name, String vatNumber, String phoneNumber, RESTAddress address) {
        setId(id);
        this.name = name;
        this.vatNumber = vatNumber;
        this.phoneNumber = phoneNumber;
        this.address = address;
    }

    public Company translate(Function function) {
        Company company = CompanyType.valueOf(type).getFactory().create();
        company.setName(getName());
        company.setBtwNumber(getVatNumber());
        company.setPhoneNumber(getPhoneNumber());
        company.setAddress(getAddress().translate());
        company.setUuid(UUIDUtil.toUUID(getId()));
        return company;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getVatNumber() {
        return vatNumber;
    }

    public void setVatNumber(String vatNumber) {
        this.vatNumber = vatNumber;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public RESTAddress getAddress() {
        return address;
    }

    public void setAddress(RESTAddress address) {
        this.address = address;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }
}


