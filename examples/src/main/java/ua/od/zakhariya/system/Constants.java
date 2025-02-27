package ua.od.zakhariya.system;

import java.awt.*;
import java.io.File;

public class Constants {
    public static final String OS = System.getProperty("os.name");
    public static final String PROGRAM_PATH = System.getProperty("user.dir");  //directory where java was run from,
    // where you started JVM (if not on console, it will be path to program)
    public static final String USER_NAME = System.getProperty("user.name"); //System.getenv("USER")
    public static final String USER_DOCUMENTS = System.getProperty("user.home"); // user`s home directory (C:\Users\Саня Z, /home/worker)
    public static final String EXECUTED_FILE_PATH = System.getProperty("java.class.path"); // real path and file name of application
    public static final String FILE_SEPARATOR = System.getProperty("file.separator"); // separation on file system "\" on win, or "/" on unix
    public static final String USER_DOWNLOADS = Constants.USER_DOCUMENTS + Constants.FILE_SEPARATOR + "Downloads" + Constants.FILE_SEPARATOR;
    public static final String PATH_SEPARATOR = System.getProperty("path.separator"); // separator for list of file paths ";" on win, or ":" on unix
    public static final String LINE_SEPARATOR = System.lineSeparator(); // \n on win, or \r\n on unix
//    public static final String COMPUTER_NAME = InetAddress.getLocalHost().getHostName();

//    public static final ImageIcon ICON_LOGO =  new ImageIcon(Constants.class.getResource("logo.png"));

    public static final int SCREEN_WIDTH = (int) Toolkit.getDefaultToolkit().getScreenSize().getWidth();
    public static final int SCREEN_HEIGHT = (int) Toolkit.getDefaultToolkit().getScreenSize().getHeight();

    public static final int SCREEN_VISIBLE_WIDTH = (int) GraphicsEnvironment.getLocalGraphicsEnvironment().getMaximumWindowBounds().getWidth();
    public static final int SCREEN_VISIBLE_HEIGHT = (int) GraphicsEnvironment.getLocalGraphicsEnvironment().getMaximumWindowBounds().getHeight();

    public static final File FILE = new File("src//main//resources//example.txt");

}