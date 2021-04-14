package ua.lpr.printservice.service.impl;

import com.itextpdf.text.DocumentException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ua.lpr.printservice.model.PrintParams;
import ua.lpr.printservice.model.Printer;
import ua.lpr.printservice.service.PrintService;

import java.awt.print.PrinterException;
import java.io.IOException;

@Service
public class PrintServiceImpl implements PrintService {

    @Autowired
    private Printer printer;

    @Override
    public void printHtml(PrintParams printParams) throws DocumentException, PrinterException, IOException {
        String printerName = printParams.getPrinterName();

        if (printParams.getHtml() != null) {
            printer.printHtml(printerName, printParams.getHtml());
        } else if (printParams.getBytes() != null) {
            printer.printHtml(printerName, printParams.getBytes());
        } else if (printParams.getFile() != null && printParams.getFile().exists()) {
            printer.printHtml(printerName, printParams.getFile());
        } else if (printParams.getUrl() != null) {
            printer.printHtml(printerName, printParams.getUrl());
        }
    }

    @Override
    public void printPdf(PrintParams printParams) throws IOException, PrinterException {
        String printerName = printParams.getPrinterName();

        if (printParams.getBytes() != null) {
            printer.printPdf(printerName, printParams.getBytes());
        } else if (printParams.getFile() != null && printParams.getFile().exists()) {
            printer.printPdf(printerName, printParams.getFile());
        } else if (printParams.getUrl() != null) {
            printer.printPdf(printerName, printParams.getUrl());
        }
    }

    @Override
    public void printImage(PrintParams printParams) throws PrinterException, IOException, DocumentException {
        String printerName = printParams.getPrinterName();

        if (printParams.getBytes() != null) {
            printer.printImage(printerName, printParams.getBytes());
        } else if (printParams.getFile() != null && printParams.getFile().exists()) {
            printer.printImage(printerName, printParams.getFile());
        } else if (printParams.getUrl() != null) {
            printer.printImage(printerName, printParams.getUrl());
        }
    }
}
