package ua.lpr.printservice.model.impl.printer;

import org.apache.pdfbox.pdmodel.PDDocument;
import org.springframework.stereotype.Component;

import java.awt.print.PrinterException;
import java.io.File;
import java.io.IOException;
import java.net.URL;

@Component
public class PrinterPdf extends PrinterSuper {

    public void print(byte[] bytes) throws IOException, PrinterException {
        super.print(PDDocument.load(bytes));
    }

    public void print(File file) throws IOException, PrinterException {
        super.print(PDDocument.load(file));
    }

    public void print(URL url) throws IOException, PrinterException {
        super.print(PDDocument.load(url.openStream()));
    }
}
