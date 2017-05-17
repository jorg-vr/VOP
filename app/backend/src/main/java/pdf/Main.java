package pdf;

import model.fleet.Vehicle;
import model.fleet.VehicleType;
import model.identity.Address;
import model.identity.Customer;
import model.identity.InsuranceCompany;
import model.identity.Periodicity;
import model.insurance.Contract;
import model.insurance.VehicleInsurance;

import java.io.IOException;
import java.time.LocalDate;

/**
 * Created by Billie Devolder on 15/05/2017.
 */
public class Main {

    public static void main(String[] args) {
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
        }
        /*
        Invoice invoice = new Invoice();
        invoice.setStartDate(LocalDateTime.now());
        invoice.setEndDate(LocalDateTime.now().plusYears(1));
        Customer customer = new Customer();
        customer.setName("Freddy en dochters");
        customer.setPaymentPeriod(Periodicity.MONTHLY);
        customer.setAddress(new Address("street", "12", "town", "postalCode", "country"));
        invoice.setPayer(customer);
        Collection<VehicleInvoice> vehicleInvoices = new ArrayList<>();
        for (int i = 0; i < 1000; i++) {
            VehicleInvoice vehicleInvoice = new VehicleInvoice();
            vehicleInvoice.setTotalCost(i * 51);
            vehicleInvoice.setTotalTax(i * 19);
            VehicleInsurance insurance = new VehicleInsurance();
            FlatSurety surety = new FlatSurety();
            surety.setSuretyType(SuretyType.values()[i % SuretyType.values().length]);
            insurance.setSurety(surety);
            vehicleInvoice.setVehicleInsurance(insurance);
            vehicleInvoice.setLicensePlate("ABC-" + i / 4);
            vehicleInvoices.add(vehicleInvoice);
        }
        invoice.setVehicleInvoices(vehicleInvoices);

        try {
            InvoicePdf invoicePdf = new InvoicePdf(invoice);
            invoicePdf.writeToFile("invoice.pdf");
        } catch (IOException e) {
            e.printStackTrace();
        }
        */
    }
}
