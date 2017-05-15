package pdf;

import com.itextpdf.text.Font;

/**
 * A cell for in the table of the green card
 *
 * @author Billie Devolder
 */
public class InvoiceCell extends MyCell {

    private static Font smallBold = new Font(Font.FontFamily.TIMES_ROMAN, 8,
            Font.BOLD);

    public InvoiceCell(String title, String body, int cols, int rows) {
        super(title, body, cols, rows, smallBold);
    }
}
