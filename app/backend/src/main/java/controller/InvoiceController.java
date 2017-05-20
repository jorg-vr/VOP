package controller;

import controller.exceptions.UnAuthorizedException;
import dao.exceptions.ConstraintViolationException;
import dao.exceptions.DataAccessException;
import dao.exceptions.ObjectNotFoundException;
import dao.interfaces.DAOManager;
import dao.interfaces.InvoiceDAO;
import model.account.Function;
import model.account.Resource;
import model.billing.Invoice;
import model.billing.InvoiceType;
import model.billing.VehicleInvoice;
import model.identity.Company;
import model.identity.Customer;
import model.insurance.Contract;
import model.insurance.VehicleInsurance;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collection;

/**
 * Created by jorg on 4/18/17.
 */
public class InvoiceController extends AbstractController<Invoice> {

    private DAOManager manager;
    private InvoiceDAO invoiceDAO;

    public InvoiceController(Function function, DAOManager manager) {
        super(manager, manager.getInvoiceDao(), Resource.BILLING, function);
        this.manager = manager;
        this.invoiceDAO = manager.getInvoiceDao();
    }

    public void endStatement(Customer customer) throws DataAccessException, ConstraintViolationException, ObjectNotFoundException {

        for (int i = 0; i < customer.getPaymentPeriod().getTime(); i += customer.getFacturationPeriod().getTime()) {
            Invoice invoice = new Invoice();

            invoice.setType(InvoiceType.BILLING);
            invoice.setPayer(customer);
            invoice.setStartDate(LocalDateTime.now().plusMonths(i));
            invoice.setEndDate(LocalDateTime.now().
                    plusMonths(i + customer.getFacturationPeriod().getTime()).
                    minusDays(1));
            invoice.setPaid(false);
            invoice.setVehicleInvoices(createVehicleInvoices(customer, customer.getFacturationPeriod().getTime()));
            invoiceDAO.create(invoice);
        }

        Invoice statement = new Invoice();
        statement.setType(InvoiceType.STATEMENT);
        statement.setPaid(false);

        statement.setPayer(customer);
        customer.setCurrentStatement(statement);

        statement.setStartDate(LocalDateTime.now());
        statement.setEndDate(LocalDateTime.now().plusMonths(customer.getPaymentPeriod().getTime()).minusDays(1));
        invoiceDAO.create(statement);
//        manager.getCustomerDAO().update(customer);
    }

    private Collection<VehicleInvoice> createVehicleInvoices(Customer customer, int duration) throws DataAccessException, ObjectNotFoundException {
        Collection<VehicleInsurance> insurances = new ArrayList<>();

        for (Contract contract : customer.getContracts()) {
            for (VehicleInsurance insurance : contract.getVehicleInsurances()) {
                insurances.add(insurance);
            }
        }

        Collection<VehicleInvoice> vehicleInvoices = new ArrayList<>();
        for (VehicleInsurance insurance : insurances) {
            VehicleInvoice vehicleInvoice = new VehicleInvoice();

            vehicleInvoice.setVin(insurance.getVehicle().getVin());
            vehicleInvoice.setLicensePlate(insurance.getVehicle().getLicensePlate());
            vehicleInvoice.setFranchise(insurance.getFranchise());
            vehicleInvoice.setSurety(manager.getSuretyDao().get(insurance.getSurety().getUuid()));
            vehicleInvoice.setTotalCost(insurance.calculateCost() * duration);
            vehicleInvoice.setTotalTax(insurance.calculateTax() * duration);
            vehicleInvoice.setInsuredValue(insurance.getInsuredValue());
            System.out.println(insurance.getUuid());
            vehicleInvoice.setVehicleInsuranceID(insurance.getUuid());
            vehicleInvoices.add(vehicleInvoice);
        }
        return vehicleInvoices;
    }

    public Collection<Invoice> getFiltered(Company company) throws UnAuthorizedException, DataAccessException {
        return getAll(
                invoiceDAO.byPayer(company)
        );
    }

    @Override
    public boolean isOwner(Invoice invoice, Function function) {
        return invoice.getPayer().equals(function.getCompany());
    }
}
