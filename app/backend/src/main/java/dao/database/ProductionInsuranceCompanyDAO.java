package dao.database;

import dao.interfaces.InsuranceCompanyDAO;
import model.identity.InsuranceCompany;
import org.hibernate.Session;

/**
 * Created by sam on 4/13/17.
 */
public class ProductionInsuranceCompanyDAO extends ProductionDAO<InsuranceCompany> implements InsuranceCompanyDAO {
    public ProductionInsuranceCompanyDAO(Session session) {
        super(session, InsuranceCompany.class);
    }
}
