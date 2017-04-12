package model.insurance;

/**
 * Created by jorg on 4/12/17.
 */
public class NonFlatSurety extends Surety {
    private int premium;

    public int getPremium() {
        return premium;
    }

    public void setPremium(int premium) {
        this.premium = premium;
    }
}
