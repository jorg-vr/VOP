package spring.model;

import controller.exceptions.UnAuthorizedException;
import model.account.Function;
import model.billing.Invoice;
import model.history.EditEvent;

import java.time.LocalDateTime;
import java.util.UUID;

/**
 * Created by Billie Devolder on 16/04/2017.
 */
public class RESTInvoice extends RESTAbstractModel<Invoice> {

    // The ID of the fleet this invoice belongs to.
    private String fleet;

    // Shows if the invoice is paid or not.
    private boolean paid;

    // The total amount of money that has to be paid in this invoice.
    private int totalAmount;

    // The type of the invoice. The only possible values are billing or payment
    private String type;

    private LocalDateTime startDate;
    private LocalDateTime endDate;

    public RESTInvoice() {

    }

    public RESTInvoice(Invoice invoice) {
        super(invoice.getUuid(), "invoices");
    }

    @Override
    public Invoice translate(Function function) throws UnAuthorizedException {
        return null;
    }

    public String getFleet() {
        return fleet;
    }

    public void setFleet(String fleet) {
        this.fleet = fleet;
    }

    public boolean isPaid() {
        return paid;
    }

    public void setPaid(boolean paid) {
        this.paid = paid;
    }

    public int getTotalAmount() {
        return totalAmount;
    }

    public void setTotalAmount(int totalAmount) {
        this.totalAmount = totalAmount;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public LocalDateTime getStartDate() {
        return startDate;
    }

    public void setStartDate(LocalDateTime startDate) {
        this.startDate = startDate;
    }

    public LocalDateTime getEndDate() {
        return endDate;
    }

    public void setEndDate(LocalDateTime endDate) {
        this.endDate = endDate;
    }
}
