package spring.controller;

import controller.AbstractController;
import controller.FleetController;
import controller.VehicleController;

import controller.exceptions.UnAuthorizedException;
import dao.interfaces.DataAccessException;
import dao.interfaces.Filter;
import dao.interfaces.VehicleDAO;
import model.fleet.Fleet;
import model.fleet.Vehicle;
import org.springframework.web.bind.annotation.*;
import spring.exceptions.InvalidInputException;
import spring.exceptions.NotAuthorizedException;
import spring.exceptions.NotFoundException;
import spring.model.RESTModelFactory;
import spring.model.RESTSchema;
import spring.model.RESTVehicle;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

import java.util.*;

/**
 * This controller is responsible for handling the HTTP requests of the URL /vehicles.
 * Currently, the following HTTP requests are supported:
 *  1) GET /vehicles
 *  2) GET /vehicles/{id}
 *  3) POST /vehicles
 *  4) PUT /vehicles/{id}
 *  5) DELETE /vehicles/{id}
 *
 *  This controller is responsible for translating the RESTModels to the backend specific models and calling the appropriate methods
 *  of the spring independent controllers,  located in the controller package.
 *  It is also responsible for translating the backend specific exceptions to HTPP repsonse codes.
 *
 *  For more information about what the HTTP requests do, see the API specification
 */
@RestController
@RequestMapping("/vehicles")
public class RESTVehicleController extends RESTAbstractController<RESTVehicle,Vehicle> {

    public static final String PATH_VEHICLE = "/vehicles";

    private static DateTimeFormatter yearFormat = DateTimeFormatter.ofPattern("yyyyMMdd").withLocale(Locale.forLanguageTag("NL"));

    private VehicleController controller = new VehicleController();

    public RESTVehicleController() {
        super(new VehicleController(), RESTVehicle::new);
    }

    /***
     * @return
     */
    @RequestMapping(method = RequestMethod.GET)
    public RESTSchema<RESTVehicle> get(@RequestParam(required = false) String licensPlate,
                                       @RequestParam(required = false) String vin,
                                       @RequestParam(required = false) String leasingCompany,
                                       @RequestParam(required = false) String year,
                                       @RequestParam(required = false) String fleet,
                                       @RequestParam(required = false) String type,
                                       @RequestParam(required = false) Integer page,
                                       @RequestParam(required = false) Integer limit) {
        String baseString = PATH_VEHICLE +"?";
        VehicleDAO vehicleDAO = (VehicleDAO) controller.getDao();
        List<Filter<Vehicle>> filters = new ArrayList<>();
        if (licensPlate != null) {
            baseString += "licensPlate=" + licensPlate + "&";
            filters.add(vehicleDAO.byLicensePlate(licensPlate));
        }
        if (vin != null) {
            //TODO after issue #87
        }
        if (leasingCompany != null) {
            //TODO after issue #88
        }
        if (year != null) {
            baseString += "year=" + year + "&";
            filters.add(vehicleDAO.atProductionDate(LocalDate.parse(year + "0101", yearFormat)));
        }
        if (fleet != null) {
            baseString += "fleet=" + fleet + "&";
            Fleet fl;
            try {
                fl = new FleetController().get(UUIDUtil.toUUID(fleet));
            } catch (DataAccessException e) {
                throw new InvalidInputException("Something went wrong when getting the fleet from the database");
            }
            filters.add(vehicleDAO.byFleet(fl));
        }
        if (type != null) {
            baseString += "type=" + type;
            try {
                filters.add(vehicleDAO.byType(controller.getVehicleType(UUIDUtil.toUUID(type))));
            } catch (DataAccessException e) {
                throw new InvalidInputException("Could not find requested type");
            }
        }
        List<RESTVehicle> result = new ArrayList<>();
        try {
            for (Vehicle vehicle : controller.getAll(filters.toArray(new Filter[filters.size()]))) {
                result.add(new RESTVehicle(vehicle));
            }

        } catch (DataAccessException e) {
            throw new InvalidInputException("Some parameters where invalid");
        }catch (UnAuthorizedException e) {
            throw new NotAuthorizedException();
        }

        return new RESTSchema<>(result, page, limit, baseString);
    }


}