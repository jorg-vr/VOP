package spring.controller;

import controler.VehicleControler;

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

        return vehicle;
    }

    @RequestMapping(method = RequestMethod.GET , value = "{id}")
    public RESTVehicle getVehicle(@PathVariable("id") String id) {

        return vehicle;
    }


    @RequestMapping(method = RequestMethod.PUT , value = "{id}")
    public ResponseEntity<?> putVehicle(@PathVariable("id") String id, @RequestBody RESTVehicle vehicle) {

        return new ResponseEntity<>("Vehicle updated successfully", HttpStatus.OK);
    }

    @RequestMapping(method = RequestMethod.DELETE , value = "{id}")
    public ResponseEntity<?> deleteVehicle(@PathVariable("id") String id) {

        return new ResponseEntity<>("Vehicle updated successfully",HttpStatus.OK);
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