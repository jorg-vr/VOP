package controller;

import controller.exceptions.UnAuthorizedException;
import dao.interfaces.DAOManager;
import dao.interfaces.LogEntryDAO;
import model.account.Function;
import model.account.Resource;
import model.account.Role;
import model.account.User;
import model.fleet.Fleet;
import model.fleet.Vehicle;
import model.history.LogEntry;

import java.util.Collection;
import java.util.UUID;

import static model.account.Action.READ_ALL;
import static model.account.Action.READ_MINE;

/**
 * @author Billie Devolder
 */
public class LogEntryController {

    private User user;
    private Role role;
    private LogEntryDAO dao;

    public LogEntryController(Function function, DAOManager manager) {
        this.role = function.getRole();
        this.user = function.getUser();
        this.dao = manager.getLogEntryDao();
    }

    /**
     * Get all the log entries that are relevant to the object with uuid objectId
     * @param vehicle uuid of the object which all the relevant log entries should be returned
     * @return a collection of all the log entries. The collection is empty if there are nog log entries for that uuid.
     */
    public Collection<LogEntry> getVehicleLogEntries(Vehicle vehicle) throws UnAuthorizedException {
        boolean isOwner = vehicle.getFleet().getOwner().equals(user);
        return getLogEntries(vehicle.getUuid(), isOwner);
    }

    /**
     * Get all the log entries that are relevant to the object with uuid objectId
     * @param fleet uuid of the object which all the relevant log entries should be returned
     * @return a collection of all the log entries. The collection is empty if there are nog log entries for that uuid.
     */
    public Collection<LogEntry> getFleetLogEntries(Fleet fleet) throws UnAuthorizedException {
        boolean isOwner = fleet.getOwner().equals(user);
        return getLogEntries(fleet.getUuid(), isOwner);
    }

    /**
     * Get all the log entries that are relevant to the object with uuid objectId
     * @param id uuid of the object which all the relevant log entries should be returned
     * @return a collection of all the log entries. The collection is empty if there are nog log entries for that uuid.
     */
    private Collection<LogEntry> getLogEntries(UUID id, boolean isOwner) throws UnAuthorizedException {
        Collection<LogEntry> entries = dao.getAllLogs(id);
        if (role.hasAccess(Resource.LOG, READ_ALL)
                || role.hasAccess(Resource.LOG, READ_MINE)) {
            return entries;
        } else {
            throw new UnAuthorizedException();
        }
    }

}
