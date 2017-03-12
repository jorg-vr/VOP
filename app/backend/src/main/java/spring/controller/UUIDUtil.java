package spring.controller;

import java.math.BigInteger;
import java.util.UUID;

public class UUIDUtil {

    /**
     * Transforms a uuid to a string representation of a positive integer
     * Note that uuid == toUUID(UUIDToNumberString(uuid))
     * @param uuid
     * @return
     */
    public static String UUIDToNumberString(UUID uuid) {
        return String.format("%040d", new BigInteger(uuid.toString().replace("-", ""), 16)).replaceAll("^0+", "");
    }

    /**
     * Transforms a string representation of a positive integer to a UUID
     * Note that uuid == toUUID(UUIDToNumberString(uuid))
     * @param numberString should be a string representation of a positive integer
     * @return
     */
    public static UUID toUUID(String numberString) {
        String leadingZero = null;
        String hexStringWithInsertedHyphens;
        BigInteger intValue = new BigInteger(numberString);

        int tmplength = intValue.toString(16).length();
        if (tmplength < 32) {
            int diff = 32 - tmplength;
            String appendValue = "";
            for (int i = 0; i < diff; i++) {
                appendValue += "0";
            }
            leadingZero = appendValue + intValue.toString(16);
        }
        if (leadingZero != null) {
            hexStringWithInsertedHyphens = leadingZero.replaceFirst("([0-9a-fA-F]{8})([0-9a-fA-F]{4})([0-9a-fA-F]{4})([0-9a-fA-F]{4})([0-9a-fA-F]+)", "$1-$2-$3-$4-$5");
        } else {
            hexStringWithInsertedHyphens = intValue.toString(16).replaceFirst("([0-9a-fA-F]{8})([0-9a-fA-F]{4})([0-9a-fA-F]{4})([0-9a-fA-F]{4})([0-9a-fA-F]+)", "$1-$2-$3-$4-$5");
        }
        return UUID.fromString(hexStringWithInsertedHyphens);
    }
}