package dao.interfaces;

import model.identity.Customer;
import model.identity.InsuranceCompany;
import model.insurance.Contract;

import java.time.LocalDateTime;

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
    Filter<Contract> startsBefore(LocalDateTime date);

    /**
     * Returns a Filter to use in ListFiltered in this class, which returns all Contracts starting after the given date.
     * Only the LocalDate part is considered for comparison (Hours/Minutes are ignored)
     * @param date The date to use in the filter
     * @return A useable Filter for listFiltered
     */
    Filter<Contract> startsAfter(LocalDateTime date);

    /**
     * Returns a Filter to use in ListFiltered in this class, which returns all Contracts ending before the given date.
     * @param date The date to use in the filter
     * @return A useable Filter for listFiltered
     */
    Filter<Contract> endsBefore(LocalDateTime date);

    /**
     * Returns a Filter to use in ListFiltered in this class, which returns all Contracts ending after the given date.
     * Only the LocalDate part is considered for comparison (Hours/Minutes are ignored)
     * @param date The date to use in the filter
     * @return A useable Filter for listFiltered
     */
    Filter<Contract> endsAfter(LocalDateTime date);
}
