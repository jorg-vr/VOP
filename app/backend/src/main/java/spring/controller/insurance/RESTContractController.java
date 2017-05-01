package spring.controller.insurance;


import controller.AbstractController;
import controller.ControllerManager;
import controller.exceptions.UnAuthorizedException;
import controller.insurance.ContractController;
import dao.exceptions.DataAccessException;
import model.identity.Customer;
import model.insurance.Contract;
import model.insurance.SuretyType;
import org.springframework.web.bind.annotation.*;
import spring.controller.RESTAbstractController;
import spring.exceptions.ServerErrorException;
import spring.model.AuthenticationToken;
import spring.model.RESTSchema;
import spring.model.insurance.RESTContract;

import javax.servlet.http.HttpServletRequest;
import java.util.*;
import java.util.stream.Collectors;

import static util.UUIDUtil.toUUID;

@RestController
@RequestMapping("/${path.contracts}")
public class RESTContractController extends RESTAbstractController<RESTContract, Contract> {

    public RESTContractController() {
        super(RESTContract::new);
    }

    @Override
    public AbstractController<Contract> getController(ControllerManager manager) {
        return manager.getContractController();
    }

    @RequestMapping(method = RequestMethod.GET)
    public RESTSchema<RESTContract> get(HttpServletRequest request,
                                        Integer page, Integer limit,
                                        String company,
                                        @RequestHeader(value = "Authorization") String token,
                                        @RequestHeader(value = "Function") String function) throws UnAuthorizedException {
        UUID user = new AuthenticationToken(token).getAccountId();
        try (ControllerManager manager = new ControllerManager(user, toUUID(function))) {
            ContractController controller = manager.getContractController();

            Customer customer = company != null ? new Customer(toUUID(company)) : null;

            Collection<RESTContract> restContracts = controller.getFiltered(customer)
                    .stream()
                    .map(RESTContract::new)
                    .collect(Collectors.toList());
            return new RESTSchema<>(restContracts, page, limit, request);
        } catch (DataAccessException e) {
            throw new ServerErrorException("contracts could not be retrieved. This is a server error");
        }
    }

    @RequestMapping(value = "/${path.contract_types}", method = RequestMethod.GET)
    RESTSchema<String> getContractTypes(HttpServletRequest request,
                                        Integer page, Integer limit,
                                        @RequestHeader(value = "Authorization") String token,
                                        @RequestHeader(value = "Function") String function) throws UnAuthorizedException {
        // Check authentication and authorization
        UUID user = new AuthenticationToken(token).getAccountId();
        try (ControllerManager manager = new ControllerManager(user, toUUID(function))) {
        }

        Collection<String> result = new ArrayList<>();
        for (SuretyType suretyType : SuretyType.values()) {
            result.add(suretyType.toString());
        }
        return new RESTSchema<>(result, page, limit, request);
    }
}
