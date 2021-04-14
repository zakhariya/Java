package ua.lpr.printservice.model.impl.printer;

import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.pdf.PdfWriter;
import com.itextpdf.tool.xml.XMLWorkerHelper;
import org.springframework.stereotype.Component;

import java.awt.print.PrinterException;
import java.io.*;
import java.net.URL;
import java.net.URLConnection;

@Component
public class PrinterHtml extends PrinterSuper {

    public void print(String html) throws DocumentException, IOException, PrinterException {
        Document document = new Document();

        ByteArrayOutputStream os = new ByteArrayOutputStream();

        PdfWriter writer = PdfWriter.getInstance(document, os);
        document.open();

        XMLWorkerHelper.getInstance().parseXHtml(writer, document, new ByteArrayInputStream(html.getBytes()));
        document.close();

        printFromHtmlBytesAsPdf(getName(), os.toByteArray());
    }

    public void print(byte[] bytes) throws DocumentException, IOException, PrinterException {
        Document document = new Document();

        ByteArrayOutputStream os = new ByteArrayOutputStream();

        PdfWriter writer = PdfWriter.getInstance(document, os);
        document.open();

        XMLWorkerHelper.getInstance().parseXHtml(writer, document, new ByteArrayInputStream(bytes));
        document.close();

        printFromHtmlBytesAsPdf(getName(), os.toByteArray());
    }

    public void print(File file) throws DocumentException, IOException, PrinterException {
        Document document = new Document();

        ByteArrayOutputStream os = new ByteArrayOutputStream();

        PdfWriter writer = PdfWriter.getInstance(document, os);
        document.open();

        XMLWorkerHelper.getInstance().parseXHtml(writer, document, new FileInputStream(file));
        document.close();

        printFromHtmlBytesAsPdf(getName(), os.toByteArray());
    }

    public void print(URL url) throws DocumentException, IOException, PrinterException {
        Document document = new Document();

        ByteArrayOutputStream os = new ByteArrayOutputStream();

        PdfWriter writer = PdfWriter.getInstance(document, os);
        document.open();

        URLConnection con = url.openConnection();
        Reader reader = new InputStreamReader(con.getInputStream());

//        XMLWorkerHelper.getInstance().parseXHtml(writer, document, url.openStream());
        XMLWorkerHelper.getInstance().parseXHtml(writer, document, reader);
        document.close();

        printFromHtmlBytesAsPdf(getName(), os.toByteArray());
    }
}
