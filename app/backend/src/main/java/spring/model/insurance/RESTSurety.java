package spring.model.insurance;

import controller.exceptions.UnAuthorizedException;
import model.account.Function;
import model.insurance.NonFlatSurety;
import model.insurance.FlatSurety;
import model.insurance.Surety;
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

    public RESTSurety() {
    }

    public RESTSurety(Surety surety) {
        super(surety.getUuid(), getProperty(PATH_SURETIES));
    }

    public RESTSurety(FlatSurety surety) {
        super(surety.getUuid(), getProperty(PATH_SURETIES));
        this.isFlat = true;
        this.premium = surety.getPremium();
    }

    public RESTSurety(NonFlatSurety surety) {
        super(surety.getUuid(), getProperty(PATH_SURETIES));
        this.isFlat = true;
        this.premium = surety.getMinPremium();
        this.premiumPercentage = surety.getPremiumPercentage();
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
}
