package spring.controller;

import controller.CustomerController;
import controller.FleetController;
import dao.interfaces.DataAccessException;
import dao.interfaces.Filter;
import dao.interfaces.FleetDAO;
import model.fleet.Fleet;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import spring.Exceptions.InvalidInputException;
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
            fleets.subList(page*limit,(page+1)*limit);
            return new RESTSchema<>(fleets,total,page,limit,baseString);
        } catch (DataAccessException e) {
            throw  new InvalidInputException();
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
