package ua.lpr.printservice.model;

import com.itextpdf.text.DocumentException;

import java.awt.print.PrinterException;
import java.io.File;
import java.io.IOException;
import java.net.URL;

public interface Printer {

    void printHtml(String printerName, String html) throws IOException, DocumentException, PrinterException;

    void printHtml(String printerName, byte[] bytes) throws IOException, DocumentException, PrinterException;

    void printHtml(String printerName, File file) throws IOException, DocumentException, PrinterException;

    void printHtml(String printerName, URL url) throws IOException, DocumentException, PrinterException;

    void printPdf(String printerName, byte[] bytes) throws IOException, PrinterException;

    void printPdf(String printerName, File file) throws IOException, PrinterException;

    void printPdf(String printerName, URL url) throws IOException, PrinterException;

    void printImage(String printerName, byte[] bytes) throws PrinterException, IOException, DocumentException;

    void printImage(String printerName, File file) throws PrinterException, IOException, DocumentException;

    void printImage(String printerName, URL url) throws PrinterException, IOException, DocumentException;
}
