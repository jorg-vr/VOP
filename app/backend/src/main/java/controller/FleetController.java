package controller;

import controller.exceptions.UnAuthorizedException;
import dao.interfaces.*;
import main.BackendApplication;
import model.account.Function;
import model.account.Resource;
import model.fleet.Fleet;
import model.identity.Customer;

import java.util.Collection;

/**
 * For more information of what this class does, see AbstractController
 */
public class FleetController extends AbstractController<Fleet> {

    public FleetController(Function function) {
        super(BackendApplication.getProvider().getFleetDAO(), Resource.FLEET,function);
    }

    @Override
    public boolean isOwner(Fleet fleet, Function function) {
        return fleet.getOwner().equals(function.getCompany());
    }

    public Collection<Fleet> getFiltered(Customer owner) throws DataAccessException, UnAuthorizedException {
        FleetDAO dao = (FleetDAO) getDao();
        return getAll(
                dao.byOwner(owner)
        );
    }
}
