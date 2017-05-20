package spring.controller;

import controller.*;
import controller.exceptions.UnAuthorizedException;
import dao.exceptions.ConstraintViolationException;
import dao.exceptions.DataAccessException;
import dao.exceptions.ObjectNotFoundException;
import model.billing.Invoice;
import model.identity.Company;
import model.identity.Customer;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import pdf.InvoicePdf;
import pdf.PdfException;
import spring.exceptions.NotAuthorizedException;
import spring.model.AuthenticationToken;
import spring.model.RESTInvoice;
import spring.model.RESTSchema;
import spring.model.RESTVehicleInvoice;
import util.UUIDUtil;

import javax.servlet.http.HttpServletRequest;
import java.util.Collection;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

import static util.UUIDUtil.toUUID;

/**
 * Requests that are implemented in this class:
 * 1) GET /companies/{id}/invoices
 * 2) GET /companies/{id}/invoices/{id}
 * 3) GET /companies/{id}/invoices/{id}/pdf
 * 4) GET /companies/{id}/invoices/current
 * 5) GET /companies/{id}/invoices/current/pdf
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
                                          @RequestHeader(value = "Function") String function) throws ObjectNotFoundException, DataAccessException, UnAuthorizedException {
        UUID user = new AuthenticationToken(token).getAccountId();
        try (ControllerManager manager = new ControllerManager(user, toUUID(function))) {
            InvoiceController controller = manager.getInvoiceController();
            CompanyController companyController = manager.getCompanyController();

            Company company = companyController.get(UUIDUtil.toUUID(companyId));

            List<RESTInvoice> invoices = controller
                    .getFiltered(company)
                    .stream()
                    .map(RESTInvoice::new)
                    .collect(Collectors.toList());
            return new RESTSchema<>(invoices, page, limit, request, (a, b) -> b.getStartDate().compareTo(a.getStartDate()));
        }
    }

    @RequestMapping(value = "/{id}/${path.vehicle_nvoices}", method = RequestMethod.GET)
    public RESTSchema<RESTVehicleInvoice> getAllVehicleInvoices(@PathVariable String companyId,
                                                                @PathVariable String id,
                                                                HttpServletRequest request,
                                                                Integer page, Integer limit,
                                                                @RequestHeader(value = "Authorization") String token,
                                                                @RequestHeader(value = "Function") String function) throws ObjectNotFoundException {
        UUID user = new AuthenticationToken(token).getAccountId();
        try (ControllerManager manager = new ControllerManager(user, toUUID(function))) {
            InvoiceController controller = manager.getInvoiceController();

            Collection<RESTVehicleInvoice> invoices = controller
                    .get(toUUID(id))
                    .getVehicleInvoices()
                    .stream()
                    .map(RESTVehicleInvoice::new)
                    .collect(Collectors.toList());
            return new RESTSchema<>(invoices, page, limit, request);
        } catch (UnAuthorizedException e) {
            throw new NotAuthorizedException();
        } catch (DataAccessException e) {
            throw new RuntimeException(e);
        }
    }

    @GetMapping("/${path.current}")
    @ResponseBody
    public RESTInvoice getCurrent(@PathVariable("companyId") String companyId, @RequestHeader(value = "Authorization") String token,
                                  @RequestHeader(value = "Function") String function) throws DataAccessException, UnAuthorizedException, ObjectNotFoundException, PdfException {
        UUID uuid = toUUID(companyId);
        UUID user = new AuthenticationToken(token).getAccountId();
        try (ControllerManager manager = new ControllerManager(user, toUUID(function))) {
            Customer customer = manager.getCustomerController().get(uuid);
            return new RESTInvoice(customer.getCurrentStatement());
        }
    }

    @GetMapping("/${path.current}/${path.pdf}")
    @ResponseBody
    public HttpEntity<byte[]> getCurrentPdf(@PathVariable("companyId") String companyId, @RequestHeader(value = "Authorization") String token,
                                            @RequestHeader(value = "Function") String function) throws DataAccessException, UnAuthorizedException, ObjectNotFoundException, PdfException {
        UUID uuid = toUUID(companyId);
        UUID user = new AuthenticationToken(token).getAccountId();
        try (ControllerManager manager = new ControllerManager(user, toUUID(function))) {
            Customer customer = manager.getCustomerController().get(uuid);
            return createEntity(customer.getCurrentStatement());
        }
    }

    @GetMapping("/{id}/${path.pdf}")
    @ResponseBody
    public HttpEntity<byte[]> getPdf(@PathVariable("id") String id, @RequestHeader(value = "Authorization") String token,
                                     @RequestHeader(value = "Function") String function) throws DataAccessException, UnAuthorizedException, ObjectNotFoundException, PdfException {
        UUID uuid = toUUID(id);
        UUID user = new AuthenticationToken(token).getAccountId();
        try (ControllerManager manager = new ControllerManager(user, toUUID(function))) {
            InvoiceController controller = manager.getInvoiceController();
            Invoice invoice = controller.get(uuid);
            return createEntity(invoice);
        }
    }

    private HttpEntity<byte[]> createEntity(Invoice invoice) {
        byte[] documentBody = new InvoicePdf(invoice).getAsByteArray();

        HttpHeaders header = new HttpHeaders();
        header.setContentType(MediaType.APPLICATION_PDF);
        header.set(HttpHeaders.CONTENT_DISPOSITION,
                "attachment; filename=" + "invoice.pdf");
        header.setContentLength(documentBody.length);
        return new HttpEntity<>(documentBody, header);
    }

    @RequestMapping(method = RequestMethod.PUT)
    public void create(@PathVariable String companyId,
                       @RequestHeader(value = "Authorization") String token,
                       @RequestHeader(value = "Function") String function) throws ObjectNotFoundException, ConstraintViolationException, DataAccessException, UnAuthorizedException {
        UUID user = new AuthenticationToken(token).getAccountId();
        try (ControllerManager manager = new ControllerManager(user, toUUID(function))) {
            InvoiceController controller = manager.getInvoiceController();
            CustomerController customerController = manager.getCustomerController();

            Customer company = customerController.get(UUIDUtil.toUUID(companyId));
            controller.endStatement(company);
        }
    }
}
