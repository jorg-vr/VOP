package controller;

import dao.database.ProductionProvider;
import dao.interfaces.*;
import dao.test.TESTFleetDAO;
import main.BackendApplication;
import model.fleet.Fleet;
import model.identity.Customer;

import java.util.UUID;

/**
 * Created by jorg on 3/13/17.
 */
public class FleetController extends AbstractController<Fleet> {

    private CustomerDAO customerDAO;
    private DAOProvider provider;
    private FleetDAO fleetDAO;

    public FleetController() {
        super(ProductionProvider.getInstance().getFleetDAO());
        provider = ProductionProvider.getInstance();
        customerDAO = provider.getCustomerDAO();
        fleetDAO = provider.getFleetDAO();
    }

    public Fleet create(UUID owner, String name) throws DataAccessException {
        Customer customer = customerDAO.get(owner);
        return fleetDAO.create(name, customer);
    }

    public Fleet update(UUID fleetId, UUID owner, String name) throws DataAccessException {
        Customer customer = customerDAO.get(owner);
        return fleetDAO.update(fleetId, name, customer);
    }
}
