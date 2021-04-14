package ua.lpr.printservice.model.impl.printer;

import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.printing.PDFPageable;

import javax.print.PrintService;
import javax.print.PrintServiceLookup;
import com.itextpdf.text.Image;
import java.awt.print.PageFormat;
import java.awt.print.Printable;
import java.awt.print.PrinterException;
import java.awt.print.PrinterJob;
import java.io.IOException;

public class PrinterSuper {

    private String name;

    protected void print(Object document) throws PrinterException {
        PrinterJob job = PrinterJob.getPrinterJob();

        job.setPrintService(findPrintService(name));

        if (document instanceof PDDocument) {
            job.setPageable(new PDFPageable((PDDocument) document));
        }
//        } else if (document instanceof Image) {
//            Image image = (Image) document;
//
//
//
////            job.setPrintable(new Printable() {
////                public int print(Graphics graphics, PageFormat pageFormat, int pageIndex) throws PrinterException {
////                    if (pageIndex != 0) {
////                        return NO_SUCH_PAGE;
////                    }
////                    System.out.println(image.getWidth(null) + " " + image.getHeight(null));
////                    graphics.drawImage(image, 0, 0, image.getWidth(null), image.getHeight(null), null);
////
////                    return PAGE_EXISTS;
////                }
////            });
//        }

        job.print();
    }

    protected PrintService findPrintService(String printerName) {
        PrintService[] printServices = PrintServiceLookup.lookupPrintServices(null, null);

        for (PrintService printService : printServices) {
            if (printService.getName().trim().equals(printerName)) {
                return printService;
            }
        }
        return PrintServiceLookup.lookupDefaultPrintService();
    }

    protected void printFromHtmlBytesAsPdf(String printerName, byte[] bytes) throws IOException, PrinterException {
        PrinterPdf printerPdf = new PrinterPdf();
        printerPdf.setName(printerName);
        printerPdf.print(bytes);
    }

    protected void printFromImageBytesAsPdf(String printerName, byte[] bytes) throws IOException, PrinterException {
        PrinterPdf printerPdf = new PrinterPdf();
        printerPdf.setName(printerName);
        printerPdf.print(bytes);
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
