package spring.controller;

import controller.AbstractController;
import controller.VehicleController;
import controller.VehicleTypeController;
import controller.exceptions.UnAuthorizedException;
import dao.interfaces.DataAccessException;
import model.fleet.VehicleType;
import org.springframework.web.bind.annotation.*;
import spring.exceptions.InvalidInputException;
import spring.exceptions.NotAuthorizedException;
import spring.exceptions.NotFoundException;
import spring.model.RESTAuthenticationToken;
import spring.model.RESTModelFactory;
import spring.model.RESTSchema;
import spring.model.RESTVehicleType;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by jorg on 3/14/17.
 * Although the api doesn't offer this, this is an improvement to the program
 * It offers the frontend applications a way to view wich vehicletypes are correct inputs
 * may be enlarged when admin gets rights to edit vehicletypes
 */
@RestController
@RequestMapping("/vehicleTypes")
public class RESTVehicleTypeController extends RESTAbstractController<RESTVehicleType,VehicleType> {

    public RESTVehicleTypeController() {
        super(VehicleTypeController::new, RESTVehicleType::new);
    }

    @RequestMapping(method = RequestMethod.GET)
    public RESTSchema<RESTVehicleType> getAllVehileTypes(
            @RequestParam(required = false) Integer page,
            @RequestParam(required = false) Integer limit,
            @RequestHeader(value="AuthToken") RESTAuthenticationToken token){
        List<RESTVehicleType> restVehicleTypes=new ArrayList<>();
        try(VehicleTypeController controller=new VehicleTypeController(verifyToken(token))) {
            for (VehicleType vehicleType : controller.getAll()) {
                restVehicleTypes.add(new RESTVehicleType(vehicleType));
            }

        } catch (DataAccessException e) {
            throw new InvalidInputException("Some parameters where invalid");
        }catch (UnAuthorizedException e) {
            throw new NotAuthorizedException();
        }

        return new RESTSchema<>(restVehicleTypes, page, limit, "/vehicleTypes?");
    }




}
