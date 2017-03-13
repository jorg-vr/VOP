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
@RequestMapping("/vehicles")
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
                                                  @RequestParam(required=false) String leasingCompany,
                                                  @RequestParam(required=false) String year,
                                                  @RequestParam(required=false) String company,
                                                  @RequestParam(required=false) String type,
                                                  @RequestParam(required=false) Integer page,
                                                  @RequestParam(required=false) Integer limit) {
        VehicleDAO vehicleDAO= (VehicleDAO) controller.getDao();
        List<Filter<Vehicle>> filters=new ArrayList<>();
        if (licensPlate!=null){filters.add(vehicleDAO.byLicensePlate(licensPlate));}
        if (chassisNumber!=null)//TODO after issue #87
        if (leasingCompany!=null)//TODO after issue #88
        if (year!=null){filters.add(vehicleDAO.atProductionDate(LocalDate.ofYearDay(year,0)));}
        if (company!=null)//TODO after issue #88
        if (licensPlate!=null){filters.add(vehicleDAO.byLicensePlate(licensPlate));}

        Collection<RESTVehicle> result=new ArrayList<>();
        try {
            for(Vehicle vehicle : controller.getAll( filters.toArray(new Filter[filters.size()]))){
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
                    vehicle.getVin(),
                    vehicle.getMileage(),
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
            return modelToRest(controller.get(UUID.fromString(id)));

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
            LocalDate year = LocalDate.parse(vehicle.getYear()+"0101", yearFormat);//Fix conversion bug
            controller.update(UUID.fromString(id),vehicle.getBrand(),
                    vehicle.getModel(),
                    vehicle.getLicensePlate(),
                    year,
                    vehicle.getVin(),
                    vehicle.getMileage(),
                    vehicle.getType());
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
            controller.archive(UUID.fromString(id));
        } catch (DataAccessException e) {
            throw new NotFoundException();
        }
    }


    /***
     * converts model vehicle to vehicle needed for rest api
     * @param vehicle
     * @return
     */
     private RESTVehicle modelToRest(Vehicle vehicle){
        return new RESTVehicle(UUIDUtil.UUIDToNumberString(vehicle.getUuid()),
                vehicle.getLicensePlate(),
                vehicle.getChassisNumber(),
                vehicle.getBrand(),
                vehicle.getModel(),
                UUIDUtil.UUIDToNumberString(vehicle.getType().getUuid()),
                vehicle.getMileage(),
                vehicle.getProductionDate().format(yearFormat),
                UUIDUtil.UUIDToNumberString(vehicle.getLeasingCompany().getUuid()),
                UUIDUtil.UUIDToNumberString(vehicle.getFleet().getUuid()),
                null,//TODO search leasing company
                null,//TODO implement edit dates with history
                null,
                "/vehicles/"+vehicle.getUuid().toString()
        );
    }
}