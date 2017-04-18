package dao.database;

import dao.interfaces.NonFlatSuretyDAO;
import model.insurance.NonFlatSurety;
import org.hibernate.Session;

/**
 * Created by sam on 4/13/17.
 */
public class ProductionNonFlatSuretyDAO extends ProductionDAO<NonFlatSurety> implements NonFlatSuretyDAO {

    public ProductionNonFlatSuretyDAO(Session session) {
        super(session, NonFlatSurety.class);
    }
}
