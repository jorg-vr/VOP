package controller;

import dao.interfaces.CustomerDAO;
import dao.interfaces.DAO;
import dao.interfaces.DataAccessException;
import dao.interfaces.IdentityDAO;
import dao.test.TESTFleetDAO;
import main.BackendApplication;
import model.fleet.Fleet;

import java.util.UUID;

/**
 * Created by jorg on 3/13/17.
 */
public class FleetController extends AbstractController<Fleet> {
    private CustomerDAO customerDAO;//TODO initialize
    public FleetController() {
        super(BackendApplication.PROVIDER.getFleetDAO());
        customerDAO = BackendApplication.PROVIDER.getCustomerDAO();
    }
    public Fleet create(UUID owner,String name) throws DataAccessException {
        return getDao().create(new Fleet(null,customerDAO.get(owner),name));
    }
    public Fleet update(UUID Fleet,UUID owner,String name)throws DataAccessException{
        return null;//TODO
    }
}
