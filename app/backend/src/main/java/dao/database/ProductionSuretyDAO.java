package dao.database;

import dao.interfaces.Filter;
import dao.interfaces.SuretyDAO;
import model.identity.InsuranceCompany;
import model.insurance.Surety;
import org.hibernate.Session;

/**
 * Created by sam on 4/18/17.
 */
public class ProductionSuretyDAO extends ProductionDAO<Surety> implements SuretyDAO<Surety> {

    public ProductionSuretyDAO(Session session) {
        super(session, Surety.class);
    }

    @Override
    public Filter<Surety> byOwner(InsuranceCompany company) {
            return filterEqual("insuranceCompany", company);
    }
}
