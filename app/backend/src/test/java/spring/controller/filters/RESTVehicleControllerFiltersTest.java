package spring.controller.filters;

import dao.database.ProductionProvider;
import dao.exceptions.DataAccessException;
import dao.interfaces.DAOManager;
import model.fleet.Fleet;
import model.fleet.Vehicle;
import model.fleet.VehicleType;
import model.identity.Address;
import model.identity.Customer;
import model.identity.Periodicity;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import spring.controller.AuthUtil;
import spring.controller.RESTVehicleController;
import spring.exceptions.MyExceptionHandler;
import util.UUIDUtil;

import java.time.LocalDate;

import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.hasSize;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

/**
 * Created by Ponti on 19/05/2017.
 */
@RunWith(SpringJUnit4ClassRunner.class)
public class RESTVehicleControllerFiltersTest {

    private MockMvc mvc = MockMvcBuilders.standaloneSetup(new RESTVehicleController())
            .addPlaceholderValue("path.vehicles", "vehicles")
            .addPlaceholderValue("path.companies", "companies")
            .addPlaceholderValue("path.fleets", "fleets")
            .setControllerAdvice(new MyExceptionHandler())
            .build();

    private static VehicleType vehicleType;
    private static Fleet fleet1, fleet2, fleet3;
    private static Customer customer1, customer2;
    private static Vehicle vehicle1, vehicle2, vehicle3;
    private static LocalDate localDate = LocalDate.of(2016, 1, 1);
    private static String[] authPair;

    @BeforeClass
    public static void setup() throws Exception {
        ProductionProvider.initializeProvider("unittest");
        //authPair = AuthUtil.getAdminToken();
        try (DAOManager manager = ProductionProvider.getInstance().getDaoManager()) {
            Address address = new Address("mystreet", "123", "lala", "12345", "land");
            customer1 = new Customer(address, "04789456121", "customerName1", "custBTW1", Periodicity.QUARTERLY, Periodicity.QUARTERLY);
            customer1 = manager.getCustomerDAO().create(customer1);
            customer2 = new Customer(address, "04789456122", "customerName2", "custBTW2", Periodicity.QUARTERLY, Periodicity.QUARTERLY);
            customer2 = manager.getCustomerDAO().create(customer2);
            vehicleType = new VehicleType("vehicleType");
            vehicleType = manager.getVehicleTypeDAO().create(vehicleType);
            fleet1 = new Fleet("fleetName1", customer1, address);
            fleet1 = manager.getFleetDAO().create(fleet1);
            fleet2 = new Fleet("fleetName2", customer1, address);
            fleet2 = manager.getFleetDAO().create(fleet2);
            fleet3 = new Fleet("fleetName3", customer2, address);
            fleet3 = manager.getFleetDAO().create(fleet3);

            authPair = AuthUtil.getCustomerToken(customer1);

            vehicle1 = manager.getVehicleDAO().create(new Vehicle("brand 1", "model A", "AAAAAAAAAAAAAAAAA", "ABC 123", 30000, 2500, vehicleType, localDate, fleet1));
            vehicle2 = manager.getVehicleDAO().create(new Vehicle("brand 1", "model A", "AAAAAAAAAAAAAAAAB", "ABC 124", 30000, 2500, vehicleType, localDate, fleet2));
            vehicle3 = manager.getVehicleDAO().create(new Vehicle("brand 1", "model A", "AAAAAAAAAAAAAAAAC", "ABC 125", 30000, 2500, vehicleType, localDate, fleet3));

        } catch (DataAccessException e) {
            e.printStackTrace();
        }
    }


    @AfterClass
    public static void afterTransaction() throws Exception {
        try (DAOManager manager = ProductionProvider.getInstance().getDaoManager()) {
            manager.getVehicleDAO().remove(vehicle1.getUuid());
            manager.getVehicleDAO().remove(vehicle2.getUuid());
            manager.getVehicleDAO().remove(vehicle3.getUuid());
            manager.getFleetDAO().remove(fleet1.getUuid());
            manager.getFleetDAO().remove(fleet2.getUuid());
            manager.getFleetDAO().remove(fleet3.getUuid());
            manager.getVehicleTypeDAO().remove(vehicleType.getUuid());
            manager.getCustomerDAO().remove(customer1.getUuid());
            manager.getCustomerDAO().remove(customer2.getUuid());
        } catch (DataAccessException e) {
            e.printStackTrace();
        }
        ProductionProvider.getInstance().close();
    }

    @Test
    public void customerFilter() throws Exception {

        mvc.perform(MockMvcRequestBuilders.get("/vehicles?company=" + UUIDUtil.UUIDToNumberString(customer1.getUuid()))
                .header("Authorization", authPair[0])
                .header("Function", authPair[1])
        )
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.data", hasSize(equalTo(2))));
                //.andExpect(jsonPath("$.data[*].customer", everyItem(equalTo(UUIDUtil.UUIDToNumberString(customer2.getUuid())))));
    }
}
