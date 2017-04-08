package model.insurance;


import model.history.EditableObject;
import model.identity.InsuranceCompany;

import java.util.UUID;

/**
 * Surety class representing an surety linked to a (group of) vehicle(s).
 */
public class Surety implements EditableObject, java.io.Serializable {



    /**
     * Percentage of insured capital that has to be paid yearly
     */

    private int premium;

    /**
     * Franchise of the surety. This is the amount that has to be paid by the one who holds the surety.
     * This is a fixed amount.
     */
    private int franchise;

    /**
     * Type of surety included
     */

    private SuretyType suretyType;

    /**
     * Constructor
     */
    public Surety() {
    }

    @Override
    public UUID getUuid() {
        return null;
    }

    @Override
    public EditableObject copy() {
        return null;
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
