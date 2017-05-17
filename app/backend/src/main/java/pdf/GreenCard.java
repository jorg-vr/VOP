package pdf;

import com.itextpdf.text.*;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import model.fleet.Vehicle;
import model.identity.Address;
import model.identity.Customer;
import model.identity.InsuranceCompany;
import model.insurance.VehicleInsurance;

import java.time.LocalDateTime;

/**
 * This class is a representation of a green card pdf
 *
 * @author Billie Devolder
 */
public class GreenCard extends Pdf {

    // Codes of the countries that are on the green card
    private String[] countries = {"A", "B", "BG", "CY", "CZ", "D", "DK", "E", "F", "FIN", "GB", "GR", "H", "HR", "I", "IRL"
            , "IS", "L", "LT", "LV", "M", "N", "NL", "P", "PL", "RO", "S", "SK", "SLO", "CH", "AL", "AND", "BIH"
            , "BY", "IL", "IR", "MA", "MD", "MK", "MNE", "RUS", "SRB", "TN", "TR", "UA"};

    private VehicleInsurance insurance;

    /**
     * @param insurance vehicle insurance for which the greencard should be created
     * @throws PdfException pdf could not be created
     */
    public GreenCard(VehicleInsurance insurance) {
        super();
        this.insurance = insurance;
        generatePdf();
    }

    protected void generateDocument() throws DocumentException {
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

        addInsuranceCompanyInformation(table);
        addDateInformation(table);
        addVehicleInformation(table);
        addCoverageArea(table);
        addCustomerInformation(table);

        getDocument().add(table);
    }

    private void addInsuranceCompanyInformation(PdfPTable table) {
        InsuranceCompany insuranceCompany = insurance.getContract().getCompany();
        Address address = insuranceCompany.getAddress();

        String body = insuranceCompany.getName() + "\n" + address.getStreet() + " " + address.getStreetNumber()
                + "\n" + address.getPostalCode() + " " + address.getTown();
        table.addCell(new GreenCardCell("Landcode/Code verzekeraar/Nummer", body, 6, 3));
    }

    private void addDateInformation(PdfPTable table) {
        PdfPCell c = new PdfPCell(new Phrase("VAN"));
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
    }

    /**
     * Add 3 GreenCardCells to the table of colspan and widthspan 1.
     * There will be 1 cell for day, month and year
     *
     * @param table table where the cells should be added to
     * @param date  date value that should be put in the cells
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

    private void addVehicleInformation(PdfPTable table) {
        Vehicle vehicle = insurance.getVehicle();
        String body = vehicle.getLicensePlate();
        if (vehicle.getLicensePlate() == null) {
            body = vehicle.getVin();
        }
        table.addCell(new GreenCardCell("Kenteken (of, indien geen kenteken) chassis- of motornummer", body, 6, 2));
        table.addCell(new GreenCardCell("Soort motorrijtuig", vehicle.getType().getType(), 3, 2));
        table.addCell(new GreenCardCell("Merk motorrijtuig", vehicle.getBrand() + " " + vehicle.getModel(), 3, 2));
    }

    private void addCoverageArea(PdfPTable table) {
        PdfPCell c = new PdfPCell(new Paragraph("DEKKINGSGEBIED"));
        c.setHorizontalAlignment(Element.ALIGN_CENTER);
        c.setColspan(12);
        table.addCell(c);
        for (String country : countries) {
            c = new PdfPCell(new Paragraph(country));
            c.setHorizontalAlignment(Element.ALIGN_CENTER);
            table.addCell(c);
        }
        int mod = countries.length % 12;
        if (mod != 0) {
            PdfPCell filler = new PdfPCell();
            filler.setColspan(12 - mod);
            table.addCell(filler);
        }
    }

    private void addCustomerInformation(PdfPTable table) {
        // Add information about the customer
        Customer customer = insurance.getContract().getCustomer();
        Address address = customer.getAddress();
        String body = customer.getName() + "\n" + address.getStreet() + " " + address.getStreetNumber()
                + "\n" + address.getPostalCode() + " " + address.getTown();
        table.addCell(new GreenCardCell("Naam en andres van verzekeringsnemer (of houder van het motorrijtuig)", body, 12, 4));
    }
}
