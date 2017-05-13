package dao.database;

import dao.interfaces.Filter;
import dao.interfaces.NonFlatSuretyDAO;
import model.identity.InsuranceCompany;
import model.insurance.NonFlatSurety;
import org.hibernate.Session;

/**
 * Created by sam on 4/13/17.
 */
public class ProductionNonFlatSuretyDAO extends ProductionDAO<NonFlatSurety> implements NonFlatSuretyDAO {

    public ProductionNonFlatSuretyDAO(Session session) {
        super(session, NonFlatSurety.class);
    }

    @Override
    public Filter<NonFlatSurety> byOwner(InsuranceCompany company) {
        return filterEqual("insuranceCompany", company);
    }
}
