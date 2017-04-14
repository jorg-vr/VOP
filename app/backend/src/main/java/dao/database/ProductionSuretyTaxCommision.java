package dao.database;

import dao.interfaces.SuretyTaxDAO;
import model.fleet.VehicleType;
import model.insurance.SuretyCommision;
import model.insurance.SuretyTax;
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
public class ProductionSuretyTaxCommision extends ProductionDAO<SuretyTax> implements SuretyTaxDAO {
    public ProductionSuretyTaxCommision(Session session) {
        super(session, SuretyTax.class);
    }

    @Override
    public SuretyTax get(SuretyType suretyType, VehicleType vehicleType) {
        Transaction tx = null;
        try {
            tx = getSession().beginTransaction();
            CriteriaBuilder criteriaBuilder = getSession().getCriteriaBuilder();
            CriteriaQuery<SuretyCommision> criteriaQuery = criteriaBuilder.createQuery(SuretyCommision.class);
            Root<SuretyCommision> root = criteriaQuery.from(SuretyCommision.class);
            Collection<SuretyCommision> suretyCommisions = getSession().createQuery(criteriaQuery.where(
                    criteriaBuilder.equal(root.get("suretyType"), suretyType),
                    criteriaBuilder.equal(root.get("vehicleType"), vehicleType))).getResultList();
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
