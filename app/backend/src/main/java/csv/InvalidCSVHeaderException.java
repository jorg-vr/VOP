package csv;

/**
 * Created by sam on 5/7/17.
 */
public class InvalidCSVHeaderException extends Exception {

    public InvalidCSVHeaderException(String s) {
        super(s);
    }
}
