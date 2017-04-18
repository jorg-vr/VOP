package database;

import dao.database.ProductionProvider;
import dao.interfaces.*;
import model.fleet.Fleet;
import model.fleet.Vehicle;
import model.fleet.VehicleType;
import model.identity.Address;
import model.identity.Customer;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;

import java.time.LocalDate;

import static org.junit.Assert.assertTrue;

/**
 * Created by Ponti on 7/04/2017.
 */
public class VehiclesCollectionTest {

    private static DAOProvider daoProvider;
    private static boolean notLocalTest = false;
    private static Address a1;
    private static Customer c1;
    private static VehicleType t1, t2;
    private static Fleet f1;

    //Setup before any of the tests are started
    @BeforeClass
    public static void initProvider() throws Exception {
        daoProvider = ProductionProvider.getInstance();
        if (daoProvider == null) {
            ProductionProvider.initializeProvider("unittest");
            daoProvider = ProductionProvider.getInstance();
            notLocalTest = true;
        }
        try (VehicleTypeDAO vehicleTypeDAO = daoProvider.getVehicleTypeDAO();
             CustomerDAO customerDAO = daoProvider.getCustomerDAO();
             FleetDAO fleetDAO = daoProvider.getFleetDAO();
             AddressDAO addressDAO = daoProvider.getAddressDao()) {
            a1 = addressDAO.create(new Address("streettest n1", "59", "town 1", "9999", "country 1"));
            c1 = customerDAO.create(new Customer(a1, "911", "customername 1", "btw123"));
            f1 = fleetDAO.create(new Fleet("fleet 1", c1, a1));
//            t1 = vehicleTypeDAO.create(new VehicleType("type 1", 2.5));
//            t2 = vehicleTypeDAO.create(new VehicleType("type 2", 3.5));
            t1 = null;
            t2 = null;
        } catch (Exception e) {

        }
    }

    //Gets executed after all tests have been run
    @AfterClass
    public static void closeProvider() throws Exception {
        try (VehicleTypeDAO vehicleTypeDAO = daoProvider.getVehicleTypeDAO();
             CustomerDAO customerDAO = daoProvider.getCustomerDAO();
             FleetDAO fleetDAO = daoProvider.getFleetDAO();
             AddressDAO addressDAO = daoProvider.getAddressDao()) {
            //vehicleTypeDAO.remove(t2.getUuid());
            //vehicleTypeDAO.remove(t1.getUuid());
            fleetDAO.remove(f1.getUuid());
            customerDAO.remove(c1.getUuid());
            addressDAO.remove(a1.getUuid());
        } catch (Exception e) {

        }
        if (notLocalTest) {
            daoProvider.close();
        }
    }

    /**
     * Test if a vehicle is automatically added to a fleet's vehicles collection when setting the vehicle's 'fleet' field to that fleet.
     *
     * @throws Exception
     */
    @Test
    public void setFleetOfVehicle() throws Exception {
        Vehicle v1 = null, v2 = null;
        try (FleetDAO fleetDAO = daoProvider.getFleetDAO();
             VehicleDAO vehicleDAO = daoProvider.getVehicleDAO()) {

            //create vehicles
            f1 = fleetDAO.get(f1.getUuid());
            v1 = vehicleDAO.create(new Vehicle("brand 2", "model A", "AZ0UZABCUKZ12345L", "ABR 569", 36000, 4900, t1, LocalDate.of(2015, 6, 17), f1, null));
            v2 = vehicleDAO.create(new Vehicle("brand 3", "model B", "BZ0UZABCUKZ12345L", "BBR 569", 36000, 4900, t1, LocalDate.of(2015, 7, 17), null, null));

            //Test if a vehicle is automatically added to a fleet's vehicles collection when creating the vehicle with a given fleet parameter
            fleetDAO.refresh(f1);
            f1 = fleetDAO.get(f1.getUuid());
            assertTrue("vehicle was not automatically added to the fleet's vehicles collection when created with given fleet parameter", f1.getVehicles().contains(v1));

            //Test if a vehicle is automatically added to a fleet's vehicles collection when setting the vehicle's fleet field.
            v2.setFleet(f1);
            vehicleDAO.update(v2);
            fleetDAO.refresh(f1);
            f1 = fleetDAO.get(f1.getUuid());
            assertTrue("vehicle was not automatically added to the fleet's vehicles collection when updating the vehicle with a fleet parameter", f1.getVehicles().contains(v2));

        } catch (Exception e) {

        } finally {
            VehicleDAO vehicleDAO = daoProvider.getVehicleDAO();
            if (v1 != null) {
                vehicleDAO.remove(v1.getUuid());
            }
            if (v2 != null) {
                vehicleDAO.remove(v2.getUuid());
            }
            vehicleDAO.close();
        }
    }

    /**
     * Test if a vehicle is correctly removed from it's fleet's vehicles collection when removing the vehicle from the database.
     *
     * @throws Exception
     */
    @Test
    public void removeVehicle() throws Exception {

        Vehicle v1 = null;
        try (FleetDAO fleetDAO = daoProvider.getFleetDAO();
             VehicleDAO vehicleDAO = daoProvider.getVehicleDAO()) {

            //create vehicle
            f1 = fleetDAO.get(f1.getUuid());
            v1 = vehicleDAO.create(new Vehicle("brand 2", "model A", "AZ0UZABCUKZ12345L", "ABR 569", 36000, 4900, t1, LocalDate.of(2015, 6, 17), f1, null));

            //Test if a vehicle is automatically added to a fleet's vehicles collection when creating the vehicle with a given fleet parameter (if this fails the rest of the test is irrelevant)
            fleetDAO.refresh(f1);
            f1 = fleetDAO.get(f1.getUuid());
            assertTrue("vehicle was not automatically added to the fleet's vehicles collection when created with given fleet parameter", f1.getVehicles().contains(v1));

            //Test if vehicles are automatically removed from the fleet's vehicle collection when a vehicle is removed from the database
            vehicleDAO.remove(v1.getUuid());
            Vehicle tempVehicle = v1;
            v1 = null;
            fleetDAO.refresh(f1);
            f1 = fleetDAO.get(f1.getUuid());
            assertTrue("vehicle still remains in the fleet's vehicles collection after removing it from the database", !f1.getVehicles().contains(tempVehicle));

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            VehicleDAO vehicleDAO = daoProvider.getVehicleDAO();
            if (v1 != null) {
                vehicleDAO.remove(v1.getUuid());
            }
            vehicleDAO.close();
        }
    }

    //TODO: test if duplicate vehicles can be added to a fleet
}
