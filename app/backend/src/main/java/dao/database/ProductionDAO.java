package dao.database;

import dao.interfaces.DAO;
import dao.interfaces.DataAccessException;
import dao.interfaces.Filter;
import model.history.EditableObject;
import org.hibernate.Session;
import org.hibernate.Transaction;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Optional;
import java.util.UUID;

/**
 * All filters using String as Object use the filterContains, all other filters are equal filters
 * Created by sam on 4/4/17.
 */
public abstract class ProductionDAO<T extends EditableObject> implements DAO<T> {

    private final Session session;
    private final Class<T> cl;

    private Collection<Predicate> predicates = new ArrayList<>();
    private Root<T> root;
    private CriteriaQuery<T> criteriaQuery;
    private CriteriaBuilder criteriaBuilder;


    public ProductionDAO(Session session, Class<T> cl) {
        this.session = session;
        this.cl = cl;
    }


    @Override
    public T create(T t) throws DataAccessException {
        HibernateUtil.create(session, t);
        return t;
    }

    @Override
    public T update(T t) throws DataAccessException {
        HibernateUtil.update(session, t);
        return t;
    }

    @Override
    public T get(UUID id) throws DataAccessException {
        return Optional.ofNullable(session.get(cl, id)).orElseThrow(DataAccessException::new);
    }

    @Override
    public void remove(UUID id) throws DataAccessException {
        HibernateUtil.remove(session, get(id));
    }

    @Override
    public Collection<T> listFiltered(Filter<T>[] filters) throws DataAccessException {
        Transaction tx = null;
        try {

            tx = session.beginTransaction();
            this.criteriaBuilder = session.getCriteriaBuilder();
            this.criteriaQuery = this.criteriaBuilder.createQuery(cl);
            this.root = this.criteriaQuery.from(cl);
            for (Filter<T> filter : filters) {
                filter.filter();
            }
            Collection<T> collection = session.createQuery(criteriaQuery.where(predicates.toArray(new Predicate[predicates.size()]))).getResultList();
            tx.commit();
            this.root = null;
            this.criteriaQuery = null;
            this.criteriaBuilder = null;
            predicates.clear();
            return collection;
        } catch (Exception e) {
            e.printStackTrace();
            if (tx != null) {
                tx.rollback();
            }
        }
        return null;
    }

    protected Collection<Predicate> getPredicates() {
        return predicates;
    }

    protected Root<T> getRoot() {
        return root;
    }

    protected CriteriaQuery<T> getCriteriaQuery() {
        return criteriaQuery;
    }

    protected CriteriaBuilder getCriteriaBuilder() {
        return criteriaBuilder;
    }

    protected Session getSession() {
        return session;
    }

    protected Filter<T> filterEqual(String fieldName, Object object) {
        if(object==null){
            return () -> {};
        }
        return () ->
                getPredicates().add(getCriteriaBuilder().equal(getRoot().get(fieldName), object));
    }

    protected Filter<T> filterContains(String fieldName, String string) {
        if(string==null){
            return ()->{};
        }
        return () -> getPredicates().add(getCriteriaBuilder().like(getRoot().get(fieldName), "%" + string + "%")) ;

    }

    @Override
    public void close() throws Exception {
        session.close();
    }

    @Override
    public void refresh(T t) throws Exception{
        session.refresh(t);
    }
}
