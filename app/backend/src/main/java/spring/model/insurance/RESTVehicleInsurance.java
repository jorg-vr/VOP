package spring.model.insurance;

import controller.VehicleController;
import controller.exceptions.UnAuthorizedException;
import dao.interfaces.DataAccessException;
import model.account.Function;
import model.fleet.Vehicle;
import model.insurance.Surety;
import model.insurance.VehicleInsurance;
import spring.exceptions.InvalidInputException;
import spring.model.RESTAbstractModel;

import java.time.LocalDateTime;
import java.util.UUID;

import static util.UUIDUtil.UUIDToNumberString;
import static util.UUIDUtil.toUUID;

public class RESTVehicleInsurance extends RESTAbstractModel<VehicleInsurance> {

    private String vehicle;
    private String surety;
    private LocalDateTime startDate;
    private LocalDateTime endDate;

    public RESTVehicleInsurance(VehicleInsurance insurance) {
        super(insurance.getUuid(), "TODO");
        vehicle = UUIDToNumberString(insurance.getVehicle().getUuid());
        surety = "TODO";
        startDate = insurance.getStartDate();
        endDate = insurance.getEndDate();
    }

    @Override
    public VehicleInsurance translate(Function function) throws UnAuthorizedException {
        VehicleInsurance insurance = new VehicleInsurance();
        insurance.setUuid(toUUID(getId()));
        try (VehicleController controller = new VehicleController(function)) {
            insurance.setVehicle(controller.get(toUUID(vehicle)));
        } catch (DataAccessException e) {
            throw new InvalidInputException("vehicle");
        }
        // TODO surety
        insurance.setStartDate(startDate);
        insurance.setEndDate(endDate);
        return insurance;
    }

    public String getVehicle() {
        return vehicle;
    }

    public void setVehicle(String vehicle) {
        this.vehicle = vehicle;
    }

    public String getSurety() {
        return surety;
    }

    public void setSurety(String surety) {
        this.surety = surety;
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
