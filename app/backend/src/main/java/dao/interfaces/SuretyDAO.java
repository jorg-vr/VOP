package dao.interfaces;

import model.identity.InsuranceCompany;
import model.insurance.Surety;

/**
 * DAO for bean Surety
 * Created by sam on 4/12/17.
 */
public interface SuretyDAO<T extends Surety> extends DAO<T>{

    /**
     * Returns a Filter to use in ListFiltered in this class, which returns all Sureties the given company has.
     * @param company the customer to use in the filter
     * @return a usable filter for ListFiltered
     */
    Filter<T> byOwner(InsuranceCompany company);
}
