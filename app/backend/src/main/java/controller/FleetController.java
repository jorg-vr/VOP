package controller;

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
    private CustomerDAO customerDAO;//TODO initialize
    private FleetDAO fleetDAO;
    public FleetController() {
        super(BackendApplication.PROVIDER.getFleetDAO());
        DAOProvider provider = BackendApplication.PROVIDER;
        customerDAO = provider.getCustomerDAO();
        fleetDAO = provider.getFleetDAO();
    }
    public Fleet create(UUID owner,String name) throws DataAccessException {
        Customer customer = customerDAO.get(owner);
        return fleetDAO.create(customer, null);
    }
    public Fleet update(UUID Fleet,UUID owner,String name)throws DataAccessException{
        return null;//TODO
    }
}
