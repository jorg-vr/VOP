package spring.model;

import controller.CompanyController;
import controller.ControllerManager;
import controller.exceptions.UnAuthorizedException;
import dao.exceptions.DataAccessException;
import model.billing.Invoice;
import model.billing.InvoiceType;
import spring.exceptions.InvalidInputException;
import util.UUIDUtil;

import java.time.LocalDateTime;

/**
 * Created by Billie Devolder on 16/04/2017.
 */
public class RESTInvoice extends RESTAbstractModel<Invoice> {
    /**
     * Company that has to pay the invoice/is the receiver of the invoice
     */
    private String payer;

    /**
     * company that the amount has to be paid to (should normally be Solvas but adding this just in case)
     */
    private String beneficiary;

    /**
     * Type of Invoice. Can be either a billing (monthly payments), a statement at the end of a billing period or a correction invoice
     */
    private String type;

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

    private int totalAmount;

    private int totalTax;

    public RESTInvoice() {

    }

    public RESTInvoice(Invoice invoice) {
        super(invoice.getUuid(), "invoices");
        setType(invoice.getType().toString());
        setBeneficiary(UUIDUtil.UUIDToNumberString(invoice.getBeneficiary().getUuid()));
        setPaid(invoice.isPaid());
        setEndDate(invoice.getEndDate());
        setStartDate(invoice.getStartDate());
        setTotalAmount(invoice.calculateCost());
        setTotalTax(invoice.calculateTax());
        setPayer(UUIDUtil.UUIDToNumberString(invoice.getPayer().getUuid()));
    }

    @Override
    public Invoice translate(ControllerManager manager) throws UnAuthorizedException {
        Invoice invoice = new Invoice();
        invoice.setUuid(UUIDUtil.toUUID(getId()));
        invoice.setEndDate(getEndDate());
        invoice.setStartDate(getStartDate());
        invoice.setPaid(isPaid());
        invoice.setType(InvoiceType.valueOf(getType()));

        CompanyController controller = manager.getCompanyController();

        try {
            invoice.setBeneficiary(controller.get(UUIDUtil.toUUID(getBeneficiary())));
        } catch (DataAccessException e) {
            throw new InvalidInputException("benificiary");
        }

        try {
            invoice.setPayer(controller.get(UUIDUtil.toUUID(getPayer())));
        } catch (DataAccessException e) {
            throw new InvalidInputException("payer");
        }

        return invoice;
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

    public String getPayer() {
        return payer;
    }

    public void setPayer(String payer) {
        this.payer = payer;
    }

    public String getBeneficiary() {
        return beneficiary;
    }

    public void setBeneficiary(String beneficiary) {
        this.beneficiary = beneficiary;
    }

    public int getTotalTax() {
        return totalTax;
    }

    public void setTotalTax(int totalTax) {
        this.totalTax = totalTax;
    }
}
