package model.billing;

import model.history.EditableObject;
import model.identity.Company;
import model.insurance.Insurance;

import java.net.URL;
import java.time.LocalDate;
import java.util.Collection;
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
     * company that the amount has to be paid to (should normally be Solvas but adding this just in case)
     */
    private Company beneficiary;

    /**
     * url to the billing itself
     */
    private URL url;

    /**
     * Type of Invoice. Can be either a billing (monthly payments), a statement at the end of a billing period or a correction invoice
     */
    private InvoiceType type;

    /**
     * indicates whether a bail has been paid for already or is still awaiting payment.
     */
    private boolean paid;

    /**
     * start-date of the billing period the invoice applies to
     */
    private LocalDate startDate;

    /**
     * end-date of the billing period the invoice applies to
     */
    private LocalDate endDate;

    /**
     * collection of the insurances that are the subject of this invoice
     */
    private Collection<Insurance> insurances;

    /**
     * constructor
     */
    public Invoice() {
    }

    public Invoice(Company payer, Company beneficiary, URL url, InvoiceType type, boolean paid, LocalDate startDate, LocalDate endDate) {
        this.payer = payer;
        this.beneficiary = beneficiary;
        this.url = url;
        this.type = type;
        this.paid = paid;
        this.startDate = startDate;
        this.endDate = endDate;
    }


    @Override
    public EditableObject copy() {
        return null;
    }

    @Override
    public UUID getUuid() {
        return uuid;
    }

    public void setUuid(UUID uuid) {
        this.uuid = uuid;
    }

    public Company getPayer() {
        return payer;
    }

    public void setPayer(Company payer) {
        this.payer = payer;
    }

    public Company getBeneficiary() {
        return beneficiary;
    }

    public void setBeneficiary(Company beneficiary) {
        this.beneficiary = beneficiary;
    }

    public URL getUrl() {
        return url;
    }

    public void setUrl(URL url) {
        this.url = url;
    }

    public boolean isPaid() {
        return paid;
    }

    public void setPaid(boolean paid) {
        this.paid = paid;
    }

    public LocalDate getStartDate() {
        return startDate;
    }

    public void setStartDate(LocalDate startDate) {
        this.startDate = startDate;
    }

    public LocalDate getEndDate() {
        return endDate;
    }

    public void setEndDate(LocalDate endDate) {
        this.endDate = endDate;
    }

    public InvoiceType getType() {
        return type;
    }

    public void setType(InvoiceType type) {
        this.type = type;
    }

    public Collection<Insurance> getInsurances() {
        return insurances;
    }

    public void setInsurances(Collection<Insurance> insurances) {
        this.insurances = insurances;
    }

}
