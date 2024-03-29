package spring.controller;

import controller.AbstractController;
import controller.ControllerManager;
import controller.VehicleController;
import controller.exceptions.UnAuthorizedException;
import dao.exceptions.DataAccessException;
import model.fleet.Fleet;
import model.fleet.Vehicle;
import model.fleet.VehicleType;
import model.identity.Customer;
import org.springframework.web.bind.annotation.*;
import spring.model.AuthenticationToken;
import spring.model.RESTSchema;
import spring.model.RESTVehicle;

import javax.servlet.http.HttpServletRequest;
import java.time.format.DateTimeFormatter;
import java.util.Collection;
import java.util.Locale;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

import static util.UUIDUtil.toUUID;

/**
 * This controller is responsible for handling the HTTP requests of the URLs /vehicles and /companies/{companyId}/fleets/{fleetId}/vehicles.
 * Currently, the following HTTP requests are supported:
 * 1)  GET /vehicles
 * 2)  GET /vehicles/{id}
 * 3)  POST /vehicles
 * 4)  PUT /vehicles/{id}
 * 5)  DELETE /vehicles/{id}
 * 6)  GET /companies/{companyId}/fleets/{fleetId}/vehicles
 * 7)  GET /companies/{companyId}/fleets/{fleetId}/vehicles/{id}
 * 8)  POST /companies/{companyId}/fleets/{fleetId}/vehicles
 * 9)  PUT /companies/{companyId}/fleets/{fleetId}/vehicles/{id}
 * 10) DELETE /companies/{companyId}/fleets/{fleetId}/vehicles/{id}
 * <p>
 * This controller is responsible for translating the RESTModels to the backend specific models and calling the appropriate methods
 * of the spring independent controllers,  located in the controller package.
 * It is also responsible for translating the backend specific exceptions to HTPP repsonse codes.
 * <p>
 * For more information about what the HTTP requests do, see the API specification
 */
@RestController
@RequestMapping(value = {"/${path.vehicles}", "/${path.companies}/{companyId}/${path.fleets}/{fleetId}/${path.vehicles}"})
public class RESTVehicleController extends RESTAbstractController<RESTVehicle, Vehicle> {

    private static DateTimeFormatter yearFormat = DateTimeFormatter.ofPattern("yyyyMMdd").withLocale(Locale.forLanguageTag("NL"));

    public RESTVehicleController() {
        super(RESTVehicle::new);
    }

    @Override
    public AbstractController<Vehicle> getController(ControllerManager manager) {
        return manager.getVehicleController();
    }

    /***
     * @return
     */
    @RequestMapping(method = RequestMethod.GET)
    public RESTSchema<RESTVehicle> get(HttpServletRequest request,
                                       @PathVariable Optional<String> fleetId,
                                       @RequestParam(required = false) String licensePlate,
                                       @RequestParam(required = false) String vin,
                                       @RequestParam(required = false) Integer year,
                                       @RequestParam(required = false) String fleet,
                                       @RequestParam(required = false) String company,
                                       @RequestParam(required = false) String type,
                                       @RequestParam(required = false) String brand,
                                       @RequestParam(required = false) String model,
                                       @RequestParam(required = false) Integer page,
                                       @RequestParam(required = false) Integer limit,
                                       @RequestHeader(value = "Authorization") String token,
                                       @RequestHeader(value = "Function") String function) throws UnAuthorizedException, DataAccessException {
        String fleetFilter = fleet;
        if (fleetId.isPresent()) {
            fleetFilter = fleetId.get();
        }

        UUID user = new AuthenticationToken(token).getAccountId();
        try (ControllerManager manager = new ControllerManager(user, toUUID(function))) {
            VehicleController controller = manager.getVehicleController();

            Fleet fleetObject = fleetFilter != null ? new Fleet(toUUID(fleetFilter)) : null;
            Customer customer = company != null ? new Customer(toUUID(company)) : null;
            VehicleType vehicleTypeObject = null;
            if(type != null){
                vehicleTypeObject = new VehicleType();
                vehicleTypeObject.setUuid(toUUID(type));
            }

            Collection<RESTVehicle> result = controller.getFiltered(licensePlate, vin, year, fleetObject, customer, vehicleTypeObject, brand, model)
                    .stream()
                    .map(RESTVehicle::new)
                    .collect(Collectors.toList());
            return new RESTSchema<>(result, page, limit, request);
        }
    }
}