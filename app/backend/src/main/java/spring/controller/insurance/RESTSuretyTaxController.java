package spring.controller.insurance;

import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

/**
 * Created by Billie Devolder on 15/04/2017.
 */
@RestController
@RequestMapping("${path.vehicles}/${path.types}/{typeName}/${path.taxes}/{contractType}")
public class RESTSuretyTaxController {

    @RequestMapping(method = RequestMethod.GET)
    public double get(@PathVariable String typeName, @PathVariable String contractType) {
        return 0.00;
    }


}
