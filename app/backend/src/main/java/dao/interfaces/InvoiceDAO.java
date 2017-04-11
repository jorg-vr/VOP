package dao.interfaces;

import model.billing.Invoice;
import model.identity.Company;

import java.time.LocalDate;

/**
 * Created by sam on 4/11/17.
 */
public interface InvoiceDAO extends DAO<Invoice> {

    Filter<Invoice> byBeneficiary(Company company);

    Filter<Invoice> byPayer(Company company);

    /**
     * Returns a Filter to use in listFiltered in this class, which returns all costs that are paid or not
     *
     * @param paid boolean if is paid
     * @return A useable Filter for listFiltered
     */
    Filter<Invoice> byPaid(boolean paid);

    /**
     * Returns a Filter to use in listFiltered in this class, which returns all costs after the given startDate.
     *
     * @param date The date to use in the filter
     * @return A useable Filter for listFiltered
     */
    Filter<Invoice> startAfter(LocalDate date);

    /**
     * Returns a Filter to use in ListFiltered in this class, which returns all costs before the given endDate.
     *
     * @param date The date to use in the filter
     * @return A useable Filter for listFiltered
     */
    Filter<Invoice> endBefore(LocalDate date);
}
