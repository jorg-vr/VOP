package spring.controller.insurance;

import controller.AbstractController;
import controller.ControllerManager;
import controller.exceptions.UnAuthorizedException;
import controller.insurance.SuretyController;
import dao.exceptions.DataAccessException;
import model.identity.InsuranceCompany;
import model.insurance.Surety;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import spring.controller.RESTAbstractController;
import spring.model.AuthenticationToken;
import spring.model.RESTSchema;
import spring.model.insurance.RESTSurety;

import javax.servlet.http.HttpServletRequest;
import java.util.Collection;
import java.util.UUID;
import java.util.stream.Collectors;

import static util.UUIDUtil.toUUID;

/**
 * Created by Billie Devolder on 18/04/2017.
 */
@RestController
@RequestMapping("${path.sureties}")
public class RESTSuretyController extends RESTAbstractController<RESTSurety, Surety> {

    public RESTSuretyController() {
        super(RESTSurety::new);
    }

    @Override
    public AbstractController<Surety> getController(ControllerManager manager) {
        return manager.getSuretyController();
    }

    @RequestMapping(method = RequestMethod.GET)
    public RESTSchema<RESTSurety> get(HttpServletRequest request,
                                        Integer page, Integer limit,
                                        String insuranceCompany,
                                        @RequestHeader(value = "Authorization") String token,
                                        @RequestHeader(value = "Function") String function) throws UnAuthorizedException, DataAccessException {
        UUID user = new AuthenticationToken(token).getAccountId();
        try (ControllerManager manager = new ControllerManager(user, toUUID(function))) {
            SuretyController controller = manager.getSuretyController();

            InsuranceCompany insuranceCompanyObject = insuranceCompany != null ? new InsuranceCompany(toUUID(insuranceCompany)) : null;

            Collection<RESTSurety> restSureties = controller.getFiltered(insuranceCompanyObject)
                    .stream()
                    .map(RESTSurety::new)
                    .collect(Collectors.toList());
            return new RESTSchema<>(restSureties, page, limit, request);
        }
    }
}
