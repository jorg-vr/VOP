package spring.controller;

import controller.VehicleController;

import dao.interfaces.DataAccessException;
import dao.interfaces.Filter;
import dao.interfaces.VehicleDAO;
import model.fleet.Vehicle;
import org.springframework.web.bind.annotation.*;
import spring.Exceptions.InvalidInputException;
import spring.Exceptions.NotFoundException;
import spring.model.RESTSchema;
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
    public RESTSchema<RESTVehicle> getAllVehicles(@RequestParam(required=false) String licensPlate,
                                     @RequestParam(required=false) String vin,
                                     @RequestParam(required=false) String leasingCompany,
                                     @RequestParam(required=false) String year,
                                     @RequestParam(required=false) String fleet,
                                     @RequestParam(required=false) String type,
                                     @RequestParam(required=false) Integer page,
                                     @RequestParam(required=false) Integer limit) {
        String baseString="/vehicles?";
        VehicleDAO vehicleDAO= (VehicleDAO) controller.getDao();
        List<Filter<Vehicle>> filters=new ArrayList<>();
        if (licensPlate!=null){
            baseString+="licensPlate="+licensPlate+"&";
            filters.add(vehicleDAO.byLicensePlate(licensPlate));
        }
        if (vin!=null) {
            //TODO after issue #87
        }
        if (leasingCompany!=null){
            //TODO after issue #88
        }
        if (year!=null) {
            baseString+="year="+year+"&";
            filters.add(vehicleDAO.atProductionDate(LocalDate.parse(year+"0101", yearFormat)));
        }
        if (fleet!=null){
            //TODO after issue #88
        }
        if(type!=null){
            baseString+="type="+type;
            try {
                filters.add(vehicleDAO.byType(controller.getVehicleType(type)));
            } catch (DataAccessException e) {
                throw new InvalidInputException("Could not find requested type");
            }
        }
        List<RESTVehicle> result=new ArrayList<>();
        try {
            for(Vehicle vehicle : controller.getAll( filters.toArray(new Filter[filters.size()]))){
                result.add(modelToRest(vehicle));
            }

        } catch (DataAccessException e) {
            throw new InvalidInputException("Some parameters where invalid");
        }
        int total=result.size();
        result.sort((vehicle1,vehicle2)->vehicle1.getId().compareTo(vehicle2.getId()));
        result=result.subList(page*limit,(page+1)*limit);

        return new RESTSchema<>(result,total,page,limit,baseString);
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