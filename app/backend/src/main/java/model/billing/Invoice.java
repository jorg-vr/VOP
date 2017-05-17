package model.billing;

import model.history.EditableObject;
import model.history.LogResource;
import model.identity.Company;
import model.insurance.Contract;
import model.insurance.VehicleInsurance;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.UUID;

/**
 * Invoice class that contains the costs for a given billing period.
 * The costs collection contains all the individual costs for the given billing period and can be used to calculate the total amount that has to be paid.
 * Costs are separate objects so that it is easy to categorize the types of costs according to personal preference when making a pdf representation
 * of the invoice for example.
 */
public class Invoice implements EditableObject, java.io.Serializable {

    private UUID uuid;

    /**
     * Company that has to pay the invoice/is the receiver of the invoice
     */
    private Company payer;


    /**
     * Type of Invoice. Can be either a billing (monthly payments),
     * a statement at the end of a billing period or a correction invoice,
     * or a correction
     */
    private InvoiceType type;

    /**
     * indicates whether a bail has been paid for already or is still awaiting payment.
     */
    private boolean paid;

    /**
     * start-date of the billing period the invoice applies to
     */
    private LocalDateTime startDate;

    /**
     * end-date of the billing period the invoice applies to
     */
    private LocalDateTime endDate;

    /**
     * collection of the insurances that are the subject of this invoice
     */
    private Collection<VehicleInvoice> vehicleInvoices;


    /**
     * constructor
     */
    public Invoice() {
        this.vehicleInvoices = new ArrayList<>();
    }

    /**
     * Constructor
     * @param payer the payer
     * @param type the type of invoice
     * @param paid paid (probably false)
     * @param startDate the start date
     * @param endDate the end date
     */
    public Invoice(Company payer, InvoiceType type, boolean paid, LocalDateTime startDate, LocalDateTime endDate) {
        this.payer = payer;
        this.type = type;
        this.paid = paid;
        this.startDate = startDate;
        this.endDate = endDate;
        this.vehicleInvoices = new ArrayList<>();
    }

    /**
     * Calculate the total cost of this contract.
     * @return sum of costs of insurances. expressed in cents
     */
    public int calculateCost() {
        int cost = 0;
        for(VehicleInvoice vehicleInvoice : vehicleInvoices){
            cost+=vehicleInvoice.getTotalCost();
        }
        return cost;
    }

    /**
     * Calculate the total tax of this contract.
     * @return sum of taxes of insurances. expressed in cents
     */
    public int calculateTax() {
        int tax = 0;
        for(VehicleInvoice vehicleInvoice : vehicleInvoices){
            tax+=vehicleInvoice.getTotalTax();
        }
        return tax;
    }

    public VehicleInvoice getVehicleInvoice(VehicleInsurance insurance){
        for(VehicleInvoice vehicleInvoice: getVehicleInvoices()){
            if(vehicleInvoice.getVehicleInsurance().getUuid().equals(insurance.getUuid())){
                return vehicleInvoice;
            }
        }
        return null;
    }

    /**
     * Gets the id
     * @return the id
     */
    @Override
    public UUID getUuid() {
        return uuid;
    }

    /**
     * Sets the id
     * @param uuid the id
     */
    public void setUuid(UUID uuid) {
        this.uuid = uuid;
    }

    /**
     * Gets the payer
     * @return the payer
     */
    public Company getPayer() {
        return payer;
    }

    /**
     * Sets the payer
     * @param payer the payer
     */
    public void setPayer(Company payer) {
        this.payer = payer;
    }

    /**
     * Gets paid status
     * @return true if paid, false if not
     */
    public boolean isPaid() {
        return paid;
    }

    /**
     * Sets the paid status
     * @param paid the status
     */
    public void setPaid(boolean paid) {
        this.paid = paid;
    }

    /**
     * Gets the start date
     * @return the start date
     */
    public LocalDateTime getStartDate() {
        return startDate;
    }

    /**
     * Sets the start date
     * @param startDate the start date
     */
    public void setStartDate(LocalDateTime startDate) {
        this.startDate = startDate;
    }

    /**
     * Gets the end date
     * @return the end date
     */
    public LocalDateTime getEndDate() {
        return endDate;
    }

    /**
     * Sets the end date
     * @param endDate the end date
     */
    public void setEndDate(LocalDateTime endDate) {
        this.endDate = endDate;
    }

    /**
     * Gets the type
     * @return the type
     */
    public InvoiceType getType() {
        return type;
    }

    /**
     * Sets the type
     * @param type the type
     */
    public void setType(InvoiceType type) {
        this.type = type;
    }

    /**
     * Gets the vehicleInvoices
     * @return the vehicle invoices
     */
    public Collection<VehicleInvoice> getVehicleInvoices() {
        return vehicleInvoices;
    }

    /**
     * Sets the vehicleInvoices
     * @param vehicleInvoices the vehicleInvoices
     */
    public void setVehicleInvoices(Collection<VehicleInvoice> vehicleInvoices) {
        this.vehicleInvoices = vehicleInvoices;
    }


    @Override
    public LogResource getLogResource() {
        return LogResource.INVOICE;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || !(o instanceof Invoice)) return false;

        Invoice that = (Invoice) o;

        return uuid!=null&&getUuid().equals(that.getUuid());

    }

    @Override
    public int hashCode() {
        if(uuid!=null){return getUuid().hashCode();}
        return super.hashCode();
    }

    /**
     * Copies the object
     * @return the copy
     */
    @Override
    public EditableObject copy() {
        Invoice invoice = new Invoice();
        invoice.setType(type);
        invoice.setUuid(uuid);
        invoice.setStartDate(startDate);
        invoice.setEndDate(endDate);
        invoice.setPayer(payer);
        invoice.setVehicleInvoices(new ArrayList<>(vehicleInvoices));
        return invoice;
    }
}
