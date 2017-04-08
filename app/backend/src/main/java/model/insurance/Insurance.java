package model.insurance;


import model.fleet.VehicleType;
import model.history.EditableObject;
import model.identity.InsuranceCompany;

import java.util.Collection;
import java.util.Map;
import java.util.UUID;

/**
 * Insurance class representing an insurance linked to a (group of) vehicle(s).
 */
public class Insurance implements EditableObject, java.io.Serializable {

    /**
     * Company that offers insurance to customer.
     */
    private InsuranceCompany company;

    /**
     * Percentage of insured capital that has to be paid yearly
     */

    private int netPrice;

    /**
     * Franchise of the insurance. This is the amount that has to be paid by the one who holds the insurance.
     * This can either be a percentage or a fixed amount.
     */
    private int franchise;

    /**
     * Type of insurance included
     */

    private SuretyType suretyType;

    /**
     * Constructor
     */
    public Insurance() {
    }

    @Override
    public UUID getUuid() {
        return null;
    }

    @Override
    public EditableObject copy() {
        return null;
    }


    public InsuranceCompany getCompany() {
        return company;
    }

    public void setCompany(InsuranceCompany company) {
        this.company = company;
    }

    public int getNetPrice() {
        return netPrice;
    }

    public void setNetPrice(int netPrice) {
        this.netPrice = netPrice;
    }

    public int getFranchise() {
        return franchise;
    }

    public void setFranchise(int franchise) {
        this.franchise = franchise;
    }

    public SuretyType getSuretyType() {
        return suretyType;
    }

    public void setSuretyType(SuretyType suretyType) {
        this.suretyType = suretyType;
    }

}
