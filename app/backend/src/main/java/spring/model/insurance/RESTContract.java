package spring.model.insurance;

import controller.ControllerManager;
import controller.CustomerController;
import controller.InsuranceCompanyController;
import controller.exceptions.UnAuthorizedException;
import dao.exceptions.ConstraintViolationException;
import dao.exceptions.DataAccessException;
import dao.exceptions.ObjectNotFoundException;
import model.insurance.Contract;
import spring.exceptions.ErrorCode;
import spring.exceptions.InvalidInputException;
import spring.model.RESTAbstractModel;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

import static util.MyProperties.PATH_CONTRACTS;
import static util.MyProperties.getProperty;
import static util.UUIDUtil.UUIDToNumberString;
import static util.UUIDUtil.toUUID;

/**
 * Created by Billie Devolder on 15/04/2017.
 */
public class RESTContract extends RESTAbstractModel<Contract> {

    private String insuranceCompany;
    private String customer;
    private LocalDateTime startDate;
    private LocalDateTime endDate;
    private int totalCost;
    private int totalTax;

    public RESTContract(Contract contract) {
        super(contract.getUuid(), getProperty(PATH_CONTRACTS));
        this.insuranceCompany = UUIDToNumberString(contract.getCompany().getUuid());
        this.customer = UUIDToNumberString(contract.getCustomer().getUuid());
        this.startDate = contract.getStartDate();
        this.endDate = contract.getEndDate();
        this.totalCost = contract.calculateCost();
        this.totalTax = contract.calculateTax();
    }

    public RESTContract() {
    }

    @Override
    public Contract translate(ControllerManager manager) throws UnAuthorizedException, DataAccessException, ConstraintViolationException {
        Contract contract = new Contract();
        contract.setUuid(toUUID(getId()));
        contract.setStartDate(startDate);
        contract.setEndDate(endDate);

        Map<String, String> violations = new HashMap<>();
        try {
            CustomerController customerController = manager.getCustomerController();
            contract.setCustomer(customerController.get(toUUID(customer)));
        } catch (ObjectNotFoundException e) {
            violations.put("customer", ErrorCode.NOT_FOUND.toString());
        }
        try {
            InsuranceCompanyController insuranceCompanyController = manager.getInsuranceCompanyController();
            contract.setCompany(insuranceCompanyController.get(toUUID(insuranceCompany)));
        } catch (ObjectNotFoundException e) {
            violations.put("insuranceCompany", ErrorCode.NOT_FOUND.toString());
        }
        if (violations.size() > 0) {
            throw new ConstraintViolationException(violations);
        }

        return contract;
    }

    public String getInsuranceCompany() {
        return insuranceCompany;
    }

    public void setInsuranceCompany(String insuranceCompany) {
        this.insuranceCompany = insuranceCompany;
    }

    public String getCustomer() {
        return customer;
    }

    public void setCustomer(String customer) {
        this.customer = customer;
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

    public int getTotalCost() {
        return totalCost;
    }

    public void setTotalCost(int totalCost) {
        this.totalCost = totalCost;
    }

    public int getTotalTax() {
        return totalTax;
    }

    public void setTotalTax(int totalTax) {
        this.totalTax = totalTax;
    }
}
