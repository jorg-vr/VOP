package controller;

import controller.exceptions.UnAuthorizedException;
import dao.database.ProductionProvider;
import dao.interfaces.DAO;
import dao.interfaces.DataAccessException;
import dao.interfaces.Filter;
import dao.interfaces.InvoiceDAO;
import model.account.Function;
import model.account.Resource;
import model.account.User;
import model.billing.Invoice;
import model.identity.Company;
import spring.exceptions.NotImplementedException;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

/**
 * Created by jorg on 4/18/17.
 */
public class InvoiceController extends AbstractController<Invoice> {


    public InvoiceController(Function function) {
        super(ProductionProvider.getInstance().getInvoiceDao(), Resource.BILLING, function);
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
        return invoice.getPayer().equals(function.getCompany()) ||
                invoice.getBeneficiary().equals(function.getCompany());
    }
}