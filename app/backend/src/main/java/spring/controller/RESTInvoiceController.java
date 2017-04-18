package spring.controller;

import controller.ControllerFactory;
import controller.InvoiceController;
import model.billing.Invoice;
import org.springframework.web.bind.annotation.*;
import spring.model.RESTInvoice;
import spring.model.RESTModelFactory;
import spring.model.RESTSchema;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.Collection;

/**
 * Created by Billie Devolder on 17/04/2017.
 */
@RestController
@RequestMapping("/${path.fleets}/{fleetId}/${path.invoices}")
public class RESTInvoiceController extends RESTAbstractController<RESTInvoice,Invoice> {


    public RESTInvoiceController() {
        super(InvoiceController::new, RESTInvoice::new);
    }

    @RequestMapping(method = RequestMethod.GET)
    public RESTSchema<RESTInvoice> getAll(@PathVariable String fleetId,
                                          HttpServletRequest request,
                                          Integer page, Integer limit,
                                          @RequestHeader(value = "Authorization") String token,
                                          @RequestHeader(value = "Function") String authorityFunction) {
        Collection<RESTInvoice> invoices = new ArrayList<>();
        invoices.add(new RESTInvoice());
        return new RESTSchema<>(invoices, page, limit, request);
    }

    @RequestMapping(value = "{invoiceId}", method = RequestMethod.GET)
    public RESTInvoice get(@PathVariable String fleetId,
                           @PathVariable String invoiceId,
                           @RequestHeader(value = "Authorization") String token,
                           @RequestHeader(value = "Function") String authorityFunction) {
        return new RESTInvoice();
    }

    @RequestMapping(value = "${path.invoices_current}", method = RequestMethod.GET)
    public RESTInvoice getCurrent(@PathVariable String fleetId,
                                  @RequestHeader(value = "Authorization") String token,
                                  @RequestHeader(value = "Function") String authorityFunction) {
        return new RESTInvoice();
    }


}
