package spring.model.insurance;

import controller.CustomerController;
import controller.InsuranceCompanyController;
import controller.exceptions.UnAuthorizedException;
import dao.interfaces.DataAccessException;
import model.account.Function;
import model.identity.Customer;
import model.identity.InsuranceCompany;
import model.insurance.Contract;
import model.insurance.VehicleInsurance;
import spring.exceptions.InvalidInputException;
import spring.model.RESTAbstractModel;
import util.UUIDUtil;

import java.time.LocalDateTime;
import java.util.Collection;
import java.util.UUID;

import static util.UUIDUtil.UUIDToNumberString;
import static util.UUIDUtil.toUUID;

/**
 * Created by Billie Devolder on 15/04/2017.
 */
public class RESTContract extends RESTAbstractModel<Contract> {

    private String insuranceCompany;
    private String customer; // not in api, maybe useful?
    private String vehicle;
    private String type;
    private int franchise;
    private int premium;
    private LocalDateTime startDate;
    private LocalDateTime endDate;

    public RESTContract(Contract contract) {
        super(contract.getUuid(), "TODO");
        this.insuranceCompany = UUIDToNumberString(contract.getCompany().getUuid());
        this.customer = UUIDToNumberString(contract.getCustomer().getUuid());
        this.startDate = contract.getStartDate();
        this.endDate = contract.getEndDate();
    }

    public RESTContract() {
    }

    @Override
    public Contract translate(Function function) throws UnAuthorizedException {
        Contract contract = new Contract();
        contract.setUuid(toUUID(getId()));
        try (CustomerController customerController = new CustomerController(function)) {
            contract.setCustomer(customerController.get(toUUID(customer)));
        } catch (DataAccessException e) {
            throw new InvalidInputException("Company with id " + customer + " does not exist");
        }
        try (InsuranceCompanyController insuranceCompanyController = new InsuranceCompanyController(function)) {
            contract.setCompany(insuranceCompanyController.get(toUUID(insuranceCompany)));
        } catch (DataAccessException e) {
            throw new InvalidInputException("InsuranceCompany with id " + insuranceCompany + " does not exist");
        }
        contract.setStartDate(startDate);
        contract.setEndDate(endDate);
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
}
