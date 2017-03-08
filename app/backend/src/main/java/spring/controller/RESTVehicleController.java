package spring.controller;

import controller.VehicleController;

import dao.interfaces.DataAccessException;
import model.fleet.Vehicle;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import spring.Exceptions.InvalidInputException;
import spring.Exceptions.NotFoundException;
import spring.Exceptions.NotImplementedException;
import spring.model.RESTVehicle;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

import java.util.Collection;
import java.util.UUID;

/**
 * Created by jorg on 3/6/17.
 * Spring class that implements swagger api for vehicle
 */


@RestController
@RequestMapping("/vehicle")
public class RESTVehicleController {
    private DateTimeFormatter yearFormat=DateTimeFormatter.ofPattern("yyyy");

    //TODO find out if this is usefull
    @Autowired
    private VehicleController controller;

    /***
     * Not yet implemented
     * @param vehicle
     * @return
     */
    @RequestMapping(method = RequestMethod.GET)
    public Collection<RESTVehicle> getAllVehicles(@RequestBody RESTVehicle vehicle) {
        throw new NotImplementedException(); //TODO when filters are fixed
    }

    /***
     * implement post method, see api
     * @param id
     * @param vehicle
     * @return
     */
    @RequestMapping(method = RequestMethod.POST)
    public void postVehicles(@PathVariable("id") String id, @RequestBody RESTVehicle vehicle) {
        try {
            controller.create(vehicle.getBrand(),
                    vehicle.getModel(),
                    vehicle.getLicense_plate(),
                    LocalDate.parse(vehicle.getYear(),yearFormat),
                    vehicle.getChassis_number(),
                    vehicle.getKilometer_count());
        } catch (DataAccessException e) {
            throw new InvalidInputException();
        }
    }

    /***
     * implement get method, see api
     * @param id
     * @return
     */
    @RequestMapping(method = RequestMethod.GET , value = "{id}")
    public RESTVehicle getVehicle(@PathVariable("id") String id) {

        try {
            return modelToRest(controller.get(id));

        } catch (DataAccessException e) {
            throw new NotFoundException();
        }
    }

    /***
     * implement put method, see api
     * @param id
     * @param vehicle
     * @return
     */
    @RequestMapping(method = RequestMethod.PUT , value = "{id}")
    public void putVehicle(@PathVariable("id") String id, @RequestBody RESTVehicle vehicle) {
        try {
            controller.get(id);
        } catch (DataAccessException e) {
            throw new NotFoundException();
        }
        try {
            controller.update(restToModel(vehicle));
        } catch (DataAccessException e) {
            throw new InvalidInputException();
        }

    }

    /***
     * implement delete method, see api
     * @param id
     * @return
     */
    @RequestMapping(method = RequestMethod.DELETE , value = "{id}")
    public void deleteVehicle(@PathVariable("id") String id) {

        try {
            controller.remove(controller.get(id));
        } catch (DataAccessException e) {
            throw new NotFoundException();
        }
    }

    /***
     * converts restVehicle to vehicle like implemented in model
     * @param restVehicle
     * @return
     */
    private Vehicle restToModel(RESTVehicle restVehicle){
        return new Vehicle(UUID.fromString(restVehicle.getId()),
                restVehicle.getBrand(),
                restVehicle.getModel(),
                restVehicle.getLicense_plate(),
                LocalDate.parse(restVehicle.getYear(),yearFormat),
                restVehicle.getChassis_number(),
                0,//TODO fix value
                restVehicle.getKilometer_count()
        );
    }

    /***
     * converts model vehicle to vehicle needed for rest api
     * @param vehicle
     * @return
     */
     private RESTVehicle modelToRest(Vehicle vehicle){
        return new RESTVehicle(vehicle.getId().toString(),
                vehicle.getLicensePlate(),
                vehicle.getChassisNumber(),
                vehicle.getBrand(),
                vehicle.getModel(),
                null,//TODO fix type
                vehicle.getMileage(),
                vehicle.getProductionDate().format(yearFormat),
                null,//TODO search leasing company
                null,//TODO implement edit dates with history
                null,
                null// todo calc URL
        );
    }

}