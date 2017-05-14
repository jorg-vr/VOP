package model.identity;

import java.util.HashMap;
import java.util.Map;

/**
 * All supported periodicities for Invoice and Statement
 * Created by jorg on 4/9/17.
 */
public enum  Periodicity {
    MONTHLY(1),
    YEARLY(12),
    HALF_YEARLY(6),
    QUARTERLY(4);

    // The time between payments in months
    private int time;

    Periodicity(int time) {
        this.time = time;
    }

    public int getTime() {
        return time;
    }

    private static Map<Integer, Periodicity> map =  new HashMap<>();
    static {
        for (Periodicity periodicity: Periodicity.values()) {
            map.put(periodicity.getTime(), periodicity);
        }
    }

    public static Periodicity fromTime(int time) {
        return map.get(time);
    }
}
