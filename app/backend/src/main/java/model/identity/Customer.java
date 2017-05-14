package model.identity;

import model.CommissionContainer;
import model.fleet.Fleet;
import model.history.EditableObject;
import model.history.LogResource;
import model.insurance.Contract;
import model.insurance.SuretyType;

import java.util.*;


/**
 * Class representing a Customer having one or more fleets
 */
public class Customer extends Company implements java.io.Serializable, CommissionContainer {

    /**
     * Collection of fleets, most of the time only 1
     */
    private Collection<Fleet> fleets;

    /**
     * All contracts the customer has
     */
    private Collection<Contract> contracts;

    /**
     * Commissions can be determined by VehicleType, by Customer and By vehicle, Default is vehicleType,
     * so commissions can be null in the object
     */
    private Map<SuretyType, Double> commissions;

    /**
     * Default Constructor
     */
    public Customer() {
        super.setCompanyType(CompanyType.CUSTOMER);
        this.fleets = new ArrayList<>();
        commissions = new HashMap<>();
    }

    /**
     * Constructor
     * @param uuid the uuid
     */
    public Customer(UUID uuid) {
        super(uuid);
        this.fleets = new ArrayList<>();
        commissions = new HashMap<>();
    }

    /**
     * Constructor
     * @param address the address
     * @param phoneNumber the phone number
     * @param name the company name
     * @param btwNumber the VAT-number
     */
    public Customer(Address address, String phoneNumber, String name, String btwNumber) {
        super(address, phoneNumber, name, btwNumber, CompanyType.CUSTOMER);
        this.fleets = new ArrayList<>();
        commissions = new HashMap<>();
    }

    /**
     * Constructor
     * @param address the address
     * @param phoneNumber the phone number
     * @param name the company name
     * @param btwNumber the VAT-number
     * @param invoicePeriodicity the invoice periodicity
     * @param statementPeriodicity the statement periodicity
     */
    public Customer(Address address, String phoneNumber, String name, String btwNumber, Periodicity invoicePeriodicity, Periodicity statementPeriodicity) {
        super(address, phoneNumber, name, btwNumber, CompanyType.CUSTOMER, invoicePeriodicity, statementPeriodicity);
        this.fleets = new ArrayList<>();
        commissions = new HashMap<>();
    }

    /**
     * Constructor
     * @param address the address
     * @param phoneNumber the phone number
     * @param name the name
     * @param btwNumber the VAT-number
     * @param companyType the company type
     */
    public Customer(Address address, String phoneNumber, String name, String btwNumber, CompanyType companyType) {
        super(address, phoneNumber, name, btwNumber, companyType);
        this.fleets = new ArrayList<>();
        commissions = new HashMap<>();
    }

    /**
     * Constructor
     * @param id the id
     * @param address the address
     * @param phoneNumber the phone number
     * @param name the company name
     * @param btwNumber the VAT-number
     * @param companyType the company type
     */
    public Customer(UUID id, Address address, String phoneNumber, String name, String btwNumber, CompanyType companyType) {
        super(id, address, phoneNumber, name, btwNumber, companyType);
        this.fleets = new ArrayList<>();
        commissions = new HashMap<>();
    }

    /**
     * Gets the fleets
     * @return the fleets
     */
    public Collection<Fleet> getFleets() {
        return fleets;
    }

    /**
     * Sets the fleets
     * @param fleets the fleets
     */
    public void setFleets(Collection<Fleet> fleets) {
        this.fleets = fleets;
    }

    /**
     * Gets the contracts
     * @return the contracts
     */
    public Collection<Contract> getContracts() {
        return contracts;
    }

    /**
     * Sets the contracts
     * @param contracts the contracts
     */
    public void setContracts(Collection<Contract> contracts) {
        this.contracts = contracts;
    }

    /**
     * Gets the commissions
     * @return the commissions
     */
    public Map<SuretyType, Double> getCommissions() {
        return commissions;
    }

    /**
     * Sets the commissions
      * @param commissions the commissions
     */
    public void setCommissions(Map<SuretyType, Double> commissions) {
        this.commissions = commissions;
    }

    /**
     * Gets a specific commissions
     * @param suretyType the type of which the specific commission should be retrieved
     * @return the value of the specific commission for the customer for given suretyType. null if there is no specific commission
     */
    public Double getSpecificCommission(SuretyType suretyType) {
        return commissions.get(suretyType);
    }

    /**
     * @param suretyType the type of which the specific commission should be retrieved
     * @param commission the value of the specific commission for the customer for given suretyType
     */
    public void setSpecificCommission(SuretyType suretyType, double commission) {
        if(commissions == null ){
            commissions = new HashMap<>();
        }
        commissions.put(suretyType, commission);
    }

    /**
     * Removes a specific
     * @param suretyType the surety type of the commissions which has to be removed
     */
    public void removeSpecificCommission(SuretyType suretyType) {
        commissions.remove(suretyType);
    }

    @Override
    public LogResource getLogResource() {
        return LogResource.CUSTOMER;
    }

    /**
     * Copies the object
     * @return the copy
     */
    @Override
    public EditableObject copy() {
        Customer customer = new Customer();
        customer.setFleets(new ArrayList<>(fleets));
        customer.setContracts(new ArrayList<>(contracts));
        customer.setCommissions(new HashMap<>(commissions));
        customer.setName(getName());
        customer.setCompanyType(getCompanyType());
        customer.setAddress((Address)getAddress().copy());
        customer.setPaymentPeriod(getPaymentPeriod());
        customer.setFacturationPeriod(getFacturationPeriod());
        customer.setBtwNumber(getBtwNumber());
        customer.setUuid(getUuid());
        customer.setPhoneNumber(getPhoneNumber());
        return customer;
    }
}
