package pdf;

import com.itextpdf.text.Element;
import com.itextpdf.text.Font;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.Rectangle;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;

/**
 * A more advanced table cell that has a title and a body
 *
 * @author Billie Devolder
 */
public class MyCell extends PdfPCell {

    private static Font smallItalic = new Font(Font.FontFamily.TIMES_ROMAN, 6,
            Font.ITALIC);

    /**
     * @param title title of this cell
     * @param body  body of this cell
     * @param cols  colspan of the cell
     * @param rows  rowspan of the cell
     * @param font  font of the title
     */
    public MyCell(String title, String body, int cols, int rows, Font font) {

        PdfPTable table = new PdfPTable(1);
        table.setHorizontalAlignment(Element.ALIGN_LEFT);
        PdfPCell c = new PdfPCell(new Paragraph(title, font));
        c.setBorder(Rectangle.NO_BORDER);
        c.setHorizontalAlignment(Element.ALIGN_LEFT);
        table.addCell(c);

        c = new PdfPCell();
        c.addElement(new Paragraph(body));
        c.setBorder(Rectangle.NO_BORDER);
        c.setHorizontalAlignment(Element.ALIGN_CENTER);
        table.addCell(c);

        addElement(table);
        setRowspan(rows);
        setColspan(cols);
    }
}
