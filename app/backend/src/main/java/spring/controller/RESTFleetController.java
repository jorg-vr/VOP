package spring.controller;

import controller.CustomerController;
import controller.FleetController;
import dao.interfaces.FleetDAO;
import model.fleet.Fleet;
import org.springframework.web.bind.annotation.*;
import spring.exceptions.InvalidInputException;
import spring.model.RESTFleet;
import spring.model.RESTSchema;

import java.util.ArrayList;
import java.util.Collection;

/**
 * This controller is responsible for handling the HTTP requests of the URL /fleets.
 * Currently, the following HTTP requests are supported:
 * 1) GET /fleet
 * 2) GET /fleets/{id}
 * 3) POST /fleets
 * 4) PUT /fleets/{id}
 * 5) DELETE /fleets/{id}
 * <p>
 * This controller is responsible for translating the RESTModels to the backend specific models and calling the appropriate methods
 * of the spring independent controllers,  located in the controller package.
 * It is also responsible for translating the backend specific exceptions to HTPP repsonse codes.
 * <p>
 * For more information about what the HTTP requests do, see the API specification
 */
@RestController
@RequestMapping("/fleets")
public class RESTFleetController extends RESTAbstractController<RESTFleet,Fleet>{

    public static final String PATH_FLEETS = "/fleets";


    public RESTFleetController() {
        super(FleetController::new, RESTFleet::new);
    }


    @RequestMapping(method = RequestMethod.GET)
    public RESTSchema<RESTFleet> get(@RequestParam(required = false) String company,
                                     @RequestParam(required = false) Integer page,
                                     @RequestParam(required = false) Integer limit,
                                     @RequestHeader(value="AuthToken") String token,
                                     @RequestHeader(value="Function") String function) {

        try(FleetController controller= new FleetController(verifyToken(token,function))) {
            FleetDAO fleetDAO = (FleetDAO) controller.getDao();
            String baseString = PATH_FLEETS + "?";
            Collection<RESTFleet> restFleets = new ArrayList<>();
            Collection<Fleet> fleets;
            if (company != null) {
                try(CustomerController customerController= new CustomerController(verifyToken(token,function))) {
                    fleets = customerController.get(UUIDUtil.toUUID(company)).getFleets();
                }
            } else {
                fleets = controller.getAll();
            }
            for (Fleet f : fleets) {
                restFleets.add(new RESTFleet(f));
            }
            return new RESTSchema<>(restFleets, page, limit, baseString);
        } catch (Exception e) {
            e.printStackTrace();
            throw new InvalidInputException();
        }

    }

}
