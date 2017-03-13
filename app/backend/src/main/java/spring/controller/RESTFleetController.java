package spring.controller;

import controller.CustomerController;
import controller.FleetController;
import dao.interfaces.DataAccessException;
import dao.interfaces.Filter;
import dao.interfaces.FleetDAO;
import model.fleet.Fleet;
import org.springframework.web.bind.annotation.*;
import spring.Exceptions.InvalidInputException;
import spring.Exceptions.NotFoundException;
import spring.model.RESTFleet;
import spring.model.RESTSchema;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

/**
 * Created by jorg on 3/13/17.
 */
@RestController
@RequestMapping("/fleets")
public class RESTFleetController {

    private FleetController controller=new FleetController();
    private CustomerController customerController=new CustomerController();

    @RequestMapping(method = RequestMethod.GET)
    private RESTSchema<RESTFleet> getAllFleets(@RequestParam(required=false) String company,
                                               @RequestParam(required=false) Integer page,
                                               @RequestParam(required=false) Integer limit){
        FleetDAO fleetDAO= (FleetDAO) controller.getDao();
        try {
            String baseString="/fleets?";
            List<Filter<Fleet>> filters=new ArrayList<>();
            if(company!=null){
                baseString+="company="+company+"&";
                filters.add(fleetDAO.byOwner(customerController.get(UUIDUtil.toUUID(company))));
            }
            List<RESTFleet> fleets=new ArrayList<>();
            for(Fleet fleet:controller.getAll(filters.toArray(new Filter[filters.size()]))){
                fleets.add(modelToRest(fleet));
            }
            int total=fleets.size();
            fleets.sort((fleet1,fleet2)->fleet1.getName().compareTo(fleet2.getName()));
            if(limit!=null){
                fleets=fleets.subList(page*limit,(page+1)*limit);
            }
            return new RESTSchema<>(fleets,total,page,limit,baseString);
        } catch (Exception e) {
            throw  new InvalidInputException();
        }

    }

    @RequestMapping(method = RequestMethod.GET, value = "{id}")
    public RESTFleet getFleet(@PathVariable("id") String id) {
        try {
            return modelToRest(controller.get(UUIDUtil.toUUID(id)));

        } catch (DataAccessException e) {
            throw new NotFoundException();
        }
    }

    @RequestMapping(method = RequestMethod.POST)
    public RESTFleet createFleet(@RequestBody RESTFleet restFleet){
        try {
            return modelToRest(controller.create(UUIDUtil.toUUID( restFleet.getCompany()),restFleet.getName()));
        } catch (DataAccessException e) {
            throw new InvalidInputException();
            //TODO update when there are more exceptions
        }
    }

    @RequestMapping(method = RequestMethod.PUT, value = "{id}")
    public RESTFleet updateFleet(@PathVariable("id") String id,@RequestBody RESTFleet restFleet){
        try {
            return modelToRest(controller.update(UUIDUtil.toUUID(id),UUIDUtil.toUUID( restFleet.getCompany()),restFleet.getName()));
        } catch (DataAccessException e) {
            throw new InvalidInputException();
            //TODO update when there are more exceptions
        }
    }

    @RequestMapping(method = RequestMethod.DELETE, value = "{id}")
    public void deleteFleet(@PathVariable("id") String id) {
        try {
            controller.archive(UUIDUtil.toUUID(id));

        } catch (DataAccessException e) {
            throw new NotFoundException();
            //TODO update when there are more exceptions
        }
    }

    private RESTFleet modelToRest(Fleet fleet){
        return new RESTFleet(UUIDUtil.UUIDToNumberString(fleet.getUuid()),
                UUIDUtil.UUIDToNumberString(fleet.getOwner().getUuid()),
                fleet.getName(),
                "",
                "",
                "",
                "/fleets/"+UUIDUtil.UUIDToNumberString(fleet.getUuid()));
    }
}
