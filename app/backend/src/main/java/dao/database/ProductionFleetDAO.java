package dao.database;

import dao.interfaces.Filter;
import dao.interfaces.FleetDAO;
import model.fleet.Fleet;
import model.identity.Customer;
import org.hibernate.Session;

/**
 * Created by sam on 3/13/17.
 */
public class ProductionFleetDAO extends ProductionDAO<Fleet> implements FleetDAO {

    public ProductionFleetDAO(Session session) {
        super(session, Fleet.class);
    }

    @Override
    public Filter<Fleet> byOwner(Customer customer) {
        return filterEqual("owner", customer);
    }
}
