package ua.lpr.util.parser;

import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.apache.poi.openxml4j.opc.OPCPackage;
//import org.apache.poi.poifs.filesystem.NPOIFSFileSystem;
import org.apache.poi.poifs.filesystem.POIFSFileSystem;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.CellType;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.streaming.SXSSFWorkbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.slf4j.Logger;
import ua.lpr.controller.Controller;
import ua.lpr.entity.Recipient;

import javax.swing.*;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.ParseException;
import java.util.Iterator;
import java.util.List;

import static org.slf4j.LoggerFactory.getLogger;

public class XlsParser {
    private final Controller controller;

    private static final Logger log = getLogger(XlsParser.class);

    public XlsParser(Controller controller) {
        this.controller = controller;

    }

    public void parseToDatabase(File file) {
        Workbook wb = null;

        try {
            String suffix = file.getName()
                    .substring(file.getName().lastIndexOf(".") + 1, file.getName().length());

            if (suffix.equals("xls")) {
                wb = new HSSFWorkbook(new POIFSFileSystem(file).getRoot(), true);
                //Biff8EncryptionKey.setCurrentUserPassword("123");
            } else if (suffix.equals("xlsx")) {
                wb = new XSSFWorkbook(OPCPackage.open(file));
            } else {
                return;
            }

            int sheetsCount = wb.getNumberOfSheets();

            System.out.println("Sheets count - " + sheetsCount);


            for (int i = 0; i < sheetsCount; i++) {
                parseToDatabase(wb.getSheetAt(i));
            }

            wb.close();
        } catch (OutOfMemoryError e) {
            log.error(e.getLocalizedMessage(), e);
            JOptionPane.showMessageDialog(null, e.getMessage(), "Error!", JOptionPane.ERROR_MESSAGE);
            controller.exit();
        } catch (InvalidFormatException | IOException e) {
            log.error(e.getLocalizedMessage(), e);
            return;
        }
    }

    public void writeToFile(List<Recipient> recipients, File file) {
        Workbook wb = new SXSSFWorkbook(100);
        Sheet sheet = wb.createSheet("Полиграфисты");

        int rowIdx = 0;

        for (Recipient recipient : recipients) {

            Row row = sheet.createRow(rowIdx);
            int colIdx = 0;
            while(colIdx <= 4) {
                Cell cell = row.createCell(colIdx);
//                cell.setCellStyle(style);

                try {
                    cell.setCellType(CellType.STRING);

                    String value = "";

                    if (colIdx == 0) {
                        value = recipient.getEmail();
                    } else if (colIdx == 1) {
                        value = recipient.getName();
                    } else if (colIdx == 2) {
                        value = recipient.getCompany();
                    } else if (colIdx == 3) {
                        value = recipient.getCity();
                    }

                    cell.setCellValue(String.valueOf(value));
                } catch (NumberFormatException ex) {
                    ex.printStackTrace();
                }


                colIdx++;
            }

            rowIdx++;

        }

        try {
            FileOutputStream fos = new FileOutputStream(file);
            wb.write(fos);
            fos.close();
            wb.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void parseToDatabase(Sheet sheet) {
        Iterator<Row> rows = sheet.iterator();
        int rowsCount = sheet.getLastRowNum(); //sheet.getPhysicalNumberOfRows();

        //need to skip a header
//        rows.next();

        while (rows.hasNext()) {
            final Row row = rows.next();

            String name = null;
            String company = null;
            String city = null;
            String email = null;

            try {
                name = row.getCell(1).getStringCellValue();
            } catch (NullPointerException e) {
            }

            try {
                company = row.getCell(2).getStringCellValue();
            } catch (NullPointerException e) {
            }

            try {
                city = row.getCell(3).getStringCellValue();
            } catch (NullPointerException e) {
            }

            try {
                email = row.getCell(0).getStringCellValue();
            } catch (NullPointerException e) {
            }

            if (email == null || email.length() < 5) continue;

            Recipient recipient =
                    new Recipient(name, company, city, email);

            try {
                Thread.sleep(50);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

            controller.saveRecipient(recipient);
        }
    }


}
