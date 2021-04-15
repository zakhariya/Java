package ua.lpr.controller;

import org.slf4j.Logger;
import ua.lpr.entity.Recipient;
import ua.lpr.model.Model;
import ua.lpr.view.View;

import java.sql.SQLException;
import java.util.List;
import java.util.Map;

import static org.slf4j.LoggerFactory.getLogger;

public class Controller {
    private final Model model = new Model();
    private final View view = new View(this);
    private static final Logger log = getLogger(Controller.class);

    public Map<String, String> getViewData() {
        return view.getViewData();
    }

    public List<Recipient> getRecipientList() {
        return model.getRecipientList();
    }

    public void exit() {
        System.exit(0);
    }

    public void hideView() {
        view.setVisible(false);
    }

    public void showView() {
        view.setVisible(true);
    }

    public void startSending() {
        Map<String, String> viewData = view.getViewData();
        System.out.println(viewData);
    }

    public void stopSending() {

    }

    public void showDetails() {

    }

    public void addRecipient() {
        Recipient recipient = view.showAddDialog();
        try {
            model.addRecipient(recipient);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void removeRecipients() {
    }
}
