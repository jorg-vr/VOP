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
import java.util.UUID;

/**
 * Created by jorg on 3/13/17.
 */
@RestController
@RequestMapping("/fleets")
public class RESTFleetController {

    public static final String PATH_FLEETS = "/fleets";

    private FleetController controller = new FleetController();
    private CustomerController customerController = new CustomerController();


    @RequestMapping(method = RequestMethod.GET)
    public RESTSchema<RESTFleet> get(@RequestParam(required = false) String company,
                                     @RequestParam(required = false) Integer page,
                                     @RequestParam(required = false) Integer limit) {
        FleetDAO fleetDAO = (FleetDAO) controller.getDao();
        try {
            String baseString = PATH_FLEETS + "?";
            Collection<RESTFleet> restFleets = new ArrayList<>();
            Collection<Fleet> fleets;
            if (company != null) {
                fleets = customerController.get(UUIDUtil.toUUID(company)).getFleets();

            } else {
                fleets = controller.getAll();
            }
            for (Fleet f : fleets) {
                restFleets.add(modelToRest(f));
            }
            return new RESTSchema<>(restFleets, page, limit, baseString);
        } catch (Exception e) {
            e.printStackTrace();
            throw new InvalidInputException();
        }

    }

    @RequestMapping(method = RequestMethod.POST)
    public RESTFleet post(@RequestBody RESTFleet restFleet) {
        UUID companyUuid = UUIDUtil.toUUID(restFleet.getCompany());
        try {
            return modelToRest(controller.create(companyUuid, restFleet.getName()));
        } catch (DataAccessException e) {
            throw new InvalidInputException();
            //TODO updateId when there are more exceptions
        }
    }

    @RequestMapping(method = RequestMethod.GET, value = "{id}")
    public RESTFleet getId(@PathVariable("id") String id) {
        UUID uuid = UUIDUtil.toUUID(id);
        try {
            return modelToRest(controller.get(uuid));

        } catch (DataAccessException | NullPointerException e) {
            throw new NotFoundException();
        }
    }

    @RequestMapping(method = RequestMethod.PUT, value = "{id}")
    public RESTFleet updateId(@PathVariable("id") String id, @RequestBody RESTFleet restFleet) {
        UUID uuid = UUIDUtil.toUUID(id);
        UUID companyUuid = UUIDUtil.toUUID(restFleet.getCompany());
        try {
            return modelToRest(controller.update(uuid, companyUuid, restFleet.getName()));
        } catch (DataAccessException e) {
            throw new InvalidInputException();
            //TODO updateId when there are more exceptions
        }
    }

    @RequestMapping(method = RequestMethod.DELETE, value = "{id}")
    public void deleteId(@PathVariable("id") String id) {
        UUID uuid = UUIDUtil.toUUID(id);
        try {
            controller.archive(uuid);
        } catch (DataAccessException e) {
            throw new NotFoundException();
            //TODO updateId when there are more exceptions
        }
    }

    private RESTFleet modelToRest(Fleet fleet) {
        String id = UUIDUtil.UUIDToNumberString(fleet.getUuid());
        String owner = fleet.getOwner() != null ? UUIDUtil.UUIDToNumberString(fleet.getOwner().getUuid()) : null;
        return new RESTFleet(id,
                owner,
                fleet.getName(),
                "",
                "",
                "",
                PATH_FLEETS + "/" + id);
    }
}
