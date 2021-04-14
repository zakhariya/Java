package ua.od.zakhariya.locale;

import java.util.Calendar;
import java.util.Date;
import java.util.Locale;
import java.util.ResourceBundle;

public class Internationalization {

    public static void main(String[] args) throws Exception {
        ResourceBundle bundle1 = ResourceBundle.getBundle("TestBundle");
        displayValues(bundle1);

        Locale defaultLocale = Locale.getDefault();
        ResourceBundle bundle2 = ResourceBundle.getBundle("TestBundle", defaultLocale);
        displayValues(bundle2);

        Locale swedishLocale = new Locale("sv", "SE");
        ResourceBundle bundle3 = ResourceBundle.getBundle("TestBundle", swedishLocale);
        displayValues(bundle3);

        Locale nonexistentLocale = new Locale("xx", "XX");
        ResourceBundle bundle4 = ResourceBundle.getBundle("TestBundle", nonexistentLocale);
        displayValues(bundle4);



        Calendar calendar = Calendar.getInstance();
        calendar.setTime(new Date());

        //определяем часы
        int temp = calendar.get(Calendar.HOUR_OF_DAY);

        //теперь узнаем локаль
        String locale = String.valueOf(Locale.getDefault());

        System.out.print("Good " + getGreeting(temp) +", " + getName() + "\n");

        // Попробуем поставить немецкую локаль
        Locale.setDefault(Locale.GERMAN);

        System.out.print("Good " + getGreeting(temp) +", " + getName() + "\n");
    }

    private static void displayValues(ResourceBundle bundle) {
        System.out.println("hello message:" + bundle.getString("my.hello"));
        System.out.println("goodbye message:" + bundle.getString("my.goodbye"));
        System.out.println("question message:" + bundle.getString("my.question"));
        System.out.println();
    }

    private static String getName() {
        if (Locale.getDefault() == Locale.GERMAN)
            return "Hans";
        if (Locale.getDefault() == Locale.ENGLISH)
            return "Tommy";
        if (Locale.getDefault() == Locale.ITALIAN)
            return "Mario";

        return "Vasya";
    }

    private static String getGreeting(int hours) {
        if (hours < 4)
            return "night";
        else if (hours < 12)
            return "morning";
        else if (hours < 18)
            return "afternoon";
        else
            return "evening";
    }

}
