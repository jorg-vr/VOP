package spring.model.insurance;

import controller.ControllerManager;
import controller.VehicleController;
import controller.exceptions.UnAuthorizedException;
import controller.insurance.ContractController;
import controller.insurance.SuretyController;
import dao.exceptions.ConstraintViolationException;
import dao.exceptions.DataAccessException;
import dao.exceptions.ObjectNotFoundException;
import model.insurance.SuretyType;
import model.insurance.VehicleInsurance;
import spring.exceptions.ErrorCode;
import spring.exceptions.InvalidInputException;
import spring.model.RESTAbstractModel;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

import static util.MyProperties.PATH_VEHICLE_INSURANCES;
import static util.MyProperties.getProperty;
import static util.UUIDUtil.UUIDToNumberString;
import static util.UUIDUtil.toUUID;

public class RESTVehicleInsurance extends RESTAbstractModel<VehicleInsurance> {

    private String vehicle;
    private String surety;
    private String contract;
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
        contract = UUIDToNumberString(insurance.getContract().getUuid());
        startDate = insurance.getStartDate();
        endDate = insurance.getEndDate();
        franchise = insurance.getFranchise();
        insuredValue = insurance.getInsuredValue();
        cost = insurance.calculateCost();
        tax = insurance.calculateTax();
    }

    @Override
    public VehicleInsurance translate(ControllerManager manager) throws UnAuthorizedException, DataAccessException, ConstraintViolationException {
        VehicleInsurance insurance = new VehicleInsurance();
        insurance.setUuid(toUUID(getId()));
        insurance.setStartDate(startDate);
        insurance.setEndDate(endDate);
        insurance.setFranchise(franchise);
        insurance.setInsuredValue(insuredValue);

        Map<String, String> violations = new HashMap<>();
        try {
            VehicleController controller = manager.getVehicleController();
            insurance.setVehicle(controller.get(toUUID(vehicle)));
            for (SuretyType suretyType : SuretyType.values()) {
                insurance.getVehicle().getType().getCommission(suretyType);
                insurance.getVehicle().getType().getTax(suretyType);
            }
        } catch (ObjectNotFoundException e) {
            violations.put("vehicle", ErrorCode.NOT_FOUND.toString());
        }
        try {
            SuretyController controller = manager.getSuretyController();
            insurance.setSurety(controller.get(toUUID(surety)));
        } catch (ObjectNotFoundException e) {
            violations.put("surety", ErrorCode.NOT_FOUND.toString());
        }
        try {
            ContractController controller = manager.getContractController();
            insurance.setContract(controller.get(toUUID(contract)));
        } catch (ObjectNotFoundException e) {
            violations.put("contract", ErrorCode.NOT_FOUND.toString());
        }
        if (violations.size() > 0) {
            throw new ConstraintViolationException(violations);
        }

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

    public String getContract() {
        return contract;
    }

    public void setContract(String contract) {
        this.contract = contract;
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
