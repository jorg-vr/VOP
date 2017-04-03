package model.insurance;

/**
 * Type of insurance surety with default values for taxes/charges and commission costs.
 *
 */
public enum SuretyType {
    CIVIL_LIABILITY(17,27.10),
    OMNIUM_FULL(19,26.75),
    OMNIUM_PARTIAL(19,26.75),
    LEGAL_AID(25,16.75),
    TRAVEL_AID(19,16.75),
    SAFETY(25,16.75)
    ;

    private double taxes;
    private double commission;

    private SuretyType(double commission, double taxes){
        this.commission=commission;
        this.taxes=taxes;
    }

    public double getTaxes() {
        return taxes;
    }

    public double getCommission() {
        return commission;
    }
}