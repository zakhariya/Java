package ua.od.zakhariya.other;

import java.io.*;
import javax.print.*;
import javax.print.attribute.*;
import javax.print.attribute.standard.*;

public class Printer2 {

    public FileInputStream psStream = null;

    public Printer2(String htmlFileName) throws IOException {

        try {
            psStream = new FileInputStream(htmlFileName);
        } catch (FileNotFoundException ffne) {
            return;
        }

        DocFlavor psInFormat = new DocFlavor("application/octet-stream","java.io.InputStream");

        Doc myDoc = new SimpleDoc(psStream, psInFormat, null);
        PrintRequestAttributeSet aset =	new HashPrintRequestAttributeSet();

        aset.add(new Copies(1));
        aset.add(OrientationRequested.PORTRAIT);
        aset.add(new JobName("Impression",null));

        PrintService[] services = PrintServiceLookup.lookupPrintServices(null, null);
        DocPrintJob job = services[0].createPrintJob();

        try {
            job.print(myDoc, aset);

            try {

                Thread.sleep(3000);

            } catch ( Exception ex ) {
                System.err.println("Error sleeping: " + ex );
            }

            System.out.println("HERE!!!!!!!!");
        } catch(PrintException pe) {
            System.out.print("ERROR!!!!---> " + pe);
        }
    }

    public static void main(String args[]) {
        try {
            Printer2 tup = new Printer2("d:\\test.html");
        } catch (IOException ioe) {
            System.out.println("IO ERR " + ioe);
        }
    }
}
