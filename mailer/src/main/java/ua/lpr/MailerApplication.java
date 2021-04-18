package ua.lpr;

import ua.lpr.controller.Controller;
import ua.lpr.view.logger.GuiLogger;

public class MailerApplication {
    public static void main(String[] args) {
        Controller controller = new Controller();
        GuiLogger.setView(controller.getView());
        controller.showView();
    }
}
