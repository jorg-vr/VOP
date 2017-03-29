package model.billing;

/**
 * Type of Invoice. Can be either a billing (monthly payments), a statement at the end of a billing period or a correction invoice
 */
public enum InvoiceType {
    BILLING,
    STATEMENT,
    CORRECTION
}
