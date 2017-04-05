package dao.database;

import dao.interfaces.DataAccessException;
import dao.interfaces.Filter;
import dao.interfaces.FunctionDAO;
import model.account.Account;
import model.account.Function;
import model.account.Role;
import model.identity.Company;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Optional;
import java.util.UUID;

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

    // TODO THIS WILL NOT WORK!!!!!!

    @Override
    public Filter<Function> byAccount(Account account) {
        return () ->
            predicates.add(criteriaBuilder.equal(root.get("account"), account));
    }

    @Override
    public Filter<Function> byCompany(Company company) {
        return () ->
            predicates.add(criteriaBuilder.equal(root.get("company"), company));
    }

}
