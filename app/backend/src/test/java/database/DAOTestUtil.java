package database;

import dao.database.ProductionProvider;
import dao.interfaces.DAOManager;
import model.account.Function;
import model.account.Role;
import model.account.User;
import model.fleet.Fleet;
import model.fleet.Vehicle;
import model.fleet.VehicleType;
import model.identity.Address;
import model.identity.Customer;

import java.util.UUID;

/**
 * Created by Ponti on 9/05/2017.
 */
public class DAOTestUtil {

    public static void removeAddress(UUID uuid) throws Exception {
        try (DAOManager manager = ProductionProvider.getInstance().getDaoManager()) {
            manager.getAddressDao().remove(uuid);
        }
    }

    public static Address createAddress(Address address) throws Exception {
        try (DAOManager manager = ProductionProvider.getInstance().getDaoManager()) {
            return manager.getAddressDao().create(address);
        }
    }

    public static Address getAddress(UUID uuid) throws Exception {
        try (DAOManager manager = ProductionProvider.getInstance().getDaoManager()) {
            return manager.getAddressDao().get(uuid);
        }
    }

    public static void removeCustomer(UUID uuid) throws Exception {
        try (DAOManager manager = ProductionProvider.getInstance().getDaoManager()) {
            manager.getCustomerDAO().remove(uuid);
        }
    }

    public static Customer createCustomer(Customer customer) throws Exception {
        try (DAOManager manager = ProductionProvider.getInstance().getDaoManager()) {
            return manager.getCustomerDAO().create(customer);
        }
    }

    public static Customer getCustomer(UUID uuid) throws Exception {
        try (DAOManager manager = ProductionProvider.getInstance().getDaoManager()) {
            return manager.getCustomerDAO().get(uuid);
        }
    }

    public static void removeFleet(UUID uuid) throws Exception {
        try (DAOManager manager = ProductionProvider.getInstance().getDaoManager()) {
            manager.getFleetDAO().remove(uuid);
        }
    }

    public static Fleet createFleet(Fleet fleet) throws Exception {
        try (DAOManager manager = ProductionProvider.getInstance().getDaoManager()) {
            return manager.getFleetDAO().create(fleet);
        }
    }

    public static Fleet getFleet(UUID uuid) throws Exception {
        try (DAOManager manager = ProductionProvider.getInstance().getDaoManager()) {
            return manager.getFleetDAO().get(uuid);
        }
    }

    public static void removeFunction(UUID uuid) throws Exception {
        try (DAOManager manager = ProductionProvider.getInstance().getDaoManager()) {
            manager.getFunctionDAO().remove(uuid);
        }
    }

    public static Function createFunction(Function function) throws Exception {
        try (DAOManager manager = ProductionProvider.getInstance().getDaoManager()) {
            return manager.getFunctionDAO().create(function);
        }
    }

    public static Function getFunction(UUID uuid) throws Exception {
        try (DAOManager manager = ProductionProvider.getInstance().getDaoManager()) {
            return manager.getFunctionDAO().get(uuid);
        }
    }

    public static void removeRole(UUID uuid) throws Exception {
        try (DAOManager manager = ProductionProvider.getInstance().getDaoManager()) {
            manager.getRoleDAO().remove(uuid);
        }
    }

    public static Role createRole(Role role) throws Exception {
        try (DAOManager manager = ProductionProvider.getInstance().getDaoManager()) {
            return manager.getRoleDAO().create(role);
        }
    }

    public static Role getRole(UUID uuid) throws Exception {
        try (DAOManager manager = ProductionProvider.getInstance().getDaoManager()) {
            return manager.getRoleDAO().get(uuid);
        }
    }

    public static void removeUser(UUID uuid) throws Exception {
        try (DAOManager manager = ProductionProvider.getInstance().getDaoManager()) {
            manager.getUserDAO().remove(uuid);
        }
    }

    public static User createUser(User user) throws Exception {
        try (DAOManager manager = ProductionProvider.getInstance().getDaoManager()) {
            return manager.getUserDAO().create(user);
        }
    }

    public static User getUser(UUID uuid) throws Exception {
        try (DAOManager manager = ProductionProvider.getInstance().getDaoManager()) {
            return manager.getUserDAO().get(uuid);
        }
    }

    public static void removeVehicle(UUID uuid) throws Exception {
        try (DAOManager manager = ProductionProvider.getInstance().getDaoManager()) {
            manager.getVehicleDAO().remove(uuid);
        }
    }

    public static Vehicle createVehicle(Vehicle vehicle) throws Exception {
        try (DAOManager manager = ProductionProvider.getInstance().getDaoManager()) {
            return manager.getVehicleDAO().create(vehicle);
        }
    }

    public static Vehicle getVehicle(UUID uuid) throws Exception {
        try (DAOManager manager = ProductionProvider.getInstance().getDaoManager()) {
            return manager.getVehicleDAO().get(uuid);
        }
    }

    public static void removeVehicleType(UUID uuid) throws Exception {
        try (DAOManager manager = ProductionProvider.getInstance().getDaoManager()) {
            manager.getVehicleTypeDAO().remove(uuid);
        }
    }

    public static VehicleType createVehicleType(VehicleType vehicleType) throws Exception {
        try (DAOManager manager = ProductionProvider.getInstance().getDaoManager()) {
            return manager.getVehicleTypeDAO().create(vehicleType);
        }
    }

    public static VehicleType getVehicleType(UUID uuid) throws Exception {
        try (DAOManager manager = ProductionProvider.getInstance().getDaoManager()) {
            return manager.getVehicleTypeDAO().get(uuid);
        }
    }
}
