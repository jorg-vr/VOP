package spring.model.insurance;

import java.time.LocalDate;

/**
 * Created by Billie Devolder on 18/05/2017.
 */
public class RESTVehicleInsuranceContainer {

    private LocalDate date;

    private RESTVehicleInsurance vehicleInsurance;

    public RESTVehicleInsuranceContainer() {
    }

    public LocalDate getDate() {
        return date;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }

    public RESTVehicleInsurance getVehicleInsurance() {
        return vehicleInsurance;
    }

    public void setVehicleInsurance(RESTVehicleInsurance vehicleInsurance) {
        this.vehicleInsurance = vehicleInsurance;
    }
}
