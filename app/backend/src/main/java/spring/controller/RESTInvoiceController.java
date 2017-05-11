package spring.controller;

import controller.*;
import controller.exceptions.UnAuthorizedException;
import dao.exceptions.DataAccessException;
import dao.exceptions.ObjectNotFoundException;
import model.billing.Invoice;
import model.identity.Company;
import org.springframework.web.bind.annotation.*;
import spring.exceptions.NotAuthorizedException;
import spring.model.AuthenticationToken;
import spring.model.RESTInvoice;
import spring.model.RESTSchema;
import spring.model.insurance.RESTContract;
import util.UUIDUtil;

import javax.servlet.http.HttpServletRequest;
import java.util.Collection;
import java.util.UUID;
import java.util.stream.Collectors;

import static util.UUIDUtil.toUUID;

/**
 * Requests that are implemented in this class:
 * 1) GET /companies/{id}/invoices
 * 2) GET /companies/{id}/invoices/{id}
 */
@RestController
@RequestMapping("/${path.companies}/{companyId}/${path.invoices}")
public class RESTInvoiceController extends RESTAbstractController<RESTInvoice, Invoice> {


    public RESTInvoiceController() {
        super(RESTInvoice::new);
    }

    @Override
    public AbstractController<Invoice> getController(ControllerManager manager) {
        return manager.getInvoiceController();
    }

    @RequestMapping(method = RequestMethod.GET)
    public RESTSchema<RESTInvoice> getAll(@PathVariable String companyId,
                                          HttpServletRequest request,
                                          Integer page, Integer limit,
                                          @RequestHeader(value = "Authorization") String token,
                                          @RequestHeader(value = "Function") String function) throws ObjectNotFoundException {
        UUID user = new AuthenticationToken(token).getAccountId();
        try (ControllerManager manager = new ControllerManager(user, toUUID(function))) {
            InvoiceController controller = manager.getInvoiceController();
            CompanyController companyController = manager.getCompanyController();

            Company company = companyController.get(UUIDUtil.toUUID(companyId));

            Collection<RESTInvoice> invoices = controller
                    .getFiltered(company)
                    .stream()
                    .map(RESTInvoice::new)
                    .collect(Collectors.toList());
            return new RESTSchema<>(invoices, page, limit, request);
        } catch (UnAuthorizedException e) {
            throw new NotAuthorizedException();
        } catch (DataAccessException e) {
            throw new RuntimeException(e);
        }


    }

    @RequestMapping(value = "/{invoiceId}/${path.contracts}", method = RequestMethod.GET)
    public RESTSchema<RESTContract> getAllContracts(@PathVariable String companyId,
                                                    @PathVariable String invoiceId,
                                                    HttpServletRequest request,
                                                    Integer page, Integer limit,
                                                    @RequestHeader(value = "Authorization") String token,
                                                    @RequestHeader(value = "Function") String function) throws ObjectNotFoundException {
        UUID user = new AuthenticationToken(token).getAccountId();
        try (ControllerManager manager = new ControllerManager(user, toUUID(function))) {
            InvoiceController controller = manager.getInvoiceController();

            Collection<RESTContract> contracts = controller.get(UUIDUtil.toUUID(invoiceId))
                    .getContracts()
                    .stream()
                    .map(RESTContract::new)
                    .collect(Collectors.toList());
            return new RESTSchema<>(contracts, page, limit, request);
        } catch (UnAuthorizedException e) {
            throw new NotAuthorizedException();
        } catch (DataAccessException e) {
            throw new RuntimeException(e);
        }
    }
}
