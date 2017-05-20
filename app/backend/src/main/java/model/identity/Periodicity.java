package model.identity;

import java.util.HashMap;
import java.util.Map;

/**
 * All supported periodicities for Invoice and Statement
 * Created by jorg on 4/9/17.
 */
public enum  Periodicity {

    MONTHLY(1, "Maandelijks"),
    YEARLY(12, "Jaarlijks"),
    HALF_YEARLY(6, "Halfjaarlijks"),
    QUARTERLY(4, "Driemaandelijks");

    // The time between payments in months
    private int time;

    private String dutchTranslation;

    Periodicity(int time, String dutchTranslation) {
        this.time = time;
        this.dutchTranslation = dutchTranslation;
    }

    public int getTime() {
        return time;
    }

    public String getDutchTranslation() {
        return dutchTranslation;
    }

    private static Map<Integer, Periodicity> map =  new HashMap<>();
    static {
        for (Periodicity periodicity: Periodicity.values()) {
            map.put(periodicity.getTime(), periodicity);
        }
    }

    /**
     * Create a periodicity based on the time
     * @param time the months between payments
     */
    public static Periodicity fromTime(int time) {
        return map.get(time);
    }
}
