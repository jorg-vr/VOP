package pdf;

import com.itextpdf.text.*;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;
import model.fleet.Vehicle;
import model.identity.Address;
import model.identity.Customer;
import model.identity.InsuranceCompany;
import model.insurance.VehicleInsurance;

import java.io.ByteArrayOutputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.time.LocalDateTime;

/**
 * This class is a representation of a green card pdf
 * @author Billie Devolder
 */
public class GreenCard {

    private VehicleInsurance insurance;

    private Document document;
    private ByteArrayOutputStream baos;

    /**
     *
     * @param insurance vehicle insurance for which the greencard should be created
     * @throws PdfException pdf could not be created
     */
    public GreenCard(VehicleInsurance insurance) {
        try {
            this.insurance = insurance;
            init();
            createGreenCard();
            finish();
        } catch (DocumentException e) {
            throw new PdfException();
        }
    }

    private void createGreenCard() throws DocumentException {
        PdfPTable table = new PdfPTable(12);
        table.setTotalWidth(PageSize.A4.getWidth() - 5);
        table.setLockedWidth(true);

        // Add a title
        PdfPCell c = new PdfPCell(new Paragraph("INTERNATIONALE MOTORRIJTUIGVERZEKERINGSKAART"));
        c.setHorizontalAlignment(Element.ALIGN_CENTER);
        c.setColspan(12);
        table.addCell(c);

        c = new PdfPCell(new Phrase("GELDIG"));
        c.setBorder(Rectangle.LEFT);
        c.setHorizontalAlignment(Element.ALIGN_CENTER);
        c.setColspan(6);
        table.addCell(c);

        // Add information about the insurance company
        InsuranceCompany insuranceCompany = insurance.getContract().getCompany();
        Address address = insuranceCompany.getAddress();

        String body = insuranceCompany.getName() + "\n" + address.getStreet() + " " + address.getStreetNumber()
                + "\n" + address.getPostalCode() + " " + address.getTown();
        table.addCell(new GreenCardCell("Landcode/Code verzekeraar/Nummer", body, 6, 3));

        // Add information about the start and end date
        c = new PdfPCell(new Phrase("VAN"));
        c.setBorder(Rectangle.LEFT);
        c.setHorizontalAlignment(Element.ALIGN_CENTER);
        c.setColspan(3);
        table.addCell(c);

        c = new PdfPCell(new Phrase("TOT"));
        c.setBorder(Rectangle.NO_BORDER);
        c.setHorizontalAlignment(Element.ALIGN_CENTER);
        c.setColspan(3);
        table.addCell(c);

        addDateTimeCells(table, insurance.getStartDate());
        addDateTimeCells(table, insurance.getEndDate());

        // Add information about the vehicle
        Vehicle vehicle = insurance.getVehicle();
        body = vehicle.getLicensePlate();
        if (vehicle.getLicensePlate() == null) {
            body = vehicle.getVin();
        }
        table.addCell(new GreenCardCell("Kenteken (of, indien geen kenteken) chassis- of motornummer", body, 6, 2));
        table.addCell(new GreenCardCell("Soort motorrijtuig", vehicle.getType().getType(), 3, 2));
        table.addCell(new GreenCardCell("Merk motorrijtuig", vehicle.getBrand() + " " + vehicle.getModel(), 3, 2));

        // Add information about the customer
        Customer customer = insurance.getContract().getCustomer();
        address = customer.getAddress();
        body = customer.getName() + "\n" + address.getStreet() + " " + address.getStreetNumber()
                + "\n" + address.getPostalCode() + " " + address.getTown();
        table.addCell(new GreenCardCell("Naam en andres van verzekeringsnemer (of houder van het motorrijtuig)", body, 12, 4));

        document.add(table);
    }

    /**
     * Add 3 GreenCardCells to the table of colspan and widthspan 1.
     * There will be 1 cell for day, month and year
     * @param table table where the cells should be added to
     * @param date date value that should be put in the cells
     */
    private void addDateTimeCells(PdfPTable table, LocalDateTime date) {
        String day = "";
        String month = "";
        String year = "";
        if (date != null) {
            day = date.getDayOfMonth() + "";
            month = date.getMonthValue() + "";
            year = date.getYear() + "";
        }
        table.addCell(new GreenCardCell("Dag", day + "", 1, 1));
        table.addCell(new GreenCardCell("Maand", month + "", 1, 1));
        table.addCell(new GreenCardCell("Jaar", year + "" + "", 1, 1));
    }

    private void init() throws DocumentException {
        document = new Document();
        baos = new ByteArrayOutputStream();
        PdfWriter.getInstance(document, baos);
        document.open();
    }

    private void finish() {
        document.close();
    }

    /**
     * @return the pdf document as a byte array
     */
    public byte[] getAsByteArray() {
        return baos.toByteArray();
    }

    /**
     * Write the green card pdf to a file
     * @param path path of the file where the green card should be written to
     * @throws IOException could not write pdf to file
     */
    public void writeToFile(String path) throws IOException {
        FileOutputStream fos = new FileOutputStream(path);
        fos.write(baos.toByteArray());
        fos.close();
    }
}