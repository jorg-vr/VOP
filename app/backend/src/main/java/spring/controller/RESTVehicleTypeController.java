package spring.controller;

import controller.VehicleController;
import controller.VehicleTypeController;
import controller.exceptions.UnAuthorizedException;
import dao.interfaces.DataAccessException;
import model.fleet.VehicleType;
import org.springframework.web.bind.annotation.*;
import spring.exceptions.InvalidInputException;
import spring.exceptions.NotAuthorizedException;
import spring.exceptions.NotFoundException;
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
public class RESTVehicleTypeController {

    VehicleTypeController controller=new VehicleTypeController();
    @RequestMapping(method = RequestMethod.GET)
    public RESTSchema<RESTVehicleType> getAllVehileTypes(
            @RequestParam(required = false) Integer page,
            @RequestParam(required = false) Integer limit){
        List<RESTVehicleType> restVehicleTypes=new ArrayList<>();
        try {
            for (VehicleType vehicleType : controller.getAll()) {
                restVehicleTypes.add(modelToREST(vehicleType));
            }

        } catch (DataAccessException e) {
            throw new InvalidInputException("Some parameters where invalid");
        }catch (UnAuthorizedException e) {
            throw new NotAuthorizedException();
        }

        return new RESTSchema<>(restVehicleTypes, page, limit, "/vehicleTypes?");
    }
    private RESTVehicleType modelToREST(VehicleType vehicleType){
        return new RESTVehicleType(UUIDUtil.UUIDToNumberString(vehicleType.getUuid()),vehicleType.getType());
    }

    @RequestMapping(method = RequestMethod.GET,value = "{id}")
    public RESTVehicleType getId(@PathVariable("id") String id) {

        try {
            return modelToREST(controller.get(UUIDUtil.toUUID(id)));

        } catch (DataAccessException e) {
            throw new NotFoundException();
        }catch (UnAuthorizedException e) {
            throw new NotAuthorizedException();
        }
    }



}
