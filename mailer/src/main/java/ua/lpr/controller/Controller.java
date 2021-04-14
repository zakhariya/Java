package ua.lpr.controller;

import ua.lpr.entity.Recipient;
import ua.lpr.model.Model;
import ua.lpr.view.View;

import java.util.List;
import java.util.Map;

public class Controller {
    Model model = new Model();
    View view = new View(this);

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

    }

    public void stopSending() {

    }

    public void showDetails() {

    }
}
