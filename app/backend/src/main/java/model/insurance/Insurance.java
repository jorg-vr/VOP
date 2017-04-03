package model.insurance;


import model.history.EditableObject;
import model.identity.InsuranceCompany;

import java.util.Collection;
import java.util.UUID;

/**
 * Insurance class representing an insurance linked to a (group of) vehicle(s).
 */
public class Insurance implements EditableObject, Calculation, java.io.Serializable {

    /**
     * Company that offers insurance to customer.
     */
    private InsuranceCompany company;

    /**
     * Price of insurance (gross premium). This is the amount the holder of the insurance has to pay and is based on
     * the net premium (= gross premium - taxes and changes) and the risk premium (net premium - commission costs).
     */

    private int price;

    /**
     * Franchise of the insurance. This is the amount that has to be paid by the one who holds the insurance.
     * This can either be a percentage or a fixed amount.
     */
    private int franchise;

    /**
     * Collection of insurance sureties included in the insurance. These determine the coverage
     * of the insurance itself aswell as the franchise and price of the insurance according
     * to their taxes and commission costs.
     */

    private Collection<Surety> sureties;

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

    /**
     * Implementation of the function to calculate price of insurance.
     */
    @Override
    public void calculatePrice() {

    }

    public InsuranceCompany getCompany() {
        return company;
    }

    public void setCompany(InsuranceCompany company) {
        this.company = company;
    }

    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }

    public int getFranchise() {
        return franchise;
    }

    public void setFranchise(int franchise) {
        this.franchise = franchise;
    }

    public Collection<Surety> getSureties() {
        return sureties;
    }

    public void setSureties(Collection<Surety> sureties) {
        this.sureties = sureties;
    }
}
