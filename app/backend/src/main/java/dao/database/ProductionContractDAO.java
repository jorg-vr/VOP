package dao.database;

import dao.interfaces.ContractDAO;
import dao.interfaces.Filter;
import model.identity.Customer;
import model.identity.InsuranceCompany;
import model.insurance.Contract;
import org.hibernate.Session;

import java.time.LocalDateTime;

/**
 * Created by sam on 4/13/17.
 */
public class ProductionContractDAO extends ProductionDAO<Contract> implements ContractDAO {

    public ProductionContractDAO(Session session) {
        super(session, Contract.class);
    }

    @Override
    public Filter<Contract> byCustomer(Customer customer) {
        return filterEqual("customer", customer);
    }

    @Override
    public Filter<Contract> byInsuranceCompany(InsuranceCompany company) {
        return filterEqual("company", company);
    }

    @Override
    public Filter<Contract> startsBefore(LocalDateTime date) {
        if (date == null) {
            return () -> {
            };
        }
        return () ->
                getPredicates().add(getCriteriaBuilder().lessThan(getRoot().<LocalDateTime>get("startDate"), date));
    }

    @Override
    public Filter<Contract> startsAfter(LocalDateTime date) {
        if (date == null) {
            return () -> {
            };
        }
        return () ->
                getPredicates().add(getCriteriaBuilder().greaterThanOrEqualTo(getRoot().<LocalDateTime>get("startDate"), date.plusDays(1)));
    }

    @Override
    public Filter<Contract> endsBefore(LocalDateTime date) {
        if (date == null) {
            return () -> {
            };
        }
        return () ->
                getPredicates().add(getCriteriaBuilder().lessThan(getRoot().<LocalDateTime>get("endDate"), date));
    }

    @Override
    public Filter<Contract> endsAfter(LocalDateTime date) {
        if (date == null) {
            return () -> {
            };
        }
        return () ->
                getPredicates().add(getCriteriaBuilder().greaterThanOrEqualTo(getRoot().<LocalDateTime>get("endDate"), date.plusDays(1)));
    }
}
