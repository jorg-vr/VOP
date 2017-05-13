package dao.database;

import dao.interfaces.Filter;
import dao.interfaces.FlatSuretyDAO;
import model.identity.InsuranceCompany;
import model.insurance.FlatSurety;
import org.hibernate.Session;

/**
 * Created by sam on 4/13/17.
 */
public class ProductionFlatSuretyDAO extends ProductionDAO<FlatSurety> implements FlatSuretyDAO {
    public ProductionFlatSuretyDAO(Session session) {
        super(session, FlatSurety.class);
    }

    @Override
    public Filter<FlatSurety> byOwner(InsuranceCompany company) {
        return filterEqual("insuranceCompany", company);
    }
}
