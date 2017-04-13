package dao.interfaces;

import model.identity.Customer;
import model.identity.InsuranceCompany;
import model.insurance.Contract;

import java.time.LocalDate;

/**
 * Created by sam on 4/12/17.
 */
public interface ContractDAO extends DAO<Contract> {

    Filter<Contract> byCustomer(Customer customer);

    Filter<Contract> byInsuranceCompany(InsuranceCompany company);

    Filter<Contract> startsBefore(LocalDate date);

    Filter<Contract> endsAfter(LocalDate date);
}
