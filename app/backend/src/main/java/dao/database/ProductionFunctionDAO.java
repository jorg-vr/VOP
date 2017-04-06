package dao.database;

import dao.interfaces.Filter;
import dao.interfaces.FunctionDAO;
import model.account.Function;
import model.account.User;
import model.identity.Company;
import org.hibernate.Session;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import java.util.ArrayList;
import java.util.Collection;

/**
 * Created by sam on 3/13/17.
 */
public class ProductionFunctionDAO extends ProductionDAO<Function> implements FunctionDAO {

    private Collection<Predicate> predicates = new ArrayList<>();
    private Root<Function> root;
    private CriteriaQuery<Function> criteriaQuery;
    private CriteriaBuilder criteriaBuilder;

    public ProductionFunctionDAO(Session session){
        super(session, Function.class);
    }

    @Override
    public Filter<Function> byUser(User user) {
        return filterEqual("user",user);
    }

    @Override
    public Filter<Function> byCompany(Company company) {
        return filterEqual("company",company);
    }

}
