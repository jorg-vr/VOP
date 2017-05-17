package pdf;

import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.pdf.PdfWriter;

import java.io.ByteArrayOutputStream;
import java.io.FileOutputStream;
import java.io.IOException;

/**
 * Created by Billie Devolder on 15/05/2017.
 */
public abstract class Pdf {

    private Document document;
    private ByteArrayOutputStream baos;


    protected void generatePdf() throws PdfException {
        try {
            init();
            generateDocument();
            finish();
        } catch (DocumentException e) {
            throw new PdfException();
        }
    }

    /**
     * Generate the pdf itself. Gets called after the init function.
     */
    protected abstract void generateDocument() throws DocumentException;

    /**
     * Initialize the creation of the pdf
     * @throws DocumentException
     */
    private void init() throws DocumentException {
        document = new Document();
        baos = new ByteArrayOutputStream();
        PdfWriter.getInstance(document, baos);
        document.open();
    }

    /**
     * Finish the creation of the pdf
     */
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
     * Write the pdf to a file
     *
     * @param path path of the file where the green card should be written to
     * @throws IOException could not write pdf to file
     */
    public void writeToFile(String path) throws IOException {
        FileOutputStream fos = new FileOutputStream(path);
        fos.write(baos.toByteArray());
        fos.close();
    }

    protected Document getDocument() {
        return document;
    }

    protected void setDocument(Document document) {
        this.document = document;
    }
}
