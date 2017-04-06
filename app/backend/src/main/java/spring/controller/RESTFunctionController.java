package spring.controller;

import controller.ControllerFactory;
import model.account.Function;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import spring.model.RESTFunction;
import spring.model.RESTModelFactory;

/**
 * Created by Jarre on 6-4-2017.
 */

@RestController
@RequestMapping("/users/{userId}/functions")
public class RESTFunctionController extends RESTAbstractController<RESTFunction, Function> {

    public RESTFunctionController(ControllerFactory<Function> controllerFactory, RESTModelFactory<RESTFunction, Function> factory) {
        super(controllerFactory, factory);
    }
}
