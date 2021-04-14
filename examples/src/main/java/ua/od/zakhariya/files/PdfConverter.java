package ua.od.zakhariya.files;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.Writer;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import javax.xml.parsers.ParserConfigurationException;

import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.encryption.InvalidPasswordException;
import org.apache.pdfbox.rendering.ImageType;
import org.apache.pdfbox.rendering.PDFRenderer;
import org.apache.pdfbox.tools.imageio.ImageIOUtil;
import org.fit.pdfdom.PDFDomTree;

import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Image;
import com.itextpdf.text.PageSize;
import com.itextpdf.text.pdf.PdfWriter;
import com.itextpdf.tool.xml.XMLWorkerHelper;

public class PdfConverter {

    public static void main(String... args) throws DocumentException, IOException  {
//		convertHTMLFromPDF("d:\\test2.pdf");
//		convertPDFFromHTML("d:\\test.html");
//		convertFromImageToPdf();

        System.out.println("Done.");
    }


    private static void pdfToHtml(String filename)
            throws InvalidPasswordException, IOException, ParserConfigurationException {

        PDDocument pdf = PDDocument.load(new File(filename));
        Writer output = new PrintWriter("d:\\new_pdf.html", "utf-8");
        new PDFDomTree().writeText(pdf, output);

        output.close();
    }

    private static void htmlToPdf(String filename) throws DocumentException, IOException {

        Document document = new Document();

        PdfWriter writer = PdfWriter.getInstance(document, new FileOutputStream("d:\\new_html.pdf"));
        document.open();

        XMLWorkerHelper.getInstance().parseXHtml(writer, document, new FileInputStream(filename));
        document.close();
    }

    private void pdfToImage(String filename, String extension) throws InvalidPasswordException, IOException {
        PDDocument document = PDDocument.load(new File(filename));
        PDFRenderer pdfRenderer = new PDFRenderer(document);

        for (int page = 0; page < document.getNumberOfPages(); ++page) {
            BufferedImage bim = pdfRenderer.renderImageWithDPI(page, 300, ImageType.RGB);
            ImageIOUtil.writeImage(bim, String.format("src/output/pdf-%d.%s", page + 1, extension), 300);
        }

        document.close();
    }

    private static void imageToPdf(String filename, String extension)
            throws DocumentException, MalformedURLException, IOException {

        Document document = new Document();
        String input = filename + "." + extension;
        String output = "src/output/" + extension + ".pdf";
        FileOutputStream fos = new FileOutputStream(output);

        PdfWriter writer = PdfWriter.getInstance(document, fos);
        writer.open();
        document.open();
        document.add(Image.getInstance((new URL(input))));
        document.close();
        writer.close();
    }

    private static void imageToPdf() throws DocumentException, MalformedURLException, IOException {
        String outputFile = "d:\\image.pdf";

        List<String> files = new ArrayList<String>();
        files.add("d:\\test_2.jpg");
//        files.add("page2.jpg");

        Document document = new Document();
        PdfWriter.getInstance(document, new FileOutputStream(outputFile));
        document.open();

        for (String f : files) {
            document.newPage();
            Image image = Image.getInstance(new File(f).getAbsolutePath());
//            image.setAbsolutePosition(0, 0);
//            image.setBorderWidth(0);
//            image.scaleAbsolute(PageSize.A4);
//            image.scalePercent(50f);
//            image.scaleToFit(100f, 100f);

            //if you would have a chapter indentation
            int indentation = 0;
            if (image.getWidth() > document.getPageSize().getWidth()) {
                float scaler = ((document.getPageSize().getWidth() - document.leftMargin()
                        - document.rightMargin() - indentation) / image.getWidth()) * 100;

                image.scalePercent(scaler);
            }

            document.add(image);
        }
        document.close();
    }
}