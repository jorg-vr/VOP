package pdf;

import com.itextpdf.text.*;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import model.billing.Invoice;
import model.billing.VehicleInvoice;
import model.identity.Address;
import model.identity.Company;
import model.identity.Periodicity;

import java.io.IOException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Collections;

import static util.Formatter.euroFormat;

/**
 * This class represents a invoice pdf
 */
public class InvoicePdf extends Pdf {

    private static Font bold = new Font(Font.FontFamily.TIMES_ROMAN, 12,
            Font.BOLD);

    private static Font largeBold = new Font(Font.FontFamily.TIMES_ROMAN, 20,
            Font.BOLD);

    private static DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");


    private Invoice invoice;

    /**
     * @param invoice for which the pdf should be created
     * @throws PdfException pdf could not be created
     */
    public InvoicePdf(Invoice invoice) throws PdfException {
        this.invoice = invoice;
        generatePdf();
    }

    @Override
    protected void generateDocument() throws DocumentException {
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

        document.add(new Paragraph(invoice.getType().getDutchTranslation(), new Font(Font.FontFamily.TIMES_ROMAN, 30,
                Font.NORMAL)));

        Company payer = invoice.getPayer();
        Address address = payer.getAddress();
        Paragraph paragraph = new Paragraph("Aan\n"
                + payer.getName() + "\n"
                + address.getStreet() + " " + address.getStreetNumber() + "\n"
                + address.getPostalCode() + " " + address.getTown());
        paragraph.setIndentationLeft(400f);
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

        LocalDateTime startDate = invoice.getStartDate();
        LocalDateTime endDate = invoice.getEndDate();
        table.addCell(new InvoiceCell("Van", startDate.format(formatter), 2, 1));
        table.addCell(new InvoiceCell("Tot", endDate.format(formatter), 2, 1));

        Periodicity periodicity = payer.getPaymentPeriod();
        table.addCell(new InvoiceCell("Periodiciteit", periodicity.getDutchTranslation(), 2, 1));

        int cost = invoice.calculateCost();
        int tax = invoice.calculateTax();
        int total = cost + tax;
        table.addCell(new InvoiceCell("Netto premie", euroFormat(cost), 2, 1));
        table.addCell(new InvoiceCell("Taksen en kosten", euroFormat(tax), 2, 1));
        table.addCell(new InvoiceCell("Totale premie", euroFormat(total), 2, 1));

        document.add(table);
    }

    private void generateVehicleInvoices() throws DocumentException {
        Document document = getDocument();
        document.newPage();

        ArrayList<VehicleInvoice> invoiceList = new ArrayList<>(invoice.getVehicleInvoices());

        Paragraph paragraph = new Paragraph("Specificatie verzekerde voertuigen", largeBold);
        document.add(paragraph);

        document.add(new Paragraph("Aantal: " + invoiceList.size()));

        document.add(Chunk.NEWLINE);

        PdfPTable table = new PdfPTable(5);
        table.setTotalWidth(PageSize.A4.getWidth() - 20);
        table.setLockedWidth(true);

        String[] titles = {"Kenteken", "Waarborg", "Netto premie", "Taksen en kosten", "Totale premie"};
        for (String title : titles) {
            table.addCell(new PdfPCell(new Paragraph(title)));
        }

        Collections.sort(invoiceList, (a, b) -> a.getLicensePlate().compareTo(b.getLicensePlate()));
        VehicleInvoice last = null;
        if (invoiceList.size() > 0) {
            last = invoiceList.get(invoiceList.size() - 1);
        }
        String previousLicensePlate = null;
        for (VehicleInvoice vehicleInvoice : invoiceList) {

            String licencePlate = vehicleInvoice.getLicensePlate();
            PdfPCell cell;
            if (licencePlate.equals(previousLicensePlate)) {
                cell = new PdfPCell();
                cell.setBorder(Rectangle.RIGHT | Rectangle.LEFT);
            } else {
                cell = new PdfPCell(new Paragraph(licencePlate));
                cell.setBorder(Rectangle.RIGHT | Rectangle.LEFT | Rectangle.TOP);
            }

            // Draw a bottom border, if this vehicle invoice is the last one
            if (vehicleInvoice.equals(last)) {
                cell.setBorder(cell.getBorder() | Rectangle.BOTTOM);
            }

            table.addCell(cell);
            previousLicensePlate = licencePlate;


            table.addCell(new PdfPCell(new Paragraph(vehicleInvoice.getSuretyType().getDutchTranslation() + "")));

            int cost = vehicleInvoice.getTotalCost();
            int tax = vehicleInvoice.getTotalTax();
            int total = cost + tax;
            table.addCell(new PdfPCell(new Paragraph(euroFormat(cost))));
            table.addCell(new PdfPCell(new Paragraph(euroFormat(tax))));
            table.addCell(new PdfPCell(new Paragraph(euroFormat(total))));
        }
        document.add(table);
    }
}
