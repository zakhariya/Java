package ua.od.zakhariya.locale;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Calendar;
import java.util.Date;

public class CurrentDateTime {

    private static final DateFormat sdf = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
    private static final DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy/MM/dd HH:mm:ss");

    public static void main(String[] args) {

        Date date = new Date();
        System.out.println("Formatted date: " + sdf.format(date));

        Calendar cal = Calendar.getInstance();
        System.out.println("Formatted calendar: " + sdf.format(cal.getTime()));

        LocalDateTime now = LocalDateTime.now();
        System.out.println("Formatted local time: " + dtf.format(now));

        LocalDate localDate = LocalDate.now();
        System.out.println("Formatted local date: " + DateTimeFormatter.ofPattern("yyy/MM/dd").format(localDate));

    }
}