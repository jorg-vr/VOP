package spring.controller;

import controller.FleetController;
import controller.VehicleController;

import controller.VehicleTypeController;
import controller.exceptions.UnAuthorizedException;
import dao.interfaces.DataAccessException;
import dao.interfaces.Filter;
import dao.interfaces.VehicleDAO;
import model.fleet.Fleet;
import model.fleet.Vehicle;
import org.springframework.web.bind.annotation.*;
import spring.exceptions.InvalidInputException;
import spring.exceptions.NotAuthorizedException;
import spring.model.RESTSchema;
import spring.model.RESTVehicle;

import javax.servlet.http.HttpServletRequest;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

import java.util.*;

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
        super(VehicleController::new, RESTVehicle::new);
    }

    /***
     * @return
     */
    @RequestMapping(method = RequestMethod.GET)
    public RESTSchema<RESTVehicle> get(HttpServletRequest request,
                                       @PathVariable Optional<String> fleetId,
                                       @RequestParam(required = false) String licensPlate,
                                       @RequestParam(required = false) String vin,
                                       @RequestParam(required = false) String leasingCompany,
                                       @RequestParam(required = false) String year,
                                       @RequestParam(required = false) String fleet,
                                       @RequestParam(required = false) String type,
                                       @RequestParam(required = false) Integer page,
                                       @RequestParam(required = false) Integer limit,
                                       @RequestHeader(value = "Authorization") String token,
                                       @RequestHeader(value = "Function") String function) throws UnAuthorizedException {
        if (fleetId.isPresent()) {
            fleet = fleetId.get();
        }

        try (VehicleController controller = new VehicleController(verifyToken(token, function))) {
            VehicleDAO vehicleDAO = (VehicleDAO) controller.getDao();
            List<Filter<Vehicle>> filters = new ArrayList<>();
            if (licensPlate != null) {
                filters.add(vehicleDAO.byLicensePlate(licensPlate));
            }
            if (vin != null) {
                //TODO after issue #87
            }
            if (leasingCompany != null) {
                //TODO after issue #88
            }
            if (year != null) {
                filters.add(vehicleDAO.atProductionDate(LocalDate.parse(year + "0101", yearFormat)));
            }
            if (fleet != null) {
                Fleet fl;
                try (FleetController fleetController = new FleetController(verifyToken(token, function))) {
                    fl = fleetController.get(UUIDUtil.toUUID(fleet));
                } catch (DataAccessException e) {
                    throw new InvalidInputException("Something went wrong when getting the fleet from the database");
                } catch (UnAuthorizedException e) {
                    throw new NotAuthorizedException();
                }
                filters.add(vehicleDAO.byFleet(fl));
            }
            if (type != null) {
                try (VehicleTypeController vehicleTypeController = new VehicleTypeController(verifyToken(token, function))) {
                    filters.add(vehicleDAO.byType(vehicleTypeController.get(UUIDUtil.toUUID(type))));
                } catch (DataAccessException e) {
                    throw new InvalidInputException("Could not find requested type");
                } catch (UnAuthorizedException e) {
                    throw new NotAuthorizedException();
                }
            }
            List<RESTVehicle> result = new ArrayList<>();
            for (Vehicle vehicle : controller.getAll(filters.toArray(new Filter[filters.size()]))) {
                result.add(new RESTVehicle(vehicle));
            }
            return new RESTSchema<>(result, page, limit, request);

        } catch (DataAccessException e) {
            throw new InvalidInputException("Some parameters where invalid");
        }
    }
}