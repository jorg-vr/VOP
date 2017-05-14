package dao.interfaces;

import model.identity.Customer;
import model.identity.InsuranceCompany;
import model.insurance.Contract;

import java.time.LocalDate;

/**
 * Created by sam on 4/12/17.
 */
public interface ContractDAO extends DAO<Contract> {

    /**
     * Returns a Filter to use in ListFiltered in this class, which returns all Contracts having the given customer.
     * @param customer The customer to use in the filter
     * @return A useable Filter for listFiltered
     */
    Filter<Contract> byCustomer(Customer customer);

    /**
     * Returns a Filter to use in ListFiltered in this class, which returns all Contracts having the given insuranceCompany.
     * @param company The company to use in the filter
     * @return A useable Filter for listFiltered
     */
    Filter<Contract> byInsuranceCompany(InsuranceCompany company);

    /**
     * Returns a Filter to use in ListFiltered in this class, which returns all Contracts starting before the given date.
     * @param date The date to use in the filter
     * @return A useable Filter for listFiltered
     */
    Filter<Contract> startsBefore(LocalDate date);

    /**
     * Returns a Filter to use in ListFiltered in this class, which returns all Contracts starting on the given date.
     * @param date The date to use in the filter
     * @return A useable Filter for listFiltered
     */
    Filter<Contract> startsOn(LocalDate date);

    /**
     * Returns a Filter to use in ListFiltered in this class, which returns all Contracts starting after the given date.
     * @param date The date to use in the filter
     * @return A useable Filter for listFiltered
     */
    Filter<Contract> startsAfter(LocalDate date);

    /**
     * Returns a Filter to use in ListFiltered in this class, which returns all Contracts ending before the given date.
     * @param date The date to use in the filter
     * @return A useable Filter for listFiltered
     */
    Filter<Contract> endsBefore(LocalDate date);

    /**
     * Returns a Filter to use in ListFiltered in this class, which returns all Contracts ending on the given date.
     * @param date The date to use in the filter
     * @return A useable Filter for listFiltered
     */
    Filter<Contract> endsOn(LocalDate date);

    /**
     * Returns a Filter to use in ListFiltered in this class, which returns all Contracts ending after the given date.
     * @param date The date to use in the filter
     * @return A useable Filter for listFiltered
     */
    Filter<Contract> endsAfter(LocalDate date);
}
