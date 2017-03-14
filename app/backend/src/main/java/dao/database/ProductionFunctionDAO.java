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
import java.util.UUID;

/**
 * Created by sam on 3/13/17.
 */
public class ProductionFunctionDAO implements FunctionDAO {

    private final SessionFactory factory;
    private Collection<Predicate> predicates = new ArrayList<>();
    private Root<Function> root;
    private CriteriaQuery<Function> criteriaQuery;
    private CriteriaBuilder criteriaBuilder;

    public ProductionFunctionDAO(SessionFactory factory){
        this.factory =factory;
    }
    @Override
    public Function create(Function function) throws DataAccessException {
        HibernateUtil.create(factory,function);
        return function;
    }

    @Override
    public Function get(UUID id) throws DataAccessException {
        try (Session session = factory.openSession()) {
            return session.get(Function.class, id);
        }
    }

    @Override
    public void update(Function function) throws DataAccessException {
        HibernateUtil.update(factory,function);
    }

    @Override
    public void remove(Function function) throws DataAccessException {
        HibernateUtil.remove(factory,function);
    }

    @Override
    public void remove(UUID id) throws DataAccessException {
        HibernateUtil.remove(factory,get(id));
    }

    @Override
    public Collection<Function> listFiltered(Filter<Function>[] filters) throws DataAccessException {
        Transaction tx = null;
        try (Session session = factory.openSession()) {

            tx = session.beginTransaction();
            this.criteriaBuilder = session.getCriteriaBuilder();
            this.criteriaQuery = this.criteriaBuilder.createQuery(Function.class);
            this.root = this.criteriaQuery.from(Function.class);
            for (Filter<Function> filter : filters) {
                filter.filter(null);
            }
            Collection<Function> functions = session.createQuery(criteriaQuery.where(predicates.toArray(new Predicate[predicates.size()]))).getResultList();
            tx.commit();
            this.root = null;
            this.criteriaQuery = null;
            this.criteriaBuilder = null;
            return functions;
        } catch (Exception e) {
            e.printStackTrace();
            if (tx != null) {
                tx.rollback();
            }
        }
        return null;
    }

    @Override
    public void close() {

    }

    @Override
    public Function create(Company company, Role role, Account account, LocalDateTime startDate, LocalDateTime endDate) throws DataAccessException {
        Function function = new Function();
        function.setCompany(company);
        function.setRole(role);
        function.setAccount(account);
        function.setStartDate(startDate);
        function.setEndDate(endDate);
        HibernateUtil.create(factory,function);
        return function;
    }

    @Override
    public Function update(UUID id, LocalDateTime endDate) throws DataAccessException {
        Function function = get(id);
        function.setEndDate(endDate);
        HibernateUtil.update(factory,function);
        return function;
    }

    @Override
    public Function update(UUID id, Company company, Role role, Account account, LocalDateTime startDate, LocalDateTime endDate) throws DataAccessException {
        Function function = get(id);
        function.setCompany(company);
        function.setRole(role);
        function.setAccount(account);
        function.setStartDate(startDate);
        function.setEndDate(endDate);
        HibernateUtil.update(factory,function);
        return function;
    }

    @Override
    public Filter<Function> byAccount(Account account) {
        return (o1) -> {
            predicates.add(criteriaBuilder.equal(root.get("account"), account));
            return true;
        };
    }

    @Override
    public Filter<Function> byCompany(Company company) {
        return (o1) -> {
            predicates.add(criteriaBuilder.equal(root.get("company"), company));
            return true;
        };
    }
}
