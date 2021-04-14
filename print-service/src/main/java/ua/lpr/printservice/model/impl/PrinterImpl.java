package ua.lpr.printservice.model.impl;

import com.itextpdf.text.DocumentException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import ua.lpr.printservice.model.Printer;
import ua.lpr.printservice.model.impl.printer.PrinterHtml;
import ua.lpr.printservice.model.impl.printer.PrinterImage;
import ua.lpr.printservice.model.impl.printer.PrinterPdf;

import java.awt.print.PrinterException;
import java.io.File;
import java.io.IOException;
import java.net.URL;

@Component
public class PrinterImpl implements Printer {

    @Autowired
    private PrinterHtml printerHtml;

    @Autowired
    private PrinterPdf printerPdf;

    @Autowired
    private PrinterImage printerImage;

    @Override
    public void printHtml(String printerName, String html)
            throws IOException, DocumentException, PrinterException {

        printerHtml.setName(printerName);
        printerHtml.print(html);
    }

    @Override
    public void printHtml(String printerName, byte[] bytes)
            throws IOException, DocumentException, PrinterException {

        printerHtml.setName(printerName);
        printerHtml.print(bytes);
    }

    @Override
    public void printHtml(String printerName, File file)
            throws IOException, DocumentException, PrinterException {

        printerHtml.setName(printerName);
        printerHtml.print(file);
    }

    @Override
    public void printHtml(String printerName, URL url)
            throws IOException, DocumentException, PrinterException {

        printerHtml.setName(printerName);
        printerHtml.print(url);
    }

    @Override
    public void printPdf(String printerName, byte[] bytes) throws IOException, PrinterException {
        printerPdf.setName(printerName);
        printerPdf.print(bytes);
    }

    @Override
    public void printPdf(String printerName, File file) throws IOException, PrinterException {
        printerPdf.setName(printerName);
        printerPdf.print(file);
    }

    @Override
    public void printPdf(String printerName, URL url) throws IOException, PrinterException {
        printerPdf.setName(printerName);
        printerPdf.print(url);
    }

    @Override
    public void printImage(String printerName, byte[] bytes) throws PrinterException, IOException, DocumentException {
        printerImage.setName(printerName);
        printerImage.printImage(bytes);
    }

    @Override
    public void printImage(String printerName, File file) throws PrinterException, IOException, DocumentException {
        printerImage.setName(printerName);
        printerImage.printImage(file);
    }

    @Override
    public void printImage(String printerName, URL url) throws PrinterException, IOException, DocumentException {
        printerImage.setName(printerName);
        printerImage.printImage(url);
    }
}