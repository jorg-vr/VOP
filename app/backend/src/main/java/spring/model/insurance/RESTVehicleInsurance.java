package spring.model.insurance;

import controller.VehicleController;
import controller.exceptions.UnAuthorizedException;
import controller.insurance.SuretyController;
import dao.interfaces.DataAccessException;
import model.account.Function;
import model.fleet.Vehicle;
import model.insurance.Surety;
import model.insurance.VehicleInsurance;
import spring.exceptions.InvalidInputException;
import spring.model.RESTAbstractModel;

import java.time.LocalDateTime;
import java.util.UUID;

import static util.MyProperties.PATH_VEHICLE_INSURANCES;
import static util.MyProperties.getProperty;
import static util.UUIDUtil.UUIDToNumberString;
import static util.UUIDUtil.toUUID;

public class RESTVehicleInsurance extends RESTAbstractModel<VehicleInsurance> {

    private String vehicle;
    private String surety;
    private LocalDateTime startDate;
    private LocalDateTime endDate;

    private int franchise;
    private int insuredValue;
    private int cost;
    private int tax;

    public RESTVehicleInsurance() {
    }

    public RESTVehicleInsurance(VehicleInsurance insurance) {
        super(insurance.getUuid(), getProperty(PATH_VEHICLE_INSURANCES));
        vehicle = UUIDToNumberString(insurance.getVehicle().getUuid());
        surety = UUIDToNumberString(insurance.getSurety().getUuid());
        startDate = insurance.getStartDate();
        endDate = insurance.getEndDate();
        franchise = insurance.getFranchise();
        insuredValue = insurance.getInsuredValue();
    }

    @Override
    public VehicleInsurance translate(Function function) throws UnAuthorizedException {
        VehicleInsurance insurance = new VehicleInsurance();
        insurance.setUuid(toUUID(getId()));
        try (VehicleController controller = new VehicleController(function)) {
            insurance.setVehicle(controller.get(toUUID(vehicle)));
        } catch (DataAccessException e) {
            throw new InvalidInputException("Vehicle with id " + vehicle + " does not exist");
        }
        try (SuretyController controller = new SuretyController(function)) {
            insurance.setSurety(controller.get(toUUID(surety)));
        } catch (DataAccessException e) {
            throw new InvalidInputException("Surety with id " + surety + " does not exist");
        }
        insurance.setStartDate(startDate);
        insurance.setEndDate(endDate);
        insurance.setFranchise(franchise);
        insurance.setInsuredValue(insuredValue);
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

    public int getFranchise() {
        return franchise;
    }

    public void setFranchise(int franchise) {
        this.franchise = franchise;
    }

    public int getInsuredValue() {
        return insuredValue;
    }

    public void setInsuredValue(int insuredValue) {
        this.insuredValue = insuredValue;
    }

    public int getCost() {
        return cost;
    }

    public void setCost(int cost) {
        this.cost = cost;
    }

    public int getTax() {
        return tax;
    }

    public void setTax(int tax) {
        this.tax = tax;
    }
}
