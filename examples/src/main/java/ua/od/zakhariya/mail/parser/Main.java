package ua.od.zakhariya.mail.parser;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

public class Main {
    public static void main(String[] args) throws ParseException {
        String dateString = "2021-04-22 00:00:01";

        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Date startDate = dateFormat.parse(dateString);

        FileParser parser = new FileParser();
        DataBase dataBase = new DataBase();
        List<String> fileNames = dataBase.getFileNames(dateString);
        List<String> emails = parser.getEmailsFromFiles(fileNames, startDate.getTime(), ErrorType.GMAIL);
//        dataBase.updateRecipients(emails);
        dataBase.removeRecipients(emails);

        System.out.println("Done");
    }
}
