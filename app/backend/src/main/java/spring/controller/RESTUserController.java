package spring.controller;

import org.springframework.web.bind.annotation.*;
import spring.model.RESTUser;

import java.time.LocalDate;
import java.util.*;

@RestController
@RequestMapping("/users")
public class RESTUserController {

    private static Map<Integer, RESTUser> users = new HashMap<>();

    static {
        RESTUser u1 = new RESTUser();
        u1.setId("0");
        u1.setFirstName("Freddy");
        u1.setLastName("Vandeputte");
        u1.setEmail("freddy.vandeputte@gmail.com");
        u1.setUpdatedAt(LocalDate.now());
        u1.setUpdatedAt(LocalDate.now());
        u1.setPassword("wachtwoord123");
        u1.setUrl("TODO");
        users.put(0, u1);

        RESTUser u2 = new RESTUser();
        u2.setId("1");
        u2.setFirstName("Patrick");
        u2.setLastName("Oostvogels");
        u2.setEmail("patrick.oostvogels@gmail.com");
        u2.setUpdatedAt(LocalDate.now());
        u2.setUpdatedAt(LocalDate.now());
        u2.setPassword("azerty");
        u2.setUrl("TODO");
        users.put(1, u2);

        RESTUser u3 = new RESTUser();
        u3.setId("2");
        u3.setFirstName("Sofie");
        u3.setLastName("Maes");
        u3.setEmail("sofietje@gmail.com");
        u3.setUpdatedAt(LocalDate.now());
        u3.setUpdatedAt(LocalDate.now());
        u3.setPassword("secret");
        u3.setUrl("TODO");
        users.put(2, u3);
    }

    private int counter = users.size();

    @RequestMapping(method = RequestMethod.GET)
    public Collection<RESTUser> get() {
        return new ArrayList<>(users.values());
    }

    @RequestMapping(method = RequestMethod.POST)
    public RESTUser post(@RequestBody RESTUser user) {
        int id = counter;
        counter++;

        user.setId(id + "");
        user.setUpdatedAt(LocalDate.now());
        user.setCreatedAt(LocalDate.now());

        users.put(counter, user);
        return user;
    }

    @RequestMapping(value = "{id}", method = RequestMethod.GET)
    public RESTUser getId(@PathVariable("id") String id) {
        return users.get(Integer.parseInt(id));
    }

    @RequestMapping(value = "{id}", method = RequestMethod.PUT)
    public RESTUser putId(@PathVariable("id") String id, @RequestBody RESTUser user) {
        RESTUser old = users.get(Integer.parseInt(id));

        old.setFirstName(user.getFirstName());

        if (user.getLastName() != null) {
            old.setLastName(user.getLastName());
        }
        if (user.getEmail() != null) {
            old.setEmail(user.getEmail());
        }
        if (user.getPassword() != null) {
            old.setPassword(user.getPassword());
        }

        old.setUpdatedAt(LocalDate.now());
        return old;
    }

    @RequestMapping(value = "{id}", method = RequestMethod.DELETE)
    public void deleteId(@PathVariable("id") String id) {
        users.remove(Integer.parseInt(id));
    }

}
