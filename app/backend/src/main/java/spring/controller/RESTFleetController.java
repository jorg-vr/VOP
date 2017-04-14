package spring.controller;

import controller.CustomerController;
import controller.FleetController;
import controller.exceptions.UnAuthorizedException;
import dao.interfaces.DataAccessException;
import model.fleet.Fleet;
import org.springframework.web.bind.annotation.*;
import spring.exceptions.InvalidInputException;
import spring.model.RESTFleet;
import spring.model.RESTSchema;
import util.UUIDUtil;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Optional;

/**
 * This controller is responsible for handling the HTTP requests of the URLs /fleets and /companies/{companyId}/fleets.
 * Currently, the following HTTP requests are supported:
 * 1)  GET /fleet
 * 2)  GET /fleets/{id}
 * 3)  POST /fleets
 * 4)  PUT /fleets/{id}
 * 5)  DELETE /fleets/{id}
 * 6)  GET companies/{companyId}/fleet
 * 7)  GET companies/{companyId}/fleets/{id}
 * 8)  POST companies/{companyId}/fleets
 * 9)  PUT companies/{companyId}/fleets/{id}
 * 10) DELETE companies/{companyId}/fleets/{id}
 * <p>
 * This controller is responsible for translating the RESTModels to the backend specific models and calling the appropriate methods
 * of the spring independent controllers,  located in the controller package.
 * It is also responsible for translating the backend specific exceptions to HTPP repsonse codes.
 * <p>
 * For more information about what the HTTP requests do, see the API specification
 */
@RestController
@RequestMapping(value = {"/${path.fleets}", "/${path.companies}/{companyId}/${path.fleets}"})
public class RESTFleetController extends RESTAbstractController<RESTFleet,Fleet>{

    public RESTFleetController() {
        super(FleetController::new, RESTFleet::new);
    }


    @RequestMapping(method = RequestMethod.GET)
    public RESTSchema<RESTFleet> get(HttpServletRequest request,
                                     @PathVariable Optional<String> companyId,
                                     @RequestParam(required = false) String company,
                                     @RequestParam(required = false) Integer page,
                                     @RequestParam(required = false) Integer limit,
                                     @RequestHeader(value="Authorization") String token,
                                     @RequestHeader(value="Function") String function) throws UnAuthorizedException {
        if (companyId.isPresent()) {
            company = companyId.get();
        }

        try(FleetController controller= new FleetController(verifyToken(token,function))) {

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
            return new RESTSchema<>(restFleets, page, limit, request);
        } catch (DataAccessException e) {
            e.printStackTrace();
            throw new InvalidInputException();
        }
    }

}
