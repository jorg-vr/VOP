package spring.controller;

import controller.FunctionController;
import dao.interfaces.DataAccessException;
import model.account.Function;
import org.springframework.web.bind.annotation.*;
import spring.Exceptions.NotFoundException;
import spring.Exceptions.NotImplementedException;
import spring.model.RESTRole;

import java.time.LocalDate;
import java.util.*;

import static spring.controller.UUIDUtil.UUIDToNumberString;

@RestController
@RequestMapping("/roles")
public class RESTRoleController {

    private FunctionController controller = new FunctionController();

    @RequestMapping(method = RequestMethod.GET)
    public Collection<RESTRole> get() {
        Collection<RESTRole> roles = new ArrayList<>();
        try {
            Collection<Function> functions = controller.getAll();
            for (Function function : functions) {
                RESTRole restRole = modelToRest(function);
                roles.add(restRole);
            }
        } catch (DataAccessException e) {
            e.printStackTrace();
        }
        return roles;
    }

    @RequestMapping(method = RequestMethod.POST)
    public RESTRole post(@RequestBody RESTRole role) {
        return null;
    }

    @RequestMapping(value = "{id}", method = RequestMethod.GET)
    public RESTRole getId(@PathVariable("id") String id) {
        UUID uuid = UUIDUtil.toUUID(id);
        try {
            Function function = controller.get(uuid);
            return modelToRest(function);
        } catch (DataAccessException e) {
            throw new NotFoundException();
        }
    }

    @RequestMapping(value = "{id}", method = RequestMethod.PUT)
    public RESTRole putId(@PathVariable("id") String id, @RequestBody RESTRole role) {
        throw new NotImplementedException();
    }

    @RequestMapping(value = "{id}", method = RequestMethod.DELETE)
    public void deleteId(@PathVariable("id") String id) {
        throw new NotImplementedException();
    }

    private RESTRole modelToRest(Function function) {
        RESTRole role = new RESTRole();
        role.setFunction(function.getRole().getName());
        role.setId(UUIDToNumberString(function.getUuid()));
        role.setUserId(UUIDToNumberString(function.getAccount().getUuid()));
        // role.setCompanyId(UUIDToNumberString(function.getCompany().getUuid()));
        role.setStartDate(function.getStartDate());
        role.setEndDate(function.getEndDate());
        //role.setStartDate(); TODO milestone?
        //role.setEndDate();
        return role;
    }

}
