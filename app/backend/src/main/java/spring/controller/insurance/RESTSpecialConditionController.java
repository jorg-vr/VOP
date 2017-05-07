package spring.controller.insurance;

import controller.AbstractController;
import controller.ControllerManager;
import controller.exceptions.UnAuthorizedException;
import controller.insurance.SpecialConditionController;
import dao.exceptions.DataAccessException;
import model.insurance.SpecialCondition;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import spring.controller.RESTAbstractController;
import spring.model.AuthenticationToken;
import spring.model.RESTSchema;
import spring.model.RESTSpecialCondition;

import javax.servlet.http.HttpServletRequest;
import java.util.Collection;
import java.util.UUID;
import java.util.stream.Collectors;

import static util.UUIDUtil.toUUID;

/**
 * Created by Billie Devolder on 7/05/2017.
 */
@RestController
@RequestMapping("/${path.special_conditions}")
public class RESTSpecialConditionController extends RESTAbstractController<RESTSpecialCondition, SpecialCondition> {

    public RESTSpecialConditionController() {
        super(RESTSpecialCondition::new);
    }

    @Override
    public AbstractController<SpecialCondition> getController(ControllerManager manager) {
        return manager.getSpecialConditionController();
    }

    @RequestMapping(method = RequestMethod.GET)
    public RESTSchema<RESTSpecialCondition> get(HttpServletRequest request,
                                                Integer page, Integer limit,
                                                @RequestHeader(value = "Authorization") String token,
                                                @RequestHeader(value = "Function") String function) throws UnAuthorizedException, DataAccessException {
        UUID user = new AuthenticationToken(token).getAccountId();
        try (ControllerManager manager = new ControllerManager(user, toUUID(function))) {
            SpecialConditionController controller = manager.getSpecialConditionController();
            Collection<RESTSpecialCondition> restModels = controller.getAll()
                    .stream()
                    .map(RESTSpecialCondition::new)
                    .collect(Collectors.toList());
            return new RESTSchema<>(restModels, page, limit, request);
        }
    }
}
