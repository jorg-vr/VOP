package spring.controller;

import controler.VehicleControler;

import dao.DataAccessException;
import model.fleet.Vehicle;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import spring.model.RESTVehicle;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.UUID;

/**
 * Created by jorg on 3/6/17.
 */


@RestController
@RequestMapping("/vehicle")
public class RESTVehicleController {
    private DateTimeFormatter yearFormat=DateTimeFormatter.ofPattern("yyyy");

    @Autowired
    private VehicleControler controler;

    @RequestMapping(method = RequestMethod.GET)
    public ResponseEntity<?> getAllVehicles(@RequestBody RESTVehicle vehicle) {
        return new ResponseEntity<>("not implemented",HttpStatus.NOT_IMPLEMENTED); //TODO when filters are fixed
    }

    @RequestMapping(method = RequestMethod.POST)
    public ResponseEntity<?> postVehicles(@PathVariable("id") String id, @RequestBody RESTVehicle vehicle) {
        try {
            controler.create(vehicle.getBrand(),vehicle.getModel(),vehicle.getLicense_plate(),LocalDate.parse(vehicle.getYear(),yearFormat),vehicle.getChassis_number(),vehicle.getKilometer_count())
            return new ResponseEntity<>("OK", HttpStatus.valueOf(200));
        } catch (DataAccessException e) {
            return new ResponseEntity<>("invalid input, object invalid", HttpStatus.valueOf(400));
        }
    }

    @RequestMapping(method = RequestMethod.GET , value = "{id}")
    public ResponseEntity<?> getVehicle(@PathVariable("id") String id) {

        try {
            return new ResponseEntity<>(controler.get(id), HttpStatus.valueOf(200));

        } catch (DataAccessException e) {
            return new ResponseEntity<>("Resource not found.", HttpStatus.valueOf(404));
        }
    }


    @RequestMapping(method = RequestMethod.PUT , value = "{id}")
    public ResponseEntity<?> putVehicle(@PathVariable("id") String id, @RequestBody RESTVehicle vehicle) {
        try {
            controler.get(id);
        } catch (DataAccessException e) {
            return new ResponseEntity<>("Resource not found.", HttpStatus.valueOf(404));
        }
        try {
            controler.update(restToModel(vehicle));
            return new ResponseEntity<>("OK", HttpStatus.valueOf(200));
        } catch (DataAccessException e) {
            return new ResponseEntity<>("Error in input.", HttpStatus.valueOf(400));
        }

    }

    @RequestMapping(method = RequestMethod.DELETE , value = "{id}")
    public ResponseEntity<?> deleteVehicle(@PathVariable("id") String id) {

        try {
            controler.remove(controler.get(id));
            return new ResponseEntity<>("OK",HttpStatus.valueOf(200));
        } catch (DataAccessException e) {
            return new ResponseEntity<>("Resource not found.", HttpStatus.valueOf(404));
        }
    }

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