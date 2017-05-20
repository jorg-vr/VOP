package spring.controller.insurance;


import controller.AbstractController;
import controller.ControllerManager;
import controller.exceptions.UnAuthorizedException;
import controller.insurance.ContractController;
import dao.exceptions.DataAccessException;
import model.identity.Customer;
import model.identity.InsuranceCompany;
import model.insurance.Contract;
import model.insurance.SuretyType;
import org.springframework.web.bind.annotation.*;
import spring.controller.RESTAbstractController;
import spring.model.AuthenticationToken;
import spring.model.RESTSchema;
import spring.model.insurance.RESTContract;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.Collection;
import java.util.UUID;
import java.util.stream.Collectors;

import static util.UUIDUtil.toUUID;

/**
 * Requests that are implemented in this class:
 * 1) GET /contracts/types
 * 2) GET /contracts
 * 3) GET /contracts/{id}
 * 4) POST /contracts
 * 5) PUT /contracts/{id}
 * 6) DELETE /contracts/{id}
 */
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
                                        @RequestParam(required = false) String customer,
                                        @RequestParam(required = false) String insuranceCompany,
                                        @RequestParam(required = false) String startsBefore,
                                        @RequestParam(required = false) String startsOn,
                                        @RequestParam(required = false) String startsAfter,
                                        @RequestParam(required = false) String endsBefore,
                                        @RequestParam(required = false) String endsOn,
                                        @RequestParam(required = false) String endsAfter,
                                        @RequestHeader(value = "Authorization") String token,
                                        @RequestHeader(value = "Function") String function) throws UnAuthorizedException, DataAccessException {
        UUID user = new AuthenticationToken(token).getAccountId();
        try (ControllerManager manager = new ControllerManager(user, toUUID(function))) {
            ContractController controller = manager.getContractController();

            Customer customerObject = customer != null ? new Customer(toUUID(customer)) : null;
            InsuranceCompany insuranceCompanyObject = insuranceCompany != null ? new InsuranceCompany(toUUID(insuranceCompany)) : null;

            Collection<RESTContract> restContracts = controller.getFiltered(customerObject, insuranceCompanyObject, startsBefore, startsOn, startsAfter, endsBefore, endsOn, endsAfter)
                    .stream()
                    .map(RESTContract::new)
                    .collect(Collectors.toList());
            return new RESTSchema<>(restContracts, page, limit, request);
        }
    }

    @RequestMapping(value = "/${path.contract_types}", method = RequestMethod.GET)
    RESTSchema<String> getContractTypes(HttpServletRequest request,
                                        Integer page, Integer limit,
                                        @RequestHeader(value = "Authorization") String token,
                                        @RequestHeader(value = "Function") String function) throws UnAuthorizedException, DataAccessException {
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
