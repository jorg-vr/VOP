package model.billing;

/**
 * A CostCorrection object is a type of Cost that relates to another Cost that has to be corrected (be it retroactively or not)
 * The inherited 'amount' field has to contain the newly calculated cost and not the cost-error.
 * The difference to be paid out can then be calculated by taking the difference between the 'amount' of both cost objects.
 */
public class CostCorrection extends Cost {

    /**
     * The Cost object on which this correction acts. Can be null in case of a correction for a cost that was never accounted for for example.
     */
    private Cost relatedCost;

    public CostCorrection() {
    }

    @Override
    public boolean isCorrection() {
        return true;
    }

    public Cost getRelatedCost() {
        return relatedCost;
    }

    public void setRelatedCost(Cost relatedCost) {
        this.relatedCost = relatedCost;
    }
}
