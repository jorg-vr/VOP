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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || !(o instanceof NonFlatSurety)) return false;

        NonFlatSurety that = (NonFlatSurety) o;

        return getUuid().equals(that.getUuid());

    }

    @Override
    public int hashCode() {
        return getUuid().hashCode();
    }
}
