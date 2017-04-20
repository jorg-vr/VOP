package model.insurance;

/**
 * Created by jorg on 4/12/17.
 */
public class FlatSurety extends Surety {

    // In cents
    private int premium;

    public FlatSurety() {
    }

    public FlatSurety(int premium) {
        this.premium = premium;
    }

    @Override
    public int calculatePremium(int value) {
        return premium;
    }

    public int getPremium() {
        return premium;
    }

    public void setPremium(int premium) {
        this.premium = premium;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || !(o instanceof FlatSurety)) return false;

        FlatSurety that = (FlatSurety) o;

        return getUuid().equals(that.getUuid());

    }

    @Override
    public int hashCode() {
        return getUuid().hashCode();
    }
}
