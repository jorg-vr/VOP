package dao.interfaces;

import model.billing.Invoice;
import model.billing.InvoiceType;
import model.identity.Company;
import model.insurance.VehicleInsurance;

import java.time.LocalDate;

/**
 * Created by sam on 4/13/17.
 */
public interface InvoiceDAO extends DAO<Invoice> {

    Filter<Invoice> byPayer(Company payer);

    Filter<Invoice> byBeneficiary(Company beneficiary);

    Filter<Invoice> startsBefore(LocalDate date);

    Filter<Invoice> endsAfter(LocalDate date);

    Filter<Invoice> byPaid(boolean paid);

    Filter<Invoice> byType(InvoiceType type);
}
