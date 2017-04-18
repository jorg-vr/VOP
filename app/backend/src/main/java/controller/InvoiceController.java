package controller;

import dao.database.ProductionProvider;
import dao.interfaces.DAO;
import dao.interfaces.InvoiceDAO;
import model.account.Function;
import model.account.Resource;
import model.billing.Invoice;

/**
 * Created by jorg on 4/18/17.
 */
public class InvoiceController extends AbstractController<Invoice> {

    public InvoiceController(Function function) {
        super(ProductionProvider.getInstance().getInvoiceDao(), Resource.BILLING, function);
    }

    @Override
    public boolean isOwner(Invoice invoice, Function function) {
        return invoice.getPayer().equals(function.getCompany()) ||
                invoice.getBeneficiary().equals(function.getCompany());
    }
}
