package ua.od.zakhariya.locale;

import java.time.Duration;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.Period;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
import java.util.Locale;

public class Java8 {

    public static void main(String[] args) {
        Java8 main = new Java8();

        main.localDate();
        System.out.println("---");

        main.localTime();
        System.out.println("---");

        main.localDateTime();
        System.out.println("---");

        main.four();
        System.out.println("---");

        main.five();
        System.out.println("---");

        main.six();
        System.out.println("---");

    }

    private void localDate() {
        LocalDate now = LocalDate.now();
        LocalDate _2017 = LocalDate.of(2017, 9, 23);
        boolean after = now.isAfter(_2017);// true
        boolean before = now.isBefore(_2017);// false

        System.out.println("Now is after 2017: " + after);
        System.out.println("Now is before 2017: " + before);
    }

    private void localTime() {
        LocalTime now = LocalTime.now();
        LocalTime _2HoursAfter = now.plusHours(2);
        boolean after = now.isAfter(_2HoursAfter); // false
        boolean before = now.isBefore(_2HoursAfter); // true

        System.out.println("Now is after +2 hours: " + after);
        System.out.println("Now is before +2 hours: " + before);
    }

    private void localDateTime() {
        LocalDateTime now = LocalDateTime.now();
        LocalDateTime monthAgo = now.minusMonths(1);
        boolean after = now.isAfter(monthAgo); // true
        boolean before = now.isBefore(monthAgo); // false

        System.out.println("Now is after month ago: " + after);
        System.out.println("Now is before month ago: " + before);
    }

    private void four() {
        LocalDate localDate = LocalDate.now();
        LocalDate tomorrow = LocalDate.now().plusDays(1);
        int isDateAfter = localDate.compareTo(tomorrow); // false (-1)

        LocalTime localTime = LocalTime.now();
        LocalTime hourLater = localTime.plusHours(1);
        int isTimeBefore = localTime.compareTo(hourLater); // true (-1)


        LocalDateTime localDateTime = LocalDateTime.now();
        LocalDateTime lastYear = localDateTime.minusYears(1);
        int isDateTimeAfter = localDateTime.compareTo(lastYear); // true (1)

        System.out.println("Is now compare tomorrow: " + isDateAfter);
        System.out.println("Is now compare +1 hour: " + isTimeBefore);
        System.out.println("Is now compare -1 year: " + isDateTimeAfter);
    }

    private void five() {
        LocalDate now = LocalDate.now(); // 2018-01-21
        LocalDate plus2Days = now.plusDays(2); // 2018-01-23
        LocalDate plusWeek = now.plusWeeks(1); // 2018-01-28
        LocalDate plus3Months = now.plusMonths(3); // 2018-04-21
        LocalDate plusPeriod = now.plus(Period.ofDays(3)); // 2018-01-24
        LocalDate plusMillennia = now.plus(1, ChronoUnit.MILLENNIA); // 3018-01-21
    }

    private void six() {
        LocalDate now = LocalDate.now(); // 2018-01-21
        LocalDate minusDays = now.minusDays(3); // 2018-01-18
        LocalDate minusWeeks = now.minusWeeks(2); // 2018-01-07
        LocalDate minusMonths = now.minusMonths(4); // 2017-09-21
        LocalDate minusPeriod = now.minus(Period.ofDays(1)); // 2018-01-20
        LocalDate minusEras = now.minus(1, ChronoUnit.CENTURIES); // 1918-01-21
    }

    private void seven() {
        LocalDate now = LocalDate.now(); // 2018-01-21
        LocalDate plusNegative = now.plusDays(-1); // 2018-01-20
        LocalDate minusNegative = now.minusDays(-1); // 2018-01-22
    }

    private void eight() {
        LocalTime now = LocalTime.now(); // 08:49:39.678703
        LocalTime plusNanos = now.plusNanos(100_000); // 08:49:39.678803
        LocalTime plusSeconds = now.plusSeconds(20); // 08:49:59.678703
        LocalTime plusMinutes = now.plusMinutes(20); // 09:09:39.678703
        LocalTime plusHours = now.plusHours(6); // 14:51:58.601216
        LocalTime plusMillis = now.plus(Duration.ofMillis(100)); // 08:49:39.778703
        LocalTime plusHalfDay = now.plus(1, ChronoUnit.HALF_DAYS); // 20:49:39.678703
    }

    private void nine() {
        LocalTime now = LocalTime.now(); // 08:57:19.743004
        LocalTime minusNanos = now.minusNanos(100_000); // 08:57:19.742904
        LocalTime minusSeconds = now.minusSeconds(20); // 08:56:59.743004
        LocalTime minusMinutes = now.minusMinutes(20); // 08:37:19.743004
        LocalTime minusHours = now.minusHours(6); // 02:57:19.743004
        LocalTime minusMillis = now.minus(Duration.ofMillis(100)); // 08:57:19.643004
        LocalTime minusHalfDay = now.minus(1, ChronoUnit.HALF_DAYS); // 20:57:19.743004
    }

    private void ten() {
        LocalDateTime now = LocalDateTime.now(); // 2018-01-21T09:11:48.486298
        LocalDateTime minusNanos = now.plusNanos(780_000_000); // 2018-01-21T09:11:49.266298
        LocalDateTime minusSeconds = now.plusSeconds(59); // 2018-01-21T09:12:47.486298
        LocalDateTime minusMinutes = now.plusMinutes(5); // 2018-01-21T09:16:48.486298
        LocalDateTime minusHours = now.plusHours(3); // 2018-01-21T12:11:48.486298
        LocalDateTime minusDays = now.plusDays(7); // 2018-01-28T09:11:48.486298
        LocalDateTime minusWeeks = now.plusWeeks(3); // 2018-02-11T09:11:48.486298
        LocalDateTime minusMonths = now.plusMonths(5); // 2018-06-21T09:11:48.486298
        LocalDateTime minusYears = now.plusYears(2); // 2020-01-21T09:11:48.486298
        LocalDateTime minusPeriod = now.plus(Period.ofWeeks(2)); // 2018-02-04T09:11:48.486298
        LocalDateTime minusDecades = now.plus(1, ChronoUnit.DECADES); // 2028-01-21T09:11:48.486298
    }

    private void eleven() {
        LocalDateTime now = LocalDateTime.now(); // 2018-01-21T09:09:33.650251
        LocalDateTime minusNanos = now.minusNanos(780_000_000); // 2018-01-21T09:09:32.870251
        LocalDateTime minusSeconds = now.minusSeconds(59); // 2018-01-21T09:08:34.650251
        LocalDateTime minusMinutes = now.minusMinutes(5); // 2018-01-21T09:04:33.650251
        LocalDateTime minusHours = now.minusHours(3); // 2018-01-21T06:09:33.650251
        LocalDateTime minusDays = now.minusDays(7); // 2018-01-14T09:09:33.650251
        LocalDateTime minusWeeks = now.minusWeeks(3); // 2017-12-31T09:09:33.650251
        LocalDateTime minusMonths = now.minusMonths(5); // 2017-08-21T09:09:33.650251
        LocalDateTime minusYears = now.minusYears(2); // 2016-01-21T09:09:33.650251
        LocalDateTime minusPeriod = now.minus(Period.ofWeeks(2)); // 2018-01-07T09:09:33.650251
        LocalDateTime minusDecades = now.minus(1, ChronoUnit.DECADES); // 2008-01-21T09:09:33.650251
    }

    private void twelve() {
        LocalDate now = LocalDate.now();
        String basicIsoDate = now.format(DateTimeFormatter.BASIC_ISO_DATE); // 20180128
        String isoDate = now.format(DateTimeFormatter.ISO_DATE); // 2018-01-28
        String isoWeekDate = now.format(DateTimeFormatter.ISO_WEEK_DATE); // 2018-W04-7
        String isoLocalDate = now.format(DateTimeFormatter.ISO_LOCAL_DATE); // 2018-01-28
        String isoOrdinalDate = now.format(DateTimeFormatter.ISO_ORDINAL_DATE); // 2018-028
    }

    private void third() {
        LocalDate now = LocalDate.now();
        String native_ = now.format(DateTimeFormatter.ofPattern("dd MMM yyyy")); // 28 Jan 2018
        String french = now.format(DateTimeFormatter.ofPattern("dd MMM yyyy", Locale.FRANCE)); // 28 janv. 2018
    }

    private void fourth() {
        LocalTime now = LocalTime.now();
        String isoLocalTime = now.format(DateTimeFormatter.ISO_LOCAL_TIME); // 08:09:31.514569
        String isoTime = now.format(DateTimeFormatter.ISO_TIME); // 08:09:31.514569
    }

    private void fifth() {
        LocalTime now = LocalTime.now();
        String nativeFormat = now.format(DateTimeFormatter.ofPattern("hh:mm:ss ")); // 08:10:43
        String engFormat = now.format(DateTimeFormatter.ofPattern("h:mm a")); // 08:10 AM
    }

    private void sixth() {
        LocalDateTime now = LocalDateTime.now();
        String rfcFormat = now.format(DateTimeFormatter.ofPattern("E, dd MMM yyyy hh:mm:ss")); // Sun, 28 Jan 2018 08:24:31
        String basicIsoDate = now.format(DateTimeFormatter.BASIC_ISO_DATE); // 20180128
        String isoDateTime = now.format(DateTimeFormatter.ISO_DATE_TIME); // 2018-01-28T08:24:31.412511
        String isoLocalDateTime = now.format(DateTimeFormatter.ISO_LOCAL_DATE_TIME); // 2018-01-28T08:24:31.412511
        String isoLocalDate = now.format(DateTimeFormatter.ISO_LOCAL_DATE); // 2018-01-28
    }

}