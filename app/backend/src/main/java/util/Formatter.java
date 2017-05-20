package util;

/**
 * Created by Billie Devolder on 15/05/2017.
 */
public class Formatter {

    /**
     * Create a string from the amount in euro
     * @param amount the amount in euro cents
     * @return e.g 150 -> 1,50€
     */
    public static String euroFormat(int amount) {
        String sign = "";
        if (amount < 0) {
            sign = "-";
            amount *= -1;
        }
        String euro = amount / 100 + "";
        String cents = amount % 100 + "";
        if (amount % 100 < 10) {
            cents = "0" + cents;
        }
        return sign + euro + "," + cents + "€";
    }
}
