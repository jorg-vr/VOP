package model.insurance;

import model.history.EditableObject;
import model.identity.Customer;
import model.identity.InsuranceCompany;

import java.time.LocalDateTime;
import java.util.Collection;
import java.util.UUID;

/**
 * Created by jorg on 4/8/17.
 */
public class Insurance implements EditableObject {
    /**
     * Company that offers surety to customer.
     */
    private Periodicity paymentPeriodicity;
    private InsuranceCompany company;
    private Customer customer;
    private Collection<VehicleInsurance> vehicleInsurances;
    private LocalDateTime startDate;
    private LocalDateTime endDate;
    private UUID uuid;

    public Insurance() {
    }

    public InsuranceCompany getCompany() {
        return company;
    }

    public void setCompany(InsuranceCompany company) {
        this.company = company;
    }

    public Customer getCustomer() {
        return customer;
    }

    public void setCustomer(Customer customer) {
        this.customer = customer;
    }

    public Collection<VehicleInsurance> getVehicleInsurances() {
        return vehicleInsurances;
    }

    public void setVehicleInsurances(Collection<VehicleInsurance> vehicleInsurances) {
        this.vehicleInsurances = vehicleInsurances;
    }

    public LocalDateTime getStartDate() {
        return startDate;
    }

    public void setStartDate(LocalDateTime startDate) {
        this.startDate = startDate;
    }

    public LocalDateTime getEndDate() {
        return endDate;
    }

    public void setEndDate(LocalDateTime endDate) {
        this.endDate = endDate;
    }

    public Periodicity getPaymentPeriodicity() {
        return paymentPeriodicity;
    }

    public void setPaymentPeriodicity(Periodicity paymentPeriodicity) {
        this.paymentPeriodicity = paymentPeriodicity;
    }

    public void setUuid(UUID uuid) {
        this.uuid = uuid;
    }

    @Override
    public UUID getUuid() {
        return uuid;
    }

    @Override
    public EditableObject copy() {
        return null;
    }
}
