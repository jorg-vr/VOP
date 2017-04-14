package spring.controller;

import controller.VehicleTypeController;
import controller.exceptions.UnAuthorizedException;
import dao.interfaces.DataAccessException;
import model.fleet.VehicleType;
import org.springframework.web.bind.annotation.*;
import spring.exceptions.InvalidInputException;
import spring.exceptions.NotAuthorizedException;
import spring.model.RESTSchema;
import spring.model.RESTVehicleType;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by jorg on 3/14/17.
 * Although the api doesn't offer this, this is an improvement to the program
 * It offers the frontend applications a way to view wich vehicletypes are correct inputs
 * may be enlarged when admin gets rights to edit vehicletypes
 */
@RestController
@RequestMapping("/${path.vehicles}/${path.types}")
public class RESTVehicleTypeController extends RESTAbstractController<RESTVehicleType, VehicleType> {

    public RESTVehicleTypeController() {
        super(VehicleTypeController::new, RESTVehicleType::new);
    }

    @RequestMapping(method = RequestMethod.GET)
    public RESTSchema<RESTVehicleType> getAllVehileTypes(HttpServletRequest request,
                                                         @RequestParam(required = false) Integer page,
                                                         @RequestParam(required = false) Integer limit,
                                                         @RequestHeader(value = "Authorization") String token,
                                                         @RequestHeader(value = "Function") String function) throws UnAuthorizedException {
        List<RESTVehicleType> restVehicleTypes = new ArrayList<>();
        try (VehicleTypeController controller = new VehicleTypeController(verifyToken(token, function))) {
            for (VehicleType vehicleType : controller.getAll()) {
                restVehicleTypes.add(new RESTVehicleType(vehicleType));
            }

        } catch (DataAccessException e) {
            throw new InvalidInputException("Some parameters where invalid");
        }
        return new RESTSchema<>(restVehicleTypes, page, limit, request);
    }

}
