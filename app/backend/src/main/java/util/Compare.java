package util;

/**
 * Created by Billie Devolder on 1/05/2017.
 */
public class Compare {

    public static boolean containsIgnoreCase(String a, String b) {
        return b != null && a.toLowerCase().contains(b.toLowerCase());
    }
}