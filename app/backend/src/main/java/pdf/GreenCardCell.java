package pdf;

import com.itextpdf.text.Font;

/**
 * A cell for in the table of the green card
 *
 * @author Billie Devolder
 */
public class GreenCardCell extends MyCell {

    private static Font smallItalic = new Font(Font.FontFamily.TIMES_ROMAN, 6,
            Font.ITALIC);

    /**
     * @param title title of this cell
     * @param body  body of this cell
     * @param cols  colspan of the cell
     * @param rows  rowspan of the cell
     */
    public GreenCardCell(String title, String body, int cols, int rows) {
        super(title, body, cols, rows, smallItalic);
    }

}
