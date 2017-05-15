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

    public Pdf() {
        try {
            init();
            generatePdf();
            finish();
        } catch (DocumentException e) {
            throw new PdfException();
        }
    }

    protected abstract void generatePdf() throws DocumentException;

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

    public Document getDocument() {
        return document;
    }

    public void setDocument(Document document) {
        this.document = document;
    }

}
