package dao.database;

import dao.interfaces.ContractDAO;
import dao.interfaces.Filter;
import model.identity.Customer;
import model.identity.InsuranceCompany;
import model.insurance.Contract;
import org.hibernate.Session;

import java.time.LocalDate;

/**
 * Created by sam on 4/13/17.
 */
public class ProductionContractDAO extends ProductionDAO<Contract> implements ContractDAO {

    public ProductionContractDAO(Session session) {
        super(session, Contract.class);
    }

    @Override
    public Filter<Contract> byCustomer(Customer customer) {
        return filterEqual("customer",customer);
    }

    @Override
    public Filter<Contract> byInsuranceCompany(InsuranceCompany company) {
        return filterEqual("company",company);
    }

    @Override
    public Filter<Contract> startsBefore(LocalDate date) {
        if(date==null){
            return ()->{};
        }
        return () ->
                getPredicates().add(getCriteriaBuilder().lessThanOrEqualTo(getRoot().<LocalDate>get("startDate"), date));
    }

    @Override
    public Filter<Contract> endsAfter(LocalDate date) {
        if(date==null){
            return ()->{};
        }
        return () ->
                getPredicates().add(getCriteriaBuilder().greaterThanOrEqualTo(getRoot().<LocalDate>get("endDate"), date));
    }
}
