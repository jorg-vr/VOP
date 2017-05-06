package model.identity;

import model.fleet.Fleet;
import model.insurance.Contract;
import model.insurance.SuretyType;

import java.util.*;

public class Customer extends Company implements java.io.Serializable {

    private Collection<Fleet> fleets;

    private Collection<Contract> contracts;

    private Map<SuretyType, Double> commissions;


    public Customer() {
        super.setCompanyType(CompanyType.CUSTOMER);
        this.fleets = new ArrayList<>();
        commissions = new HashMap<>();
    }

    public Customer(UUID uuid) {
        super(uuid);
        this.fleets = new ArrayList<>();
        commissions = new HashMap<>();
    }

    public Customer(Address address, String phoneNumber, String name, String btwNumber) {
        super(address, phoneNumber, name, btwNumber, CompanyType.CUSTOMER);
        this.fleets = new ArrayList<>();
        commissions = new HashMap<>();
    }

    /**
     *
     * @param address
     * @param phoneNumber
     * @param name
     * @param btwNumber
     * @param invoicePeriodicity
     * @param statementPeriodicity
     */
    public Customer(Address address, String phoneNumber, String name, String btwNumber, Periodicity invoicePeriodicity, Periodicity statementPeriodicity) {
        super(address, phoneNumber, name, btwNumber, CompanyType.CUSTOMER, invoicePeriodicity, statementPeriodicity);
        this.fleets = new ArrayList<>();
        commissions = new HashMap<>();
    }

    public Customer(Address address, String phoneNumber, String name, String btwNumber, CompanyType companyType) {
        super(address, phoneNumber, name, btwNumber, companyType);
        this.fleets = new ArrayList<>();
        commissions = new HashMap<>();
    }

    public Customer(UUID id, Address address, String phoneNumber, String name, String btwNumber, CompanyType companyType) {
        super(id, address, phoneNumber, name, btwNumber, companyType);
        this.fleets = new ArrayList<>();
        commissions = new HashMap<>();
    }

    public Collection<Fleet> getFleets() {
        return fleets;
    }

    public void setFleets(Collection<Fleet> fleets) {
        this.fleets = fleets;
    }

    public Collection<Contract> getContracts() {
        return contracts;
    }

    public void setContracts(Collection<Contract> contracts) {
        this.contracts = contracts;
    }

    public Map<SuretyType, Double> getCommissions() {
        return commissions;
    }

    public void setCommissions(Map<SuretyType, Double> commissions) {
        this.commissions = commissions;
    }

    /**
     * @param suretyType the type of which the specific commission should be retrieved
     * @return the value of the specific commission for the customer for given suretyType. null if there is no specific commission
     */
    public Double getSpecificCommission(SuretyType suretyType) {
        return commissions.get(suretyType);
    }

    public void setSpecificCommission(SuretyType suretyType, double commission) {
        if(commissions == null ){
            commissions = new HashMap<>();
        }
        commissions.put(suretyType, commission);
    }

    public void removeSpecificCommission(SuretyType suretyType) {
        commissions.remove(suretyType);
    }
}
