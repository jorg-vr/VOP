package pdf;

/**
 * Created by Billie Devolder on 14/05/2017.
 */
public class PdfException extends RuntimeException {

    public PdfException() {
        super("Could not create pdf");
    }
}
