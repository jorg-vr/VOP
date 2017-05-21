package dao.database;

import dao.interfaces.Filter;
import dao.interfaces.InvoiceDAO;
import model.billing.Invoice;
import model.billing.InvoiceType;
import model.identity.Company;
import model.insurance.VehicleInsurance;
import org.hibernate.Session;

import java.time.LocalDate;
import java.time.LocalDateTime;

/**
 * Created by sam on 4/13/17.
 */
public class ProductionInvoiceDAO extends ProductionDAO<Invoice> implements InvoiceDAO{

    public ProductionInvoiceDAO(Session session) {
        super(session, Invoice.class);
    }

    @Override
    public Filter<Invoice> byPayer(Company payer) {
        return filterEqual("payer",payer);
    }

    @Override
    public Filter<Invoice> startsBefore(LocalDateTime date) {
        if(date==null){
            return ()->{};
        }
        return () ->
                getPredicates().add(getCriteriaBuilder().lessThanOrEqualTo(getRoot().get("startDate"), date));
    }

    @Override
    public Filter<Invoice> endsAfter(LocalDateTime date) {
        if(date==null){
            return ()->{};
        }
        return () ->
                getPredicates().add(getCriteriaBuilder().greaterThanOrEqualTo(getRoot().get("endDate"), date));
    }

    @Override
    public Filter<Invoice> byPaid(boolean paid) {
        return filterEqual("paid",paid);
    }

    @Override
    public Filter<Invoice> byType(InvoiceType type) {
        return filterEqual("type",type);
    }
}
