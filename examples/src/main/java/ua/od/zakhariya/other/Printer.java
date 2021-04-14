package ua.od.zakhariya.other;

import java.awt.*;
import java.awt.print.*;
import java.io.* ;
import java.util.Locale;
import javax.print.Doc;
import javax.print.DocFlavor;
import javax.print.DocPrintJob;
import javax.print.PrintException;
import javax.print.PrintService;
import javax.print.PrintServiceLookup;
import javax.print.ServiceUI;
import javax.print.SimpleDoc;
import javax.print.attribute.DocAttributeSet;
import javax.print.attribute.HashDocAttributeSet;
import javax.print.attribute.HashPrintRequestAttributeSet;
import javax.print.attribute.PrintRequestAttributeSet;
import javax.print.attribute.standard.Copies;
import javax.print.attribute.standard.JobName;
import javax.print.attribute.standard.OrientationRequested;

import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.encryption.InvalidPasswordException;
import org.apache.pdfbox.printing.PDFPageable;

import org.xhtmlrenderer.pdf.ITextRenderer;

import com.itextpdf.text.Document;
import com.itextpdf.text.pdf.PdfWriter;
import com.itextpdf.tool.xml.XMLWorkerHelper;
import com.lowagie.text.DocumentException;

public class Printer {

    public static void main(String[] args) throws com.itextpdf.text.DocumentException{
        System.out.println(getBytes());
//		one();
//		two();
//		three();
//		four();
        printImage();
//		printHtmlFromString();

        System.out.println("Done!");
    }

    public static byte[] getBytes() {

        return null;
    }

    public static void one() {
        System.out.println();
        PrintRequestAttributeSet pras = new HashPrintRequestAttributeSet();
        DocFlavor flavor = DocFlavor.INPUT_STREAM.AUTOSENSE;
        PrintService printService[] = PrintServiceLookup.lookupPrintServices(flavor, pras);
        PrintService defaultService = PrintServiceLookup.lookupDefaultPrintService();
        PrintService service = ServiceUI.printDialog(GraphicsEnvironment.getLocalGraphicsEnvironment().getDefaultScreenDevice().getDefaultConfiguration(), 200, 200,
                printService, defaultService, flavor, pras);
        if (service != null) {
            DocPrintJob job = service.createPrintJob();
            FileInputStream fis;
            try {
                fis = new FileInputStream(new File("d:\\Обеды.xlsx"));
                DocAttributeSet das = new HashDocAttributeSet();
                Doc document = new SimpleDoc(fis, flavor, das);
                job.print(document, pras);
            } catch (FileNotFoundException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            } catch (PrintException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
        }
    }

    public static void two() {
        try {
            Desktop.getDesktop().print(new File("d:\\Обеды.xlsx"));
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }

    public static void three() {
        String filePath = "d:\\Обеды.xlsx";

        PrinterJob pj = PrinterJob.getPrinterJob();

        PageFormat pf = pj.pageDialog(pj.defaultPage());

        pj.setPrintable(new FilePagePainter(filePath), pf);

        if (pj.printDialog()){
            try{
                pj.print();
            }catch(PrinterException e){}
            System.exit(0);

        }
    }

    public static void four() {
        String html = "https://api.lpr.ua/";
        String pdf = "d:\\Test.pdf";

        Printer f = new Printer();
        try {
            f.printPdf(html, pdf);
            f.print(null, pdf);
        } catch (DocumentException | IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (PrintException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }

    public static void printImage() {
//		String printerName = "\\\\192.168.0.40\\EPSON L355 Series";
        String printerName = "EPSON L805 Series";
        File file = new File("d:\\test.jpg");
//		File file = new File("d:\\test.html");

        String html = "<html>\r\n" +
                "	<head>\r\n" +
                "		<style>\r\n" +
                "			table {\r\n" +
                "				text-align: center;\r\n" +
                "				margin: auto;\r\n" +
                "				width: max-content;\r\n" +
                "			}\r\n" +
                "		</style>\r\n" +
                "	</head>\r\n" +
                "	<body>\r\n" +
                "		<table border=1>\r\n" +
                "			<tr>\r\n" +
                "				<td>Test</td>\r\n" +
                "				<td>Of html output</td>\r\n" +
                "			</tr>\r\n" +
                "		</table>\r\n" +
                "	</body>\r\n" +
                "</html>";

        PrintService[] printServices = PrintServiceLookup.lookupPrintServices(null, null);
        PrintService printService =	PrintServiceLookup.lookupDefaultPrintService();

        System.out.println("List of printers:");

        for(PrintService service : printServices) {
            System.out.println(service.getName());
        }

        System.out.println("---------------");

        for(PrintService service : printServices) {
            if (service.getName().equals(printerName)) {
                printService = service;

                break;
            }
        }

        System.err.println("\nPrinter name = " + printService.getName() + "\n");

        DocPrintJob job = printService.createPrintJob();

        System.out.println(job);

        DocFlavor[] docFlavors = printService.getSupportedDocFlavors();

        DocFlavor testFlavor = new DocFlavor("image/jpeg", "java.io.InputStream");
//		DocFlavor testFlavor = new DocFlavor("text/html; charset=utf-8", "java.lang.String");
//		DocFlavor testFlavor = DocFlavor.STRING.TEXT_HTML;

        System.out.println("List of flavors:");

        for (int i = 0; i < docFlavors.length; i++) {
            if (docFlavors[i].equals(testFlavor)) {
                System.err.println(docFlavors[i]);

                continue;
            }

            System.out.println(docFlavors[i].toString());
        }

        System.out.println("-----------");

        DocAttributeSet docAttributes =	new HashDocAttributeSet();
        docAttributes.add(OrientationRequested.PORTRAIT);

        PrintRequestAttributeSet printAttributes = new HashPrintRequestAttributeSet();
        printAttributes.add(new Copies(1));
        printAttributes.add(new JobName("Joooobbbb", Locale.UK));

        Doc doc = null;

        try {
            doc = new SimpleDoc(new FileInputStream(file),
                    testFlavor,
                    docAttributes);

//			doc = new SimpleDoc(html, testFlavor, docAttributes);

            job.print(doc, printAttributes);
        } catch (FileNotFoundException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (PrintException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }


    }

    public static void six() {
        try {
            PDDocument document = PDDocument.load(new File("d:\\test2.pdf"));
            PrintService myPrintService = findPrintService("EPSON L805 Series");

            PrinterJob job = PrinterJob.getPrinterJob();
            job.setPageable(new PDFPageable(document));
            job.setPrintService(myPrintService);
            job.print();
        } catch (InvalidPasswordException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (PrinterException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }

    public static void printHtmlFromString() throws com.itextpdf.text.DocumentException {
        try {
            String printerName = "\\\\192.168.0.40\\EPSON L355 Series";
//			String printerName = "EPSON L805 Series";

            String html = "<!DOCTYPE html>\r\n" +
                    "<html>\r\n" +
                    "<head>\r\n" +
                    "    <title>HTML -> PDF</title>\r\n" +
                    "    <meta charset=\"utf-8\" />\r\n" +
                    "    <link href=\"https://lpr.ua/custom-css/style.css\" rel=\"stylesheet\" type=\"text/css\" />\r\n" +
                    "	<!-- -->\r\n" +
                    "	<style>\r\n" +
                    "		h1 {\r\n" +
                    "			color:#ccc;\r\n" +
                    "		}\r\n" +
                    "		table tr td{\r\n" +
                    "			text-align:center;\r\n" +
                    "			border:1px solid gray;\r\n" +
                    "			padding:4px;\r\n" +
                    "		}\r\n" +
                    "		table tr th{\r\n" +
                    "			background-color:#84C7FD;\r\n" +
                    "			color:#fff;\r\n" +
                    "			width: 100px;\r\n" +
                    "		}\r\n" +
                    "		.itext{\r\n" +
                    "			color:#84C7FD;\r\n" +
                    "			font-weight:bold;\r\n" +
                    "		}\r\n" +
                    "		.description{\r\n" +
                    "			color:gray;\r\n" +
                    "		}\r\n" +
                    "	</style>\r\n" +
                    "</head>\r\n" +
                    "<body>\r\n" +
                    "<h1>HTML -> PDF</h1>\r\n" +
                    "<p>\r\n" +
                    "    <span class=\"itext\">itext</span> <span class=\"description\"> HTML -> PDFFFFF</span>\r\n" +
                    "</p>\r\n" +
                    "<table>\r\n" +
                    "    <tr>\r\n" +
                    "        <th class=\"label\">Title</th>\r\n" +
                    "        <td>iText - Java HTML -> PDF</td>\r\n" +
                    "    </tr>\r\n" +
                    "    <tr>\r\n" +
                    "        <th>URL</th>\r\n" +
                    "        <td>https://devcolibri.com/kraaaa</td>\r\n" +
                    "    </tr>\r\n" +
                    "    <tr>\r\n" +
                    "        <th>Та ну</th>\r\n" +
                    "        <td>такие дожди</td>\r\n" +
                    "    </tr>\r\n" +
                    "</table>\r\n" +
                    "</body>\r\n" +
                    "</html>";

            Document document = new Document();

            ByteArrayOutputStream os = new ByteArrayOutputStream();

            PdfWriter writer = PdfWriter.getInstance(document, os); //new FileOutputStream("d:\\newest_html.pdf")
            document.open();

            XMLWorkerHelper.getInstance().parseXHtml(writer, document, new ByteArrayInputStream(html.getBytes())); // file
            document.close();

//		    File file = new File

//		    InputStream is = new ByteArrayInputStream(null);

            PDDocument pdfDocument = PDDocument.load(os.toByteArray()); // file
            PrintService myPrintService = findPrintService(printerName);

            PrinterJob job = PrinterJob.getPrinterJob();

            job.setPageable(new PDFPageable(pdfDocument));
            job.setPrintService(myPrintService);
            job.print();

        } catch (InvalidPasswordException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (PrinterException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }

    private static PrintService findPrintService(String printerName) {
        PrintService[] printServices = PrintServiceLookup.lookupPrintServices(null, null);

        for (PrintService printService : printServices) {
            if (printService.getName().trim().equals(printerName)) {
                return printService;
            }
        }
        return PrintServiceLookup.lookupDefaultPrintService();
    }

    public void printPdf(String html, String pdf) throws DocumentException, IOException {
        String url = new File(html).toURI().toURL().toString();
        OutputStream os = new FileOutputStream(pdf);

        ITextRenderer renderer = new ITextRenderer();
        renderer.setDocument(url);
        renderer.layout();
        renderer.createPDF(os);

        os.close();
    }

    public void print(File file, String pdf) throws FileNotFoundException, PrintException {
//	    PrinterService ps = new PrinterService();
        // get the printer service by printer name
        PrintService pss = PrintServiceLookup.lookupDefaultPrintService();// ps.getCheckPrintService("Samsung ML-2850 Series PCL6 Class Driver");
        System.out.println("Printer - " + pss.getName());
        DocPrintJob job = pss.createPrintJob();
        DocAttributeSet das = new HashDocAttributeSet();
        Doc document = new SimpleDoc(new FileInputStream(new File(pdf)), DocFlavor.INPUT_STREAM.AUTOSENSE, das);
        // new htmldo
        PrintRequestAttributeSet pras = new HashPrintRequestAttributeSet();
        job.print(document, pras);
    }


    static class FilePagePainter implements Printable{

        private BufferedReader br;
        private String file;
        private int page = -1;
        private boolean eof;
        private String[] line;
        private int numLines;

        public FilePagePainter(String file){

            this.file = file;

            try{
                br = new BufferedReader(new FileReader(file));
            }catch(IOException e){
                eof = true;
            }
        }


        @Override
        public int print(Graphics g, PageFormat pf, int ind)throws PrinterException{

            g.setColor(Color.black);
            g.setFont(new Font("Times new roman", Font.PLAIN, 10));

            int h = (int)pf.getImageableHeight();
            int x = (int)pf.getImageableX() + 10;
            int у = (int)pf.getImageableY() + 12;

            try{
                // Если система печати запросила эту страницу первый раз
                if (ind != page){

                    if (eof) {
                        return Printable.NO_SUCH_PAGE;
                    }

                    page = ind;
                    line = new String[h/12];           // Массив строк на странице
                    numLines =0;                       // Число строк на странице

                    // Читаем строки из файла и формируем массив строк
                    while (у + 48 < pf.getImageableY() + h){
                        line[numLines] = br.readLine();

                        if (line[numLines] == null){

                            eof = true; break;
                        }

                        numLines++;
                        у+= 12;
                    }

                }

                // Размещаем колонтитул

                у = (int)pf.getImageableY() + 12;
                g.drawString("Файл: " + file + ", страница " +
                        (ind + 1), x, у);

                // Оставляем две пустые строки
                у += 36;

                // Размещаем строки текста текущей страницы
                for (int i = 0; i < numLines; i++){
                    g.drawString(line[i], x, у) ;
                    у += 12;
                }

                return Printable.PAGE_EXISTS;

            }catch(IOException e){

                return Printable.NO_SUCH_PAGE;

            }

        }
    }
}