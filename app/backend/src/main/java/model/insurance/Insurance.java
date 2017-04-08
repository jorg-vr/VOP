package model.insurance;

import model.fleet.Vehicle;
import model.identity.InsuranceCompany;

import java.time.LocalDateTime;
import java.util.Collection;

/**
 * Created by jorg on 4/8/17.
 */
public class Insurance {
    /**
     * Company that offers surety to customer.
     */
    private InsuranceCompany company;
    Vehicle vehicle;
    Collection<Surety> sureties;
    LocalDateTime startDate;
    LocalDateTime endDate;

    public Insurance() {
    }

    public InsuranceCompany getCompany() {
        return company;
    }

    public void setCompany(InsuranceCompany company) {
        this.company = company;
    }

    public Vehicle getVehicle() {
        return vehicle;
    }

    public void setVehicle(Vehicle vehicle) {
        this.vehicle = vehicle;
    }

    public Collection<Surety> getSureties() {
        return sureties;
    }

    public void setSureties(Collection<Surety> sureties) {
        this.sureties = sureties;
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
}
