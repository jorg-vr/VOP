package controller;

import dao.interfaces.CustomerDAO;
import dao.interfaces.DAO;
import dao.interfaces.DataAccessException;
import dao.interfaces.IdentityDAO;
import model.fleet.Fleet;

import java.util.UUID;

/**
 * Created by jorg on 3/13/17.
 */
public class FleetController extends AbstractController<Fleet> {
    private CustomerDAO customerDAO;//TODO initialize
    public FleetController() {
        super(dao);//Todo add fleetdao
    }
    public Fleet create(UUID owner) throws DataAccessException {
        return getDao().create(new Fleet(null,customerDAO.get(owner)));//Todo maybe use customercontroller
    }
    public void update(UUID Fleet,UUID owner,String name){
        //TODO
    }
}
