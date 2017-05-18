package dao.interfaces;

import model.billing.Invoice;
import model.billing.InvoiceType;
import model.identity.Company;
import model.insurance.VehicleInsurance;

import java.time.LocalDate;

/**
 * DAO for the bean Invoice
 * Created by sam on 4/13/17.
 */
public interface InvoiceDAO extends DAO<Invoice> {

    /**
     * Returns a Filter to use in ListFiltered in this class, which returns all invoices the given payer has.
     * @param payer the payer
     * @return a useable Filter for listFiltered
     */
    Filter<Invoice> byPayer(Company payer);

    /**
     * Returns a Filter to use in ListFiltered in this class, which returns all invoices the given beneficiary has.
     * @param beneficiary the beneficiary
     * @return a useable Filter for listFiltered
     */
    Filter<Invoice> byBeneficiary(Company beneficiary);

    /**
     * Returns a Filter to use in ListFiltered in this class, which returns all invoices starting before the given date.
     * @param date the date
     * @return a useable Filter for listFiltered
     */
    Filter<Invoice> startsBefore(LocalDate date);

    /**
     * Returns a Filter to use in ListFiltered in this class, which returns all invoices ending after the given date.
     * @param date the date
     * @return a useable Filter for listFiltered
     */
    Filter<Invoice> endsAfter(LocalDate date);

    /**
     * Returns a Filter to use in ListFiltered in this class, which returns all invoices that are paid.
     * @param paid  paid or not
     * @return a useable Filter for listFiltered
     */
    Filter<Invoice> byPaid(boolean paid);

    /**
     * Returns a Filter to use in ListFiltered in this class, which returns all invoices with the given type.
     * @param type the payer
     * @return a useable Filter for listFiltered
     */
    Filter<Invoice> byType(InvoiceType type);
}
