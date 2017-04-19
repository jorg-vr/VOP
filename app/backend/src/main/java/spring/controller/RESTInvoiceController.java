package spring.controller;

import controller.ControllerFactory;
import controller.CustomerController;
import controller.InvoiceController;
import controller.exceptions.UnAuthorizedException;
import dao.interfaces.DataAccessException;
import dao.interfaces.Filter;
import dao.interfaces.InvoiceDAO;
import model.billing.Invoice;
import model.identity.Company;
import model.insurance.Contract;
import org.springframework.web.bind.annotation.*;
import spring.exceptions.NotAuthorizedException;
import spring.model.RESTInvoice;
import spring.model.RESTModelFactory;
import spring.model.RESTSchema;
import spring.model.insurance.RESTContract;
import util.UUIDUtil;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

/**
 * Created by Billie Devolder on 17/04/2017.
 */
@RestController
@RequestMapping("/${path.companies}/{companyId}/${path.invoices}")
public class RESTInvoiceController extends RESTAbstractController<RESTInvoice,Invoice> {


    public RESTInvoiceController() {
        super(InvoiceController::new, RESTInvoice::new);
    }

    @RequestMapping(method = RequestMethod.GET)
    public RESTSchema<RESTInvoice> getAll(@PathVariable String companyId,
                                          HttpServletRequest request,
                                          Integer page, Integer limit,
                                          @RequestHeader(value = "Authorization") String token,
                                          @RequestHeader(value = "Function") String authorityFunction) {
        try(InvoiceController controller=new InvoiceController(verifyToken(token,authorityFunction));
            CustomerController customerController=new CustomerController(verifyToken(token,authorityFunction))) {

            Collection<RESTInvoice> invoices=new ArrayList<>();
            Company company=customerController.get(UUIDUtil.toUUID(companyId));//TODO companycontroller
            for(Invoice invoice:controller.getFiltered(company)){
                invoices.add(new RESTInvoice(invoice));
            }
            return new RESTSchema<>(invoices, page, limit, request);
        } catch (UnAuthorizedException e) {
            throw new NotAuthorizedException();
        } catch (DataAccessException e) {
            throw  new RuntimeException(e);
        }


    }

    @RequestMapping(value = "/{invoiceId}/${path.refresh}",method = RequestMethod.GET)
    public RESTSchema<RESTContract> getAllContracts(@PathVariable String companyId,
                                                    @PathVariable String invoiceId,
                                                    HttpServletRequest request,
                                                    Integer page, Integer limit,
                                                    @RequestHeader(value = "Authorization") String token,
                                                    @RequestHeader(value = "Function") String authorityFunction) {
        try(InvoiceController controller=new InvoiceController(verifyToken(token,authorityFunction))) {
            Collection<RESTContract> contracts=new ArrayList<>();
            for (Contract contract: controller.get(UUIDUtil.toUUID(invoiceId)).getContracts()){
                contracts.add(new RESTContract(contract));
            }
            return new RESTSchema<>(contracts, page, limit, request);
        } catch (UnAuthorizedException e) {
            throw new NotAuthorizedException();
        } catch (DataAccessException e) {
            throw  new RuntimeException(e);
        }


    }



}
