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
 * Created by Billie Devolder on 13/05/2017.
 */
public class GreenCard {

    private VehicleInsurance insurance;

    private Document document;
    private ByteArrayOutputStream baos;

    public GreenCard(VehicleInsurance insurance) {
        try {
            this.insurance = insurance;
            init();
            createTable();
            finish();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * Creates a table; widths are set with setWidths().
     *
     * @return a PdfPTable
     * @throws DocumentException
     */
    public void createTable() throws DocumentException {
        PdfPTable table = new PdfPTable(12);
        table.setTotalWidth(PageSize.A4.getWidth() - 5);
        table.setLockedWidth(true);

        PdfPCell c = new PdfPCell(new Paragraph("INTERNATIONALE MOTORRIJTUIGVERZEKERINGSKAART"));
        c.setHorizontalAlignment(Element.ALIGN_CENTER);
        c.setColspan(12);
        table.addCell(c);

        c = new PdfPCell(new Phrase("GELDIG"));
        c.setBorder(Rectangle.LEFT);
        c.setHorizontalAlignment(Element.ALIGN_CENTER);
        c.setColspan(6);
        table.addCell(c);

        InsuranceCompany insuranceCompany = insurance.getContract().getCompany();
        Address address = insuranceCompany.getAddress();

        String body = insuranceCompany.getName() + "\n" + address.getStreet() + " " + address.getStreetNumber()
                + "\n" + address.getPostalCode() + " " + address.getTown();
        table.addCell(new GreenCardCell("Landcode/Code verzekeraar/Nummer", body, 6, 3));

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

        Vehicle vehicle = insurance.getVehicle();
        body = vehicle.getLicensePlate();
        if (vehicle.getLicensePlate() == null) {
            body = vehicle.getChassisNumber();
        }
        table.addCell(new GreenCardCell("Kenteken (of, indien geen kenteken) chassis- of motornummer",body, 6, 2));
        table.addCell(new GreenCardCell("Soort motorrijtuig", vehicle.getType().getType(), 3, 2));
        table.addCell(new GreenCardCell("Merk motorrijtuig", vehicle.getBrand() + " " + vehicle.getModel(), 3, 2));

        Customer customer = insurance.getContract().getCustomer();
        address = customer.getAddress();
        body = customer.getName() + "\n" + address.getStreet() + " " + address.getStreetNumber()
                + "\n" + address.getPostalCode() + " " + address.getTown();
        table.addCell(new GreenCardCell("Naam en andres van verzekeringsnemer (of houder van het motorrijtuig)", body, 12, 4));

        document.add(table);
    }

    private void addDateTimeCells(PdfPTable table, LocalDateTime date) {
        table.addCell(new GreenCardCell("Dag", date.getDayOfMonth() + "", 1, 1));
        table.addCell(new GreenCardCell("Maand", date.getMonthValue() + "", 1, 1));
        table.addCell(new GreenCardCell("Jaar", date.getYear() + "" + "", 1, 1));
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

    public byte[] getAsByteArray() {
        return baos.toByteArray();
    }

    public void writeToFile(String fileName) throws IOException {

        FileOutputStream fos = new FileOutputStream(fileName);
        fos.write(baos.toByteArray());
        fos.close();
    }
}
