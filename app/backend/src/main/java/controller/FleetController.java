package controller;

import controller.exceptions.UnAuthorizedException;
import dao.exceptions.DataAccessException;
import dao.interfaces.*;
import model.account.Function;
import model.account.Resource;
import model.fleet.Fleet;
import model.fleet.Vehicle;
import model.identity.Customer;

import java.util.Collection;
import java.util.stream.Collectors;

import static util.Compare.containsIgnoreCase;

/**
 * For more information of what this class does, see AbstractController
 */
public class FleetController extends AbstractController<Fleet> {

    public FleetController(Function function, DAOManager manager) {
        super(manager, manager.getFleetDAO(), Resource.FLEET, function);
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
    public Collection<Fleet> getFiltered(Customer owner, String name) throws DataAccessException, UnAuthorizedException {
        FleetDAO dao = (FleetDAO) getDao();

        // Filter vehicles on criteria that are supported by the database
        Collection<Fleet> result = getAll(
                dao.byOwner(owner)
        );

        // Filter vehicles on criteria that are not supported by the database
        return result.stream()
                .filter(c -> name == null || (c.getName() != null && containsIgnoreCase(c.getName(), name)))
                .collect(Collectors.toList());
    }
}
