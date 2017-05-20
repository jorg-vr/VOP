package pdf;

import dao.database.ProductionProvider;
import dao.exceptions.DataAccessException;
import dao.interfaces.DAOManager;
import dao.interfaces.InvoiceDAO;
import model.billing.Invoice;

import java.io.IOException;

/**
 * Created by Billie Devolder on 15/05/2017.
 */
public class Main {

    public static void main(String[] args) throws DataAccessException {
        /*
        VehicleInsurance vehicleInsurance = new VehicleInsurance();
        Contract contract = new Contract();
        InsuranceCompany insuranceCompany = new InsuranceCompany();
        insuranceCompany.setName("Freddy en dochters");
        insuranceCompany.setPaymentPeriod(Periodicity.MONTHLY);
        insuranceCompany.setAddress(new Address("street", "12", "town", "postalCode", "country"));
        Customer customer = new Customer();
        customer.setAddress(new Address("street", "12", "town", "postalCode", "country"));
        contract.setCompany(insuranceCompany);
        contract.setCustomer(customer);
        vehicleInsurance.setContract(contract);

        Vehicle vehicle = new Vehicle();
        vehicle.setVin("1233");
        vehicle.setBrand("a");
        vehicle.setLicensePlate("b");
        VehicleType type = new VehicleType();
        type.setType("Volkswagen");
        vehicle.setType(type);
        vehicle.setModel("aaa");
        vehicle.setYear(LocalDate.now());
        vehicleInsurance.setVehicle(vehicle);


        GreenCard greenCard = new GreenCard(vehicleInsurance);
        try {
            greenCard.writeToFile("green.pdf");
        } catch (IOException e) {
            e.printStackTrace();
        }*/
        ProductionProvider.initializeProvider("localtest");
        try (DAOManager manager = ProductionProvider.getInstance().getDaoManager()) {
            InvoiceDAO dao = manager.getInvoiceDao();
            for (Invoice invoice : dao.listFiltered()) {
                try {
                    InvoicePdf invoicePdf = new InvoicePdf(invoice);
                    invoicePdf.writeToFile("invoice.pdf");
                } catch (IOException e) {
                    e.printStackTrace();
                }
                break;
            }
            System.out.println("DONE");
        }

    }
}
