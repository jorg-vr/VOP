package spring.model;

import controller.CompanyController;
import controller.ControllerManager;
import controller.exceptions.UnAuthorizedException;
import dao.exceptions.ConstraintViolationException;
import dao.exceptions.DataAccessException;
import dao.exceptions.ObjectNotFoundException;
import model.billing.Invoice;
import model.billing.InvoiceType;
import model.billing.VehicleInvoice;
import spring.exceptions.ErrorCode;
import spring.exceptions.InvalidInputException;
import spring.exceptions.NotImplementedException;
import util.UUIDUtil;

import java.time.LocalDateTime;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * Created by Billie Devolder on 16/04/2017.
 */
public class RESTInvoice extends RESTAbstractModel<Invoice> {

    /**
     * Company that has to pay the invoice/is the receiver of the invoice
     */
    private String payer;


    /**
     * Type of Invoice. Can be either a billing (monthly payments),
     * a statement at the end of a billing period or a correction invoice,
     * or a correction
     */
    private InvoiceType type;

    /**
     * indicates whether a bill has been paid for already or is still awaiting payment.
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

    private int totalAmount;

    private int totalTax;


    public RESTInvoice() {

    }

    public RESTInvoice(Invoice invoice) {
        super(invoice.getUuid(), "invoices");
        setType(invoice.getType());
        setPaid(invoice.isPaid());
        setEndDate(invoice.getEndDate());
        setStartDate(invoice.getStartDate());
        setTotalAmount(invoice.calculateCost());
        setTotalTax(invoice.calculateTax());
        setPayer(UUIDUtil.UUIDToNumberString(invoice.getPayer().getUuid()));
    }

    /**
     * Cannot be used for put or post so no implementation needed
     * @throws NotImplementedException
     */
    @Override
    public Invoice translate(ControllerManager manager) throws NotImplementedException {
        throw new NotImplementedException();
    }


    public String getPayer() {
        return payer;
    }

    public void setPayer(String payer) {
        this.payer = payer;
    }

    public InvoiceType getType() {
        return type;
    }

    public void setType(InvoiceType type) {
        this.type = type;
    }

    public boolean isPaid() {
        return paid;
    }

    public void setPaid(boolean paid) {
        this.paid = paid;
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




    public int getTotalAmount() {
        return totalAmount;
    }

    public void setTotalAmount(int totalAmount) {
        this.totalAmount = totalAmount;
    }

    public int getTotalTax() {
        return totalTax;
    }

    public void setTotalTax(int totalTax) {
        this.totalTax = totalTax;
    }
}
