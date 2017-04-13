package dao.database;

import dao.interfaces.FlatSuretyDAO;
import model.insurance.FlatSurety;
import org.hibernate.Session;

/**
 * Created by sam on 4/13/17.
 */
public class ProductionFlatSuretyDAO extends ProductionDAO<FlatSurety> implements FlatSuretyDAO {

    public ProductionFlatSuretyDAO(Session session, Class<FlatSurety> cl) {
        super(session, cl);
    }
}
