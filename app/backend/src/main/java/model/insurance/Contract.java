package model.insurance;

import model.history.EditableObject;
import model.history.LogResource;
import model.identity.Customer;
import model.identity.InsuranceCompany;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collection;
import java.util.UUID;

/**
 * Representing a contract: a collection of VehicleInsurance of a Customer from an InsuranceCompany
 * Created by jorg on 4/8/17.
 */
public class Contract implements EditableObject {

    /**
     * Company that offers surety to customer.
     */
    private InsuranceCompany company;

    /**
     * The customer of the vehicleInsurances
     */
    private Customer customer;

    /**
     * The collection of vehicleInsurances belonging to Vehicles the customer owns
     */
    private Collection<VehicleInsurance> vehicleInsurances;

    /**
     * The start date of the contract
     */
    private LocalDateTime startDate;

    /**
     * The end date of the contract
     */
    private LocalDateTime endDate;

    /**
     * The unique id of the object
     */
    private UUID uuid;

    /**
     * Default constructor
     */
    public Contract() {
        this.vehicleInsurances = new ArrayList<>();
    }

    /**
     * Constructor
     * @param company the company
     * @param customer the customer
     * @param startDate the start date
     * @param endDate the end date
     */
    public Contract(InsuranceCompany company, Customer customer, LocalDateTime startDate, LocalDateTime endDate) {
        this.company = company;
        this.customer = customer;
        this.startDate = startDate;
        this.endDate = endDate;
        this.vehicleInsurances = new ArrayList<>();
    }

    /**
     * Calculate the total cost of this contract.
     *
     * @return sum of costs of insurances. expressed in cents
     */
    public int calculateCost() {
        int totalCost = 0;
        for (VehicleInsurance insurance : vehicleInsurances) {
            totalCost += insurance.calculateCost();
        }
        return totalCost;
    }

    /**
     * Calculate the total tax of this contract.
     *
     * @return sum of taxes of insurances. expressed in cents
     */
    public int calculateTax() {
        int totalTax = 0;
        for (VehicleInsurance insurance : vehicleInsurances) {
            totalTax += insurance.calculateTax();
        }
        return totalTax;
    }

    /**
     * Gets the InsuranceCompany
     * @return the insurance company
     */
    public InsuranceCompany getCompany() {
        return company;
    }

    /**
     * Sets the insurance company
     * @param company the insurance company
     */
    public void setCompany(InsuranceCompany company) {
        this.company = company;
    }

    /**
     * Gets the customer
     * @return the customer
     */
    public Customer getCustomer() {
        return customer;
    }

    /**
     * Sets the customer
     * @param customer the customer
     */
    public void setCustomer(Customer customer) {
        this.customer = customer;
    }

    /**
     * Get all vehicle insurances
     * @return the vehicle insurances
     */
    public Collection<VehicleInsurance> getVehicleInsurances() {
        return vehicleInsurances;
    }

    /**
     * Sets all vehicle insurances
     * @param vehicleInsurances the vehicle insurances
     */
    public void setVehicleInsurances(Collection<VehicleInsurance> vehicleInsurances) {
        this.vehicleInsurances = vehicleInsurances;
    }

    /**
     * Get the start date
     * @return the start date
     */
    public LocalDateTime getStartDate() {
        return startDate;
    }

    /**
     * Sets the start date
     * @param startDate the start date
     */
    public void setStartDate(LocalDateTime startDate) {
        this.startDate = startDate;
    }

    /**
     * Gets the end date
     * @return the end date
     */
    public LocalDateTime getEndDate() {
        return endDate;
    }

    /**
     * Sets the end date
     * @param endDate the end date
     */
    public void setEndDate(LocalDateTime endDate) {
        this.endDate = endDate;
    }

    /**
     * Sets the uuid
     * @param uuid the uuid
     */
    public void setUuid(UUID uuid) {
        this.uuid = uuid;
    }

    /**
     * Gets the uuid
     * @return the uuid
     */
    @Override
    public UUID getUuid() {
        return uuid;
    }

    /**
     * Copies the object
     * @return the copy
     */
    @Override
    public EditableObject copy() {
        Contract contract = new Contract();
        contract.setEndDate(endDate);
        contract.setStartDate(startDate);
        contract.setCompany((InsuranceCompany) getCompany().copy());
        contract.setCustomer((Customer) getCustomer().copy());
        contract.setVehicleInsurances(new ArrayList<>(getVehicleInsurances()));
        contract.setUuid(getUuid());
        return contract;
    }

    @Override
    public LogResource getLogResource() {
        return LogResource.CONTRACT;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || !(o instanceof Contract)) return false;

        Contract that = (Contract) o;

        return uuid!= null && getUuid().equals(that.getUuid());

    }

    @Override
    public int hashCode() {
        if(uuid!=null){return getUuid().hashCode();}
        return super.hashCode();
    }
}
