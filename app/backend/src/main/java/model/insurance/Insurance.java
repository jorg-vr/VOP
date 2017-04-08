package model.insurance;


import model.history.EditableObject;
import model.identity.InsuranceCompany;

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

    private int premium;

    /**
     * Franchise of the insurance. This is the amount that has to be paid by the one who holds the insurance.
     * This is a fixed amount.
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

    public int getPremium() {
        return premium;
    }

    public void setPremium(int premium) {
        this.premium = premium;
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
