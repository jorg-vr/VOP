package controller;

import controller.exceptions.UnAuthorizedException;
import dao.exceptions.ConstraintViolationException;
import dao.interfaces.DAOManager;
import dao.exceptions.DataAccessException;
import dao.interfaces.Filter;
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

    public void endStatement(Customer customer) throws DataAccessException, ConstraintViolationException {

        for(int i = 0; i<customer.getPaymentPeriod().getTime();i+=customer.getFacturationPeriod().getTime()){
            Invoice invoice = new Invoice();

            invoice.setType(InvoiceType.BILLING);
            invoice.setPayer(customer);
            invoice.setStartDate(LocalDateTime.now().plusMonths(i));
            invoice.setEndDate(LocalDateTime.now().
                    plusMonths(i+customer.getFacturationPeriod().getTime()).
                    minusDays(1));
            invoice.setContracts(customer.getContracts());
            invoice.setPaid(false);
            invoice.setVehicleInvoices(createVehicleInvoices(customer));
            getDao().create(invoice);
        }

        Invoice statement = new Invoice();
        statement.setType(InvoiceType.STATEMENT);
        statement.setPaid(false);

        statement.setPayer(customer);
        customer.setCurrentStatement(statement);

        statement.setContracts(customer.getContracts());
        statement.setStartDate(LocalDateTime.now());
        statement.setEndDate(LocalDateTime.now().plusMonths(customer.getPaymentPeriod().getTime()).minusDays(1));
        getDao().create(statement);
    }

    private Collection<VehicleInvoice> createVehicleInvoices(Customer customer){
        Collection<VehicleInsurance> insurances = new ArrayList<>();

        for(Contract contract: customer.getContracts()){
            for(VehicleInsurance insurance: contract.getVehicleInsurances()){
                insurances.add(insurance);
            }
        }

        Collection<VehicleInvoice> vehicleInvoices = new ArrayList<>();
        for(VehicleInsurance insurance: insurances){
            VehicleInvoice vehicleInvoice = new VehicleInvoice();

            vehicleInvoice.setVin(insurance.getVehicle().getVin());
            vehicleInvoice.setLicensePlate(insurance.getVehicle().getLicensePlate());
            vehicleInvoice.setFranchise(insurance.getFranchise());
            vehicleInvoice.setVehicleInsurance(insurance);
            vehicleInvoice.setTotalCost(insurance.calculateCost());
            vehicleInvoice.setTotalTax(insurance.calculateTax());
            vehicleInvoice.setInsuredValue(insurance.getInsuredValue());

            vehicleInvoices.add(vehicleInvoice);
        }
        return vehicleInvoices;
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
