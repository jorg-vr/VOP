package controller;

import dao.database.ProductionProvider;
import dao.interfaces.*;
import dao.test.TESTFleetDAO;
import main.BackendApplication;
import model.fleet.Fleet;
import model.identity.Customer;

import java.util.UUID;

/**
 * For more information of what this class does, see AbstractController
 */
public class FleetController extends AbstractController<Fleet> {

    private CustomerDAO customerDAO;
    private DAOProvider provider;
    private FleetDAO fleetDAO;

    public FleetController() {
        super(BackendApplication.getProvider().getFleetDAO());
        provider = BackendApplication.getProvider();
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
