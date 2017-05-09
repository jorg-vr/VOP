package controller;

import dao.interfaces.DAOManager;
import model.account.Function;
import model.account.Role;
import model.account.User;
import model.fleet.Fleet;
import model.fleet.Vehicle;
import model.fleet.VehicleType;
import model.history.LogEntry;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collection;
import java.util.UUID;

/**
 * @author Billie Devolder
 */
public class LogEntryController {

    private User user;
    private Role role;

    public LogEntryController(Function function, DAOManager manager) {
        this.role = function.getRole();
        this.user = function.getUser();
    }

    /**
     * Get all the log entries that are relevant to the object with uuid objectId
     * @param objectId uuid of the object which all the relevant log entries should be returned
     * @return a collection of all the log entries. The collection is empty if there are nog log entries for that uuid.
     */
    public Collection<LogEntry> getLogEntries(UUID objectId) {
        Collection<LogEntry> entries = new ArrayList<>();
        Vehicle vehicle = new Vehicle("brand 2", "model A", "AZ0UZABCUKZ12345L", "ABR 569", 36000, 4900, new VehicleType(), LocalDate.of(2015, 6, 17), new Fleet());
        entries.addAll(vehicle.logCreate(user));

        Vehicle copy = vehicle.copy();
        vehicle.setBrand("Brand 1");
        entries.addAll(vehicle.logUpdate(user, copy));

        entries.addAll(vehicle.logDelete(user));
        return entries;
    }
}
