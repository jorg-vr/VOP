package spring.controller.insurance;


import controller.ControllerFactory;
import controller.UserController;
import controller.exceptions.UnAuthorizedException;
import controller.insurance.ContractController;
import dao.interfaces.DataAccessException;
import model.account.Function;
import model.insurance.Contract;
import model.insurance.Surety;
import model.insurance.SuretyType;
import org.springframework.web.bind.annotation.*;
import spring.controller.RESTAbstractController;
import spring.model.RESTFunction;
import spring.model.RESTModelFactory;
import spring.model.RESTSchema;
import spring.model.insurance.RESTContract;
import util.UUIDUtil;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Optional;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/${path.contracts}")
public class RESTContractController extends RESTAbstractController<RESTContract, Contract> {

    public RESTContractController() {
        super(ContractController::new, RESTContract::new);
    }

    @RequestMapping(method = RequestMethod.GET)
    public RESTSchema<RESTContract> get(HttpServletRequest request,
                                        Integer page, Integer limit,
                                        @RequestHeader(value = "Authorization") String token,
                                        @RequestHeader(value = "Function") String function) throws UnAuthorizedException {
        try (ContractController contractController = new ContractController(verifyToken(token, function))) {
            Collection<RESTContract> restContracts = contractController.getAll()
                    .stream()
                    .map(RESTContract::new)
                    .collect(Collectors.toList());
            return new RESTSchema<>(restContracts, page, limit, request);
        } catch (DataAccessException e) {
            throw new RuntimeException(e);
        }
    }

    @RequestMapping(value = "/${path.contract_types}", method = RequestMethod.GET)
    RESTSchema<String> getContractTypes(HttpServletRequest request,
                                        Integer page, Integer limit,
                                        @RequestHeader(value = "Authorization") String token) {
        Collection<String> result = new ArrayList<>();
        for (SuretyType suretyType : SuretyType.values()) {
            result.add(suretyType.toString());
        }
        return new RESTSchema<>(result, page, limit, request);
    }
}
