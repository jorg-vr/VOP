package pdf;

import com.itextpdf.text.*;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;

/**
 * Created by Billie Devolder on 13/05/2017.
 */
public class GreenCardCell extends PdfPCell {

    private static Font smallItalic = new Font(Font.FontFamily.TIMES_ROMAN, 6,
            Font.ITALIC);

    public GreenCardCell(String title, String body, int cols, int rows) {

        PdfPTable table = new PdfPTable(1);
        table.setHorizontalAlignment(Element.ALIGN_LEFT);
        PdfPCell c = new PdfPCell(new Paragraph(title, smallItalic));
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
