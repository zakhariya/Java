package ua.od.zakhariya.mail.parser;

import org.apache.commons.io.FileUtils;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class FileParser {
    private final String mailFolder = "C:\\Program Files (x86)\\hMailServer\\Data\\e.lpr.ua\\info\\";

    public List<String> getEmailsFromFiles(List<String> fileNames, long startDate, ErrorType type) {
        List<String> emails = new ArrayList();
        List<File> files = getFiles(startDate);

        System.out.println("File names count: " + fileNames.size() + System.lineSeparator() + "Files count: " + files.size());

        for (String fileName : fileNames) {
            for (File file : files) {
                if (file.getName().equals(fileName)) {
                    if (type == ErrorType.SMTP) {
                        emails.add(findEmailIfErrorSmtp(file));
                    } else if (type == ErrorType.GMAIL) {
                        emails.add(findEmailIfErrorGmail(file));
                    }
                }
            }
        }

        System.out.println("Found emails: " + emails.size());

        return emails;
    }

    private String findEmailIfErrorGmail(File file) {
        try {
            String data = FileUtils.readFileToString(file, "UTF-8");

            if (data.contains("Delivery Status Notification (Failure)")) {
                Matcher m = Pattern.compile("[a-zA-Z0-9_.+-]+@[a-zA-Z0-9-]+\\.[a-zA-Z0-9-.]+")
                        .matcher(data);

                while (m.find()) {
                    String s = m.group();
//                    System.out.println(s);
                    if (!s.contains("lpr.ua")
                            && !s.contains("google.com") && !s.contains("mailer-daemon")) {
                        return s;
                    }
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        return "";
    }

    private String findEmailIfErrorSmtp(File file) {
        try {
            String data = FileUtils.readFileToString(file, "UTF-8");

            if (data.contains("Error Type: SMTP")) {
                Matcher m = Pattern.compile("[a-zA-Z0-9_.+-]+@[a-zA-Z0-9-]+\\.[a-zA-Z0-9-.]+")
                        .matcher(data);

                while (m.find()) {
                    String s = m.group();
                    if (!s.contains("lpr.ua")) {
                        return s;
                    }
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        return "";
    }

    private List<File> getFiles(long startDate) {
        try (Stream<Path> walk = Files.walk(Paths.get(mailFolder))) {

            return walk.filter(Files::isRegularFile)
                    .map(x -> new File(x.toString()))
                    .filter(file -> file.lastModified() >= startDate)
                    .collect(Collectors.toList());
        } catch (IOException e) {
            e.printStackTrace();
        }

        return Collections.EMPTY_LIST;
    }
}
