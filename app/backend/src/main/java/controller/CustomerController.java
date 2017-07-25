package controller;

import controller.exceptions.UnAuthorizedException;
import controller.insurance.CommissionContainerController;
import dao.exceptions.ConstraintViolationException;
import dao.exceptions.DataAccessException;
import dao.interfaces.DAOManager;
import model.account.Function;
import model.account.Resource;
import model.billing.Invoice;
import model.billing.InvoiceType;
import model.identity.Customer;

import java.time.LocalDateTime;

/**
 * For more information of what this class does, see AbstractController
 */
public class CustomerController extends CommissionContainerController<Customer> {

    private final DAOManager manager;

    public CustomerController(Function function, DAOManager manager) {
        super(manager, manager.getCustomerDAO(), Resource.COMPANY,function);
        this.manager = manager;
    }

    @Override
    public boolean isOwner(Customer customer, Function function) {
        return function.getCompany().equals(customer);
    }

    @Override
    public Customer create(Customer newCustomer) throws DataAccessException, UnAuthorizedException, ConstraintViolationException {
        Customer customer = newCustomer;
        customer = super.create(customer);// create the customer

        //Create the the first active statement, starting at the moment the customer is created
        Invoice invoice = new Invoice();
        invoice.setPayer(customer);
        invoice.setType(InvoiceType.STATEMENT);
        invoice.setStartDate(LocalDateTime.now());
        //TODO: make starting statement end at the end of  month instead of potentially the middle of a month (ex. statement of 3 months starts on juli 15th -> ends on september 30th instead of oktober 15th)
        invoice.setEndDate(LocalDateTime.now().plusMonths(customer.getStatementPeriod().getTime()));
        invoice.setPaid(false);
        manager.getInvoiceDao().create(invoice);

        customer.setCurrentStatement(invoice);
        manager.getCustomerDAO().update(customer);
        return customer;
    }
}
