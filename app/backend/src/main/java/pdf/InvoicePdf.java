package pdf;

import com.itextpdf.text.*;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;

import java.io.IOException;
import java.time.LocalDate;

/**
 * Created by Billie Devolder on 15/05/2017.
 */
public class InvoicePdf extends Pdf {

    private static Font bold = new Font(Font.FontFamily.TIMES_ROMAN, 12,
            Font.BOLD);

    public InvoicePdf() {
        super();
    }

    @Override
    protected void generatePdf() throws DocumentException {
        generateFirstPage();
        generateVehicleInvoices();
    }

    private void generateFirstPage() throws DocumentException {
        Document document = getDocument();

        // Add the logo
        try {
            Image img = Image.getInstance(ClassLoader.getSystemResource("solvas_logo.png"));
            img.scaleAbsolute(150f, 150f);
            document.add(img);
        } catch (IOException e) {
            e.printStackTrace();
            throw new DocumentException();
        }

        Paragraph paragraph = new Paragraph("Aan\na\nb");
        paragraph.setAlignment(Element.ALIGN_RIGHT);
        document.add(paragraph);

        PdfPTable table = new PdfPTable(3);
        table.setTotalWidth(PageSize.A4.getWidth() - 5);
        table.setLockedWidth(true);

        String[] titles = {"Vervaldagbericht", "Ons kenmerk", "Zomergem"};
        LocalDate now = LocalDate.now();
        String[] bodies = {"Contante", "15158/00-483", now.getDayOfMonth() + "/" + now.getMonthValue() + "/" + now.getYear()};
        for (int i = 0; i < titles.length; i++) {
            PdfPCell c = new PdfPCell();
            c.addElement(new Paragraph(titles[i], bold));
            c.addElement(new Paragraph(bodies[i]));
            c.setBorder(Rectangle.NO_BORDER);
            table.addCell(c);
        }
        document.add(table);

        document.add(Chunk.NEWLINE);
        document.add(Chunk.NEWLINE);

        table = new PdfPTable(12);
        table.setTotalWidth(PageSize.A4.getWidth() - 5);
        table.setLockedWidth(true);
        table.addCell(new InvoiceCell("Domein-omschrijving", "auto", 6, 1));
        table.addCell(new InvoiceCell("Maatschappij", "", 3, 1));
        table.addCell(new InvoiceCell("Polisnummer", "", 3, 1));

        table.addCell(new InvoiceCell("Van", "", 2, 1));
        table.addCell(new InvoiceCell("Tot", "", 2, 1));
        table.addCell(new InvoiceCell("Periodiciteit", "", 2, 1));

        table.addCell(new InvoiceCell("Netto premie", "", 2, 1));
        table.addCell(new InvoiceCell("Taksen en kosten", "", 2, 1));
        table.addCell(new InvoiceCell("Totale premie", "", 2, 1));

        table.addCell(new InvoiceCell("Verzekerd risico en waarborgen", "", 12, 1));

        document.add(table);

    }

    private static Font smallBold = new Font(Font.FontFamily.TIMES_ROMAN, 30,
            Font.BOLD);

    private void generateVehicleInvoices() throws DocumentException {
        Document document = getDocument();
        document.newPage();
        Paragraph paragraph = new Paragraph("Voertuigverzekeringen", smallBold);
        paragraph.setAlignment(Element.ALIGN_CENTER);
        document.add(paragraph);

        document.add(Chunk.NEWLINE);

        PdfPTable table = new PdfPTable(5);
        table.setTotalWidth(PageSize.A4.getWidth() - 5);
        table.setLockedWidth(true);

        String[] titles = {"Kenteken", "Waarborg", "Netto premie", "Taksen en kosten", "Totale premie"};
        for (String title: titles) {
            table.addCell(new PdfPCell(new Paragraph(title)));
        }

        for (int i = 0; i < 10; i++) {
            table.addCell(new PdfPCell(new Paragraph("ABC-123")));
            table.addCell(new PdfPCell(new Paragraph("Omnium")));
            table.addCell(new PdfPCell(new Paragraph("10,05")));
            table.addCell(new PdfPCell(new Paragraph("3,89")));
            table.addCell(new PdfPCell(new Paragraph("19,90")));
        }
        document.add(table);
    }
}
