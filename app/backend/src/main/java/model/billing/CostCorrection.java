package model.billing;

/**
 * Created by Ponti on 27/03/2017.
 */
public class CostCorrection extends Cost {

    @Override
    public boolean isCorrection() {
        return true;
    }
}
