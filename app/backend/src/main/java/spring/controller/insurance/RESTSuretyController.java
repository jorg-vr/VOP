package spring.controller.insurance;

import controller.ControllerFactory;
import controller.exceptions.UnAuthorizedException;
import controller.insurance.ContractController;
import controller.insurance.SuretyController;
import dao.interfaces.DataAccessException;
import model.insurance.Surety;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import spring.controller.RESTAbstractController;
import spring.exceptions.ServerErrorException;
import spring.model.RESTModelFactory;
import spring.model.RESTSchema;
import spring.model.insurance.RESTContract;
import spring.model.insurance.RESTSurety;

import javax.servlet.http.HttpServletRequest;
import java.util.Collection;
import java.util.stream.Collectors;

/**
 * Created by Billie Devolder on 18/04/2017.
 */
@RestController
@RequestMapping("${path.sureties}")
public class RESTSuretyController extends RESTAbstractController<RESTSurety, Surety> {

    public RESTSuretyController() {
        super(SuretyController::new, RESTSurety::new);
    }

    @RequestMapping(method = RequestMethod.GET)
    public RESTSchema<RESTSurety> get(HttpServletRequest request,
                                        Integer page, Integer limit,
                                        @RequestHeader(value = "Authorization") String token,
                                        @RequestHeader(value = "Function") String function) throws UnAuthorizedException {
        try (SuretyController controller = new SuretyController(verifyToken(token, function))) {
            Collection<RESTSurety> restSureties = controller.getAll()
                    .stream()
                    .map(RESTSurety::new)
                    .collect(Collectors.toList());
            return new RESTSchema<>(restSureties, page, limit, request);
        } catch (DataAccessException e) {
            throw new ServerErrorException("sureties could not be retrieved. This is a server error");
        }
    }
}
