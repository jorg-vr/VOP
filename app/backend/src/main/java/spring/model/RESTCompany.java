package spring.model;

import controller.ControllerManager;
import dao.exceptions.ConstraintViolationException;
import model.identity.Address;
import model.identity.Company;
import model.identity.CompanyType;
import model.identity.Periodicity;
import spring.exceptions.ErrorCode;
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
    private int paymentPeriod;
    private int facturationPeriod;

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
        if (company.getPaymentPeriod() != null) {
            this.paymentPeriod = company.getPaymentPeriod().getTime();
        }
        if (company.getFacturationPeriod() != null) {
            this.facturationPeriod = company.getFacturationPeriod().getTime();
        }
    }

    public RESTCompany(String id, String name, String vatNumber, String phoneNumber, RESTAddress address) {
        setId(id);
        this.name = name;
        this.vatNumber = vatNumber;
        this.phoneNumber = phoneNumber;
        this.address = address;
    }

    public Company translate(ControllerManager manager) throws ConstraintViolationException {
        Company company;
        try {
            company = CompanyType.valueOf(type).getFactory().create();
        } catch (IllegalArgumentException | NullPointerException e) {
            throw new ConstraintViolationException("type", ErrorCode.INVALID.toString());
        }

        Map<String, String> violations = new HashMap<>();
        Periodicity payment = Periodicity.fromTime(paymentPeriod);
        if (payment == null) {
            violations.put("paymentPeriod", ErrorCode.INVALID.toString());
        }
        company.setPaymentPeriod(payment);
        Periodicity facturation = Periodicity.fromTime(facturationPeriod);
        if (facturation == null) {
            violations.put("facturationPeriod", ErrorCode.INVALID.toString());
        }
        company.setFacturationPeriod(facturation);
        if (violations.size() > 0) {
            throw new ConstraintViolationException(violations);
        }

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

    public int getPaymentPeriod() {
        return paymentPeriod;
    }

    public void setPaymentPeriod(int paymentPeriod) {
        this.paymentPeriod = paymentPeriod;
    }

    public int getFacturationPeriod() {
        return facturationPeriod;
    }

    public void setFacturationPeriod(int facturationPeriod) {
        this.facturationPeriod = facturationPeriod;
    }
}


