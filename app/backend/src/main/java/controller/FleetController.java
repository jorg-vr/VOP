package controller;

import controller.exceptions.UnAuthorizedException;
import dao.exceptions.DataAccessException;
import dao.interfaces.*;
import model.account.Function;
import model.account.Resource;
import model.fleet.Fleet;
import model.identity.Customer;

import java.util.Collection;

/**
 * For more information of what this class does, see AbstractController
 */
public class FleetController extends AbstractController<Fleet> {

    public FleetController(Function function, DAOManager manager) {
        super(manager.getFleetDAO(), Resource.FLEET, function);
    }

    @Override
    public boolean isOwner(Fleet fleet, Function function) {
        return fleet.getOwner().equals(function.getCompany());
    }

    /**
     * @param owner Only return the fleets of this owner. If this owner does not exist or has no fleets, an empty list will be returned
     * @return all fleets filtered by the arguments
     * @throws DataAccessException   Something went horribly wrong with the database
     * @throws UnAuthorizedException Function is not authorized to get all the objects.
     */
    public Collection<Fleet> getFiltered(Customer owner) throws DataAccessException, UnAuthorizedException {
        FleetDAO dao = (FleetDAO) getDao();
        return getAll(
                dao.byOwner(owner)
        );
    }
}
