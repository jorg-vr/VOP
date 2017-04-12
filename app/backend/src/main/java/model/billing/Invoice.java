package model.billing;

import model.history.EditableObject;
import model.identity.Company;
import model.insurance.Contract;

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
    private Collection<Contract> contracts;

    /**
     * constructor
     */
    public Invoice() {
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

    public Collection<Contract> getContracts() {
        return contracts;
    }

    public void setContracts(Collection<Contract> contracts) {
        this.contracts = contracts;
    }
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || !(o instanceof Invoice)) return false;

        Invoice that = (Invoice) o;

        return getUuid().equals(that.getUuid());

    }

    @Override
    public int hashCode() {
        return getUuid().hashCode();
    }
}
