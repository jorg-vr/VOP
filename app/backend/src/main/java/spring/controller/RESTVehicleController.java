package spring.controller;

import controller.VehicleController;

import dao.interfaces.DataAccessException;
import dao.interfaces.Filter;
import dao.interfaces.VehicleDAO;
import model.fleet.Vehicle;
import org.springframework.web.bind.annotation.*;
import spring.Exceptions.InvalidInputException;
import spring.Exceptions.NotFoundException;
import spring.model.RESTVehicle;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

import java.util.*;

/**
 * Created by jorg on 3/6/17.
 * Spring class that implements swagger api for vehicle
 */


@RestController
@RequestMapping("/vehicle")
public class RESTVehicleController {
    private static DateTimeFormatter yearFormat=DateTimeFormatter.ofPattern("yyyyMMdd").withLocale(Locale.forLanguageTag("NL"));

    //TODO find out if this is usefull
    //@Autowired
    private VehicleController controller=new VehicleController();

    /***
     * Not yet implemented
     * @return
     */
    @RequestMapping(method = RequestMethod.GET)
    public Collection<RESTVehicle> getAllVehicles(@RequestParam(required=false) String licensPlate,
                                              @RequestParam(required=false) String chassisNumber,
                                              @RequestParam(required=false) Integer leasingCompany,
                                              @RequestParam(required=false) Integer year,
                                              @RequestParam(required=false) Integer company) {

        VehicleDAO vehicleDAO=controller.getVehicleDAO();
        List<Filter<Vehicle>> filters=new ArrayList<>();
        if (licensPlate!=null){filters.add(vehicleDAO.byLicensePlate(licensPlate));}
        if (chassisNumber!=null)//TODO after issue #87
        if (leasingCompany!=null)//TODO after issue #88
        if (year!=null){filters.add(vehicleDAO.atProductionDate(LocalDate.ofYearDay(year,0)));}
        if (company!=null)//TODO after issue #88
        if (licensPlate!=null){filters.add(vehicleDAO.byLicensePlate(licensPlate));}

        Collection<RESTVehicle> result=new ArrayList<>();
        try {
            for(Vehicle vehicle : controller.listFiltered( filters.toArray(new Filter[filters.size()]))){
                result.add(modelToRest(vehicle));
            }

        } catch (DataAccessException e) {
            //API doesn't contain error
        }
        return result;
    }

    /***
     * implement post method, see api
     * @param vehicle
     * @return
     */
    @RequestMapping(method = RequestMethod.POST)
    public void postVehicles(@RequestBody RESTVehicle vehicle) {
        try {
            LocalDate year = LocalDate.parse(vehicle.getYear()+"0101", yearFormat);//Fix conversion bug
            controller.create(vehicle.getBrand(),
                    vehicle.getModel(),
                    vehicle.getLicensePlate(),
                    year,
                    vehicle.getChassisNumber(),
                    vehicle.getKilometerCount(),
                    vehicle.getType());
        } catch (DataAccessException e) {
            throw new InvalidInputException(e);
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
            controller.update(id,restToModel(vehicle));
        } catch (DataAccessException e) {
            e.printStackTrace();
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
     * TODO convert type field to corresponding VehicleType object
     * converts restVehicle to vehicle like implemented in model
     * @param restVehicle
     * @return
     */
    private Vehicle restToModel(RESTVehicle restVehicle){
        return new Vehicle(UUID.fromString(restVehicle.getId()),
                restVehicle.getBrand(),
                restVehicle.getModel(),
                restVehicle.getLicensePlate(),
                LocalDate.parse(restVehicle.getYear(),yearFormat),
                restVehicle.getChassisNumber(),
                0,//TODO fix value
                restVehicle.getKilometerCount(),
                null//TODO String value of type has to be converted to the corresponding VehicleType object
        );
    }

    /***
     * converts model vehicle to vehicle needed for rest api
     * @param vehicle
     * @return
     */
     private RESTVehicle modelToRest(Vehicle vehicle){
        return new RESTVehicle(vehicle.getUuid().toString(),
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