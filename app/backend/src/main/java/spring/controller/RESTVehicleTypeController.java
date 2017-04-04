package spring.controller;

import controller.VehicleController;
import dao.interfaces.DataAccessException;
import model.fleet.VehicleType;
import org.springframework.web.bind.annotation.*;
import spring.exceptions.InvalidInputException;
import spring.exceptions.NotFoundException;
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
@RequestMapping("/vehicleTypes")
public class RESTVehicleTypeController {

    @RequestMapping(method = RequestMethod.GET)
    public RESTSchema<RESTVehicleType> getAllVehileTypes(HttpServletRequest request,
                                                         @RequestParam(required = false) Integer page,
                                                         @RequestParam(required = false) Integer limit) {
        List<RESTVehicleType> restVehicleTypes = new ArrayList<>();
        try {
            for (VehicleType vehicleType : new VehicleController().getAllVehicleTypes()) {
                restVehicleTypes.add(modelToREST(vehicleType));
            }

        } catch (DataAccessException e) {
            throw new InvalidInputException("Some parameters where invalid");
        }

        return new RESTSchema<>(restVehicleTypes, page, limit, request);
    }

    private RESTVehicleType modelToREST(VehicleType vehicleType) {
        return new RESTVehicleType(UUIDUtil.UUIDToNumberString(vehicleType.getUuid()), vehicleType.getType());
    }

    @RequestMapping(method = RequestMethod.GET, value = "{id}")
    public RESTVehicleType getId(@PathVariable("id") String id) {

        try {
            return modelToREST(new VehicleController().getVehicleType(UUIDUtil.toUUID(id)));

        } catch (DataAccessException e) {
            throw new NotFoundException();
        }
    }


}
