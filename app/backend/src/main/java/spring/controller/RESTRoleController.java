package spring.controller;

import org.springframework.web.bind.annotation.*;
import spring.model.RESTRole;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/roles")
public class RESTRoleController {

    private static Map<Integer, RESTRole> roles = new HashMap<>();
    static {
        RESTRole u1 = new RESTRole();
        u1.setId("0");
        u1.setCompanyId("0");
        u1.setUserId("0");
        u1.setFunction("CUSTOMER");
        roles.put(0, u1);

        RESTRole u2 = new RESTRole();
        u2.setId("1");
        u2.setCompanyId("1");
        u2.setUserId("1");
        u2.setFunction("CUSTOMER");
        roles.put(1, u2);

        RESTRole u3 = new RESTRole();
        u3.setId("2");
        u2.setCompanyId("2");
        u2.setUserId("2");
        u2.setFunction("CUSTOMER");
        roles.put(2, u3);
    }

    private int counter = roles.size();

    @RequestMapping(method = RequestMethod.GET)
    public Collection<RESTRole> get() {
        return new ArrayList<>(roles.values());
    }

    @RequestMapping(method = RequestMethod.POST)
    public RESTRole post(@RequestBody RESTRole role) {
        int id = counter;
        counter++;

        role.setId(id + "");
        role.setUpdatedAt(LocalDate.now());
        role.setCreatedAt(LocalDate.now());

        roles.put(counter, role);
        return role;
    }

    @RequestMapping(value = "{id}", method = RequestMethod.GET)
    public RESTRole getId(@PathVariable("id") String id) {
        return roles.get(Integer.parseInt(id));
    }

    @RequestMapping(value = "{id}", method = RequestMethod.PUT)
    public RESTRole putId(@PathVariable("id") String id, @RequestBody RESTRole role) {
        RESTRole old = roles.get(Integer.parseInt(id));

        // TODO should these field even be able to change?
        if (role.getCompanyId() != null) {
            old.setCompanyId(role.getCompanyId());
        }
        if (role.getUserId() != null) {
            old.setUserId(role.getUserId());
        }
        if (role.getEndDate() != null) {
            old.setEndDate(role.getEndDate());
        }
        if (role.getStartDate() != null) {
            old.setStartDate(role.getStartDate());
        }
        if (role.getFunction() != null) {
            old.setFunction(role.getFunction());
        }
        old.setUpdatedAt(LocalDate.now());
        return old;
    }

    @RequestMapping(value = "{id}", method = RequestMethod.DELETE)
    public void deleteId(@PathVariable("id") String id) {
        roles.remove(Integer.parseInt(id));
    }

}
