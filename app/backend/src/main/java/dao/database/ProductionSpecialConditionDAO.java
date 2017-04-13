package dao.database;

import dao.interfaces.SpecialConditionDAO;
import model.insurance.SpecialCondition;
import org.hibernate.Session;

/**
 * Created by sam on 4/13/17.
 */
public class ProductionSpecialConditionDAO extends ProductionDAO<SpecialCondition> implements SpecialConditionDAO {
    public ProductionSpecialConditionDAO(Session session) {
        super(session, SpecialCondition.class);
    }
}
