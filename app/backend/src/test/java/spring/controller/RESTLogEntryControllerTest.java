package spring.controller;

import controller.ControllerManager;
import dao.database.ProductionProvider;
import dao.exceptions.DataAccessException;
import dao.interfaces.DAOManager;
import model.fleet.Fleet;
import model.fleet.Vehicle;
import model.fleet.VehicleType;
import model.history.LogAction;
import model.history.LogEntry;
import model.history.LogResource;
import model.identity.Address;
import model.identity.Customer;
import model.identity.Periodicity;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import spring.exceptions.MyExceptionHandler;
import spring.model.AuthenticationToken;

import java.time.LocalDate;
import java.util.UUID;

import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.hasSize;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static util.UUIDUtil.UUIDToNumberString;
import static util.UUIDUtil.toUUID;

/**
 * Created by Billie Devolder on 21/05/2017.
 */
public class RESTLogEntryControllerTest {

    private MockMvc mvc = MockMvcBuilders.standaloneSetup(new RESTLogEntryController())
            .addPlaceholderValue("path.vehicles", "vehicles")
            .addPlaceholderValue("path.fleets", "fleets")
            .addPlaceholderValue("path.logs", "logs")
            .setControllerAdvice(new MyExceptionHandler())
            .build();

    private static VehicleType vehicleType;
    private static Fleet fleet;
    private static Customer customer;
    private static LocalDate localDate = LocalDate.of(2016, 1, 1);
    private static Vehicle vehicle;
    private static String[] authPair;

    @BeforeClass
    public static void setup() throws Exception {
        ProductionProvider.initializeProvider("unittest");
        authPair = AuthUtil.getAdminToken();
        UUID user = new AuthenticationToken(authPair[0]).getAccountId();
        UUID function = toUUID(authPair[1]);
        try (ControllerManager manager = new ControllerManager(user, function)) {
            Address address = new Address("mystreet", "123", "lala", "12345", "land");
            customer = new Customer(address, "04789456121", "customerName1", "custBTW1", Periodicity.QUARTERLY, Periodicity.QUARTERLY);
            customer = manager.getCustomerController().create(customer);
            vehicleType = new VehicleType("vehicleType");
            vehicleType = manager.getVehicleTypeController().create(vehicleType);
            fleet = new Fleet("fleetName1", customer, address);
            fleet = manager.getFleetController().create(fleet);
            vehicle = new Vehicle("brand 1", "model A", "AAAAAAAAAAAAAAAAA", "ABC 123", 30000, 2500, vehicleType, localDate, fleet);
            vehicle = manager.getVehicleController().create(vehicle);
        } catch (DataAccessException e) {
            e.printStackTrace();
        }
    }


    @AfterClass
    public static void afterTransaction() throws Exception {
        try (DAOManager manager = ProductionProvider.getInstance().getDaoManager()) {
            manager.getFleetDAO().remove(fleet.getUuid());
            manager.getVehicleTypeDAO().remove(vehicleType.getUuid());
            manager.getCustomerDAO().remove(customer.getUuid());
        } catch (DataAccessException e) {
            e.printStackTrace();
        }
        ProductionProvider.getInstance().close();
    }

    @Test
    public void getVehicleLogs() throws Exception {
        mvc.perform(MockMvcRequestBuilders.get("/vehicles/" + UUIDToNumberString(vehicle.getUuid()) + "/logs")
                .header("Authorization", authPair[0])
                .header("Function", authPair[1])
        )
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.data", hasSize(equalTo(1))))
                .andExpect(jsonPath("$.total", equalTo(1)));
    }

    @Test
    public void getFleetLogs() throws Exception {
        // There should be a log for the creation of the fleet and the creation of the vehicle
        mvc.perform(MockMvcRequestBuilders.get("/fleets/" + UUIDToNumberString(fleet.getUuid()) + "/logs")
                .header("Authorization", authPair[0])
                .header("Function", authPair[1])
        )
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.data", hasSize(equalTo(2))))
                .andExpect(jsonPath("$.total", equalTo(2)));
    }

    /**
     * GET /vehicles/{vehicleId}/logs/{id}
     *
     * @throws Exception
     */
    @Test
    public void get() throws Exception {

        LogEntry entry = create(new LogEntry(UUID.randomUUID(), null, LogAction.CREATE, LogResource.VEHICLE));

        try {
            mvc.perform(MockMvcRequestBuilders.get("/vehicles/10/logs/" + UUIDToNumberString(entry.getUuid()))
                    .header("Authorization", authPair[0])
                    .header("Function", authPair[1])
            )
                    .andExpect(status().isOk())
                    .andExpect(jsonPath("$.id", equalTo(UUIDToNumberString(entry.getUuid()))))
                    .andExpect(jsonPath("$.object", equalTo(UUIDToNumberString(entry.getObject()))))
                    .andExpect(jsonPath("$.resource", equalTo(entry.getResource().toString())))
                    .andExpect(jsonPath("$.action", equalTo(entry.getAction().toString())));
        } finally {
            remove(entry.getUuid());
        }
    }

    @Test
    public void getNotFound() throws Exception {
        mvc.perform(MockMvcRequestBuilders.get("/vehicles/10/logs/10")
                .header("Authorization", authPair[0])
                .header("Function", authPair[1]))
                .andExpect(status().isNotFound());
    }

    private LogEntry create(LogEntry entry) throws Exception {
        try (DAOManager manager = ProductionProvider.getInstance().getDaoManager()) {
            return manager.getLogEntryDao().create(entry);
        }
    }

    private void remove(UUID uuid) throws Exception {
        try (DAOManager manager = ProductionProvider.getInstance().getDaoManager()) {
            manager.getLogEntryDao().remove(uuid);
        }
    }
}
