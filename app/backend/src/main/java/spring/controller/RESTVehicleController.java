package spring.controller;

import controler.VehicleControler;
import model.fleet.Vehicle;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import spring.model.RESTVehicle;

import java.util.List;

/**
 * Created by jorg on 3/6/17.
 */


@RestController
@RequestMapping("/vehicle")
public class RESTVehicleController {

    @Autowired
    private VehicleControler controler;

    @RequestMapping(method = RequestMethod.GET,@RequestBody RESTVehicle vehicle)
    public List<RESTVehicle> getAllVehicles(@RequestBody RESTVehicle vehicle) {
        return null; //TODO
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

        return new ResponseEntity<String>("Vehicle updated successfully",HttpStatus.OK);
    }

    @RequestMapping(method = RequestMethod.DELETE , value = "{id}")
    public ResponseEntity<?> deleteVehicle(@PathVariable("id") String id) {

        return new ResponseEntity<String>("Vehicle updated successfully",HttpStatus.OK);
    }
}