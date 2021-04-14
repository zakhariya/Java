package ua.lpr.printservice.model.impl.printer;

import com.itextpdf.text.BadElementException;
import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.pdf.PdfWriter;
import com.itextpdf.text.Image;
import org.apache.pdfbox.io.IOUtils;
import org.springframework.stereotype.Component;

import java.awt.print.PrinterException;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.net.URL;

@Component
public class PrinterImage extends PrinterSuper {

    private Document document;
    private Image image;

//    public void printImage(byte[] bytes) throws PrinterException {
//        Toolkit toolkit = Toolkit.getDefaultToolkit();
//        Image image = toolkit.createImage(bytes);
//
//        super.print(image);
//    }

    public void printImage(byte[] bytes) throws PrinterException, DocumentException, IOException {
        image = Image.getInstance(bytes);
        document = new Document();

        printFromImageBytesAsPdf(getName(), getBytes());
    }

    public void printImage(File file) throws PrinterException, IOException, DocumentException {
        image = Image.getInstance(file.getAbsolutePath());
        document = new Document();

        printFromImageBytesAsPdf(getName(), getBytes());
    }

    public void printImage(URL url) throws PrinterException, IOException, DocumentException {
        image = Image.getInstance(url);
        document = new Document();

        printFromImageBytesAsPdf(getName(), getBytes());



        //TODO: remove this "Saving file"
//        try{
//            URL url = new URL("https://www.filepicker.io/api/file/KW9EJhYtS6y48Whm2S6D?signature=4098f262b9dba23" +
//                    "e4766ce127353aaf4f37fde0fd726d164d944e031fd862c18&policy=eyJoYW5" +
//                    "kbGUiOiJLVzlFSmhZdFM2eTQ4V2htMlM2RCIsImV4cGlyeSI6MTUwODE0MTUwNH0=");
//            String = path = "D://download/";
//            InputStream ins = url.openStream();
//            OutputStream ous = new FileOutputStream(path);
//            final byte[] b = new byte[2048];
//            int length;
//
//            while ((length = inputStream.read(b)) != -1) {
//                ous.write(b, 0, length);
//            }
//
//            ins.close();
//            ous.close();
//        }

//        File file = File.createTempFile("test-", ".jpg", new File("D:/download/"));
//        Files.copy(url.openStream(), file.toPath(), StandardCopyOption.REPLACE_EXISTING);
    }

    private byte[] getBytes() throws DocumentException {
        ByteArrayOutputStream os = new ByteArrayOutputStream();

        PdfWriter.getInstance(document, os);
        document.open();
        scaleImage();
        document.add(image);
        document.close();

        return os.toByteArray();
    }

    private void scaleImage() {
        int indentation = 0;

        if (image.getWidth() > document.getPageSize().getWidth()) {
            float scale = ((document.getPageSize().getWidth() - document.leftMargin()
                    - document.rightMargin() - indentation) / image.getWidth()) * 100;

            image.scalePercent(scale);
        }
    }
}
