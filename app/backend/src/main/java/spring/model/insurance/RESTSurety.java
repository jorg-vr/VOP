package spring.model.insurance;

import controller.exceptions.UnAuthorizedException;
import model.account.Function;
import model.insurance.NonFlatSurety;
import model.insurance.FlatSurety;
import model.insurance.Surety;
import model.insurance.SuretyType;
import spring.model.RESTAbstractModel;

import static util.MyProperties.PATH_SURETIES;
import static util.MyProperties.getProperty;

/**
 * Created by Billie Devolder on 18/04/2017.
 */
public class RESTSurety extends RESTAbstractModel<Surety> {

    private boolean isFlat;

    // is the minimumPremium when is NonFlatSurety
    private int premium;

    // Only applies to NonFlatSurety
    private double premiumPercentage;

    private SuretyType suretyType;

    public RESTSurety() {
    }

    public RESTSurety(Surety surety) {
        super(surety.getUuid(), getProperty(PATH_SURETIES));
        if (surety instanceof FlatSurety) {
            FlatSurety flatSurety = (FlatSurety) surety;
            this.isFlat = true;
            this.premium = flatSurety.getPremium();
        } else if (surety instanceof NonFlatSurety) {
            NonFlatSurety nonFlatSurety = (NonFlatSurety) surety;
            this.isFlat = false;
            this.premium = nonFlatSurety.getMinPremium();
            this.premiumPercentage = nonFlatSurety.getPremiumPercentage();
        }
        this.suretyType = surety.getSuretyType();
    }

    @Override
    public Surety translate(Function function) throws UnAuthorizedException {
        if (isFlat) {
            FlatSurety surety = new FlatSurety();
            surety.setPremium(premium);
            return surety;
        } else {
            NonFlatSurety surety = new NonFlatSurety();
            surety.setMinPremium(premium);
            surety.setPremiumPercentage(premiumPercentage);
            return surety;
        }
    }

    public boolean isFlat() {
        return isFlat;
    }

    public void setFlat(boolean flat) {
        isFlat = flat;
    }

    public int getPremium() {
        return premium;
    }

    public void setPremium(int premium) {
        this.premium = premium;
    }

    public double getPremiumPercentage() {
        return premiumPercentage;
    }

    public void setPremiumPercentage(double premiumPercentage) {
        this.premiumPercentage = premiumPercentage;
    }

    public SuretyType getSuretyType() {
        return suretyType;
    }

    public void setSuretyType(SuretyType suretyType) {
        this.suretyType = suretyType;
    }
}
