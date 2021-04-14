package ua.od.zakhariya.patterns.chain_of_responsibility;

public class Main {
    public static void main(String[] args) {

        Logger logger0 = newFileLogger(Level.INFO);
        Logger logger1 = newConsoleLogger(Level.WARN);
        Logger logger2 = newSmsLogger(Level.ERROR);
        Logger logger3 = newPhoneLogger(Level.FATAL);

        logger3.setNext(logger2);
        logger2.setNext(logger1);
        logger1.setNext(logger0);

        logger3.inform("Everything is OK", Level.INFO);
        logger3.inform("We found a bug", Level.WARN);
        logger3.inform("Database connection error", Level.ERROR);
        logger3.inform("System shut down", Level.FATAL);

        System.out.println("-----------");

        logger1.inform("Everything is OK", Level.INFO);
        logger1.inform("We found a bug", Level.WARN);
        logger1.inform("Database connection error", Level.ERROR);
        logger1.inform("System shut down", Level.FATAL);
    }

    private static Logger newFileLogger(int level) {
        return new Main().new FileLogger(level);
    }

    private static Logger newConsoleLogger(int level) {
        return new Main().new ConsoleLogger(level);
    }

    private static Logger newSmsLogger(int level) {
        return new Main().new SmsLogger(level);
    }

    private static Logger newPhoneLogger(int level) {
        return new Main().new PhoneLogger(level);
    }


    class FileLogger extends AbstractLogger {

        public FileLogger(int level) {
            super(level);
        }

        @Override
        public void info(String message) {

            System.out.println("Logging to file: " + message);
        }

    }
    class ConsoleLogger extends AbstractLogger {

        public ConsoleLogger(int level) {
            super(level);
        }

        @Override
        public void info(String message) {

            System.out.println("Logging to console: " + message);
        }

    }
    class SmsLogger extends AbstractLogger {

        public SmsLogger(int level) {
            super(level);
        }

        @Override
        public void info(String message) {

            System.out.println("Send SMS to CEO: " + message);
        }

    }
    class PhoneLogger extends AbstractLogger {

        public PhoneLogger(int level) {
            super(level);
        }

        @Override
        public void info(String message) {

            System.out.println("Call to director: " + message);
        }

    }
}