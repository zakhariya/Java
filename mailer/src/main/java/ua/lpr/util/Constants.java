package ua.lpr.util;

import ua.lpr.MailerApplication;

import javax.swing.*;
import java.awt.*;

public class Constants {
    public static final Image LOGO = Toolkit.getDefaultToolkit().getImage(MailerApplication.class.getResource("/img/logo.png"));
    public static final Image TRAY_LOGO_I = Toolkit.getDefaultToolkit().getImage(MailerApplication.class.getResource("/img/tray1.png"));
    public static final Image TRAY_LOGO_II = Toolkit.getDefaultToolkit().getImage(MailerApplication.class.getResource("/img/tray2.png"));
    public static final Image TRAY_LOGO_III = Toolkit.getDefaultToolkit().getImage(MailerApplication.class.getResource("/img/tray3.png"));

    public static final ImageIcon ICON_DETAILS =  new ImageIcon(MailerApplication.class.getResource("/img/details.png"));
    public static final ImageIcon ICON_START =  new ImageIcon(MailerApplication.class.getResource("/img/start.png"));
    public static final ImageIcon ICON_PAUSE =  new ImageIcon(MailerApplication.class.getResource("/img/pause.png"));
    public static final ImageIcon ICON_EXIT =  new ImageIcon(MailerApplication.class.getResource("/img/exit.png"));
    public static final ImageIcon ICON_ADD =  new ImageIcon(MailerApplication.class.getResource("/img/add.png"));
    public static final ImageIcon ICON_REMOVE =  new ImageIcon(MailerApplication.class.getResource("/img/remove.png"));
    public static final ImageIcon ICON_CLEAR =  new ImageIcon(MailerApplication.class.getResource("/img/clear.png"));
    public static final ImageIcon ICON_LOAD =  new ImageIcon(MailerApplication.class.getResource("/img/download.png"));
    public static final ImageIcon ICON_TO_TRAY =  new ImageIcon(MailerApplication.class.getResource("/img/toTray.png"));
    public static final ImageIcon ICON_FROM_TRAY =  new ImageIcon(MailerApplication.class.getResource("/img/fromTray.png"));
    public static final ImageIcon ICON_CLOSE =  new ImageIcon(MailerApplication.class.getResource("/img/close.png"));

}
