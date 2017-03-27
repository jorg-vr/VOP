package model.billing;

import model.history.EditableObject;

import java.time.LocalDate;
import java.util.UUID;

/**
 * A cost object represents the cost for 1 specific contract for 1 specific billing period.
 * Multiple cost objects can exist for 1 contract (for example 1 cost object for the standard monthly contract rate, another that represents the cost for
 * an accident which the contract is related to, etc...)
 */
public class Cost implements EditableObject, java.io.Serializable {

    private UUID uuid;

    /**
     * Stub. needs to be replaced once insurances have been implemented. (should link to the contract object)
     */
    private int ContractID;

    /**
     * how much money this cost object amounts to.
     * In case of a correction object this field still has to contain the calculated cost, irespective of the related cost object.
     */
    private double amount;

    private LocalDate startDate;

    private LocalDate endDate;

    /**
     * Indicates whether the cost object is part of an invoice yet and is thus accounted for.
     * If true then it should not be able to be added to a list of costs of another invoice (to make sure costs aren't paid for in double or more)
     * If false that means the cost has yet to be added to an invoice. (the name paidFor is not 100% correct so feel free to rename)
     */
    private boolean paidFor;

    public Cost() {
    }

    public boolean isCorrection() {
        return false;
    }

    @Override
    public EditableObject copy() {
        return null;
    }

    @Override
    public UUID getUuid() {
        return uuid;
    }

    public void setUuid(UUID uuid) {
        this.uuid = uuid;
    }

    public int getContractID() {
        return ContractID;
    }

    public void setContractID(int contractID) {
        ContractID = contractID;
    }

    public double getAmount() {
        return amount;
    }

    public void setAmount(double amount) {
        this.amount = amount;
    }

    public LocalDate getStartDate() {
        return startDate;
    }

    public void setStartDate(LocalDate startDate) {
        this.startDate = startDate;
    }

    public LocalDate getEndDate() {
        return endDate;
    }

    public void setEndDate(LocalDate endDate) {
        this.endDate = endDate;
    }

    public boolean isPaidFor() {
        return paidFor;
    }

    public void setPaidFor(boolean paidFor) {
        this.paidFor = paidFor;
    }
}
