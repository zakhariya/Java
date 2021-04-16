package ua.lpr;

import ua.lpr.controller.Controller;
import ua.lpr.util.LogAppender;

public class MailerApplication {
    public static void main(String[] args) {
        Controller controller = new Controller();
        LogAppender.setView(controller.getView());
        controller.showView();
    }
}
