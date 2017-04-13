package dao.database;

import dao.interfaces.DataAccessException;
import dao.interfaces.SuretyCommisionDAO;
import model.account.User;
import model.fleet.VehicleType;
import model.identity.Customer;
import model.insurance.SuretyCommision;
import model.insurance.SuretyType;
import org.hibernate.Session;
import org.hibernate.Transaction;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import java.util.Collection;

/**
 * Created by sam on 4/13/17.
 */
public class ProductionSuretyCommisionDAO extends ProductionDAO<SuretyCommision> implements SuretyCommisionDAO {


    public ProductionSuretyCommisionDAO(Session session) {
        super(session, SuretyCommision.class);
    }

    @Override
    public SuretyCommision get(SuretyType suretyType, VehicleType vehicleType, Customer customer) {
        Transaction tx = null;
        try {
            tx = getSession().beginTransaction();
            CriteriaBuilder criteriaBuilder = getSession().getCriteriaBuilder();
            CriteriaQuery<SuretyCommision> criteriaQuery = criteriaBuilder.createQuery(SuretyCommision.class);
            Root<SuretyCommision> root = criteriaQuery.from(SuretyCommision.class);
            Collection<SuretyCommision> suretyCommisions = getSession().createQuery(criteriaQuery.where(
                    criteriaBuilder.equal(root.get("suretyType"), suretyType),
                    criteriaBuilder.equal(root.get("vehicleType"), vehicleType),
                    criteriaBuilder.equal(root.get("customer"),customer))).getResultList();
            tx.commit();

        } catch (Exception e) {
            if (tx != null) {
                tx.rollback();
            }
            e.printStackTrace();
        }
        return null;
    }
}
