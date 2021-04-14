package ua.lpr.printservice.service;

import com.itextpdf.text.DocumentException;
import ua.lpr.printservice.model.PrintParams;

import java.awt.print.PrinterException;
import java.io.IOException;

public interface PrintService {

    void printHtml(PrintParams printParams) throws DocumentException, PrinterException, IOException;

    void printPdf (PrintParams printParams) throws IOException, PrinterException;

    void printImage (PrintParams printParams) throws PrinterException, IOException, DocumentException;
}
