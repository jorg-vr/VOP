package controller;

import controller.exceptions.UnAuthorizedException;
import dao.interfaces.DAOManager;
import dao.exceptions.DataAccessException;
import dao.interfaces.Filter;
import dao.interfaces.InvoiceDAO;
import model.account.Function;
import model.account.Resource;
import model.billing.Invoice;
import model.identity.Company;
import model.identity.Customer;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

/**
 * Created by jorg on 4/18/17.
 */
public class InvoiceController extends AbstractController<Invoice> {

    private DAOManager manager;

    public InvoiceController(Function function, DAOManager manager) {
        super(manager, manager.getInvoiceDao(), Resource.BILLING, function);
        this.manager = manager;
    }

    public void endStatement(Customer customer){
        Collection<Invoice> invoices = new ArrayList<>();
        int numberOfInvoices = customer.getPaymentPeriod().getTime()/customer.getFacturationPeriod().getTime();


        for(int i = 0; i<numberOfInvoices;i+=customer.getPaymentPeriod().getTime()){
            Invoice invoice = new Invoice();
            invoices.add(invoice);
        }
    }

    public Collection<Invoice> getFiltered(Company company) throws UnAuthorizedException {
        try {
            List<Filter<Invoice>> filters = new ArrayList<>();
            InvoiceDAO invoiceDAO=(InvoiceDAO) getDao();

            if (company != null) {
                filters.add(invoiceDAO.byPayer(company));
            }

            return getAll(filters.toArray(new Filter[filters.size()]));
        } catch (DataAccessException e) {
            // return empty list
            return new ArrayList<>();
        }
    }

    @Override
    public boolean isOwner(Invoice invoice, Function function) {
        return invoice.getPayer().equals(function.getCompany());
    }
}
