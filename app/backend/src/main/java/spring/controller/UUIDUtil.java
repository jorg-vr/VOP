package spring.controller;

import java.math.BigInteger;
import java.util.UUID;

public class UUIDUtil {

    /**
     * Transforms a uuid to a string representation of a positive integer
     * Note that uuid == toUUID(UUIDToNumberString(uuid))
     *
     * @param uuid if null, null will be returned
     * @return if uuid is null, null will be returned
     */
    public static String UUIDToNumberString(UUID uuid) {
        if(uuid==null){
            return null;
        }
        String string = String.format("%040d", new BigInteger(uuid.toString().replace("-", ""), 16)).replaceAll("^0+", "");
        // TODO try to do this in regex
        if (string.equals("")) {
            string = "0";
        }
        return string;
    }

    /**
     * Transforms a string representation of a positive integer to a UUID
     * Note that uuid == toUUID(UUIDToNumberString(uuid))
     *
     * @param numberString should be a string representation of a positive integer
     * @return if numberString is null, null will be returned
     */
    public static UUID toUUID(String numberString) {
        if(numberString==null){
            return null;
        }
        String hex = new BigInteger(numberString).toString(16);
        while (hex.length() < 32) {
            hex = "0" + hex;
        }
        String hexWithHyphens = hex.replaceFirst("([0-9a-fA-F]{8})([0-9a-fA-F]{4})([0-9a-fA-F]{4})([0-9a-fA-F]{4})([0-9a-fA-F]+)", "$1-$2-$3-$4-$5");
        return UUID.fromString(hexWithHyphens);
    }
}
