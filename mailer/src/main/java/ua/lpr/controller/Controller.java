package ua.lpr.controller;

import org.slf4j.Logger;
import ua.lpr.entity.Recipient;
import ua.lpr.model.Model;
import ua.lpr.util.EmailSender;
import ua.lpr.view.View;
import ua.lpr.view.ViewImpl;

import javax.mail.MessagingException;
import java.sql.SQLException;
import java.util.Collections;
import java.util.List;
import java.util.Map;

import static org.slf4j.LoggerFactory.getLogger;

public class Controller {
    private final Model model = new Model();
    private final View view = new ViewImpl(this);
    private static final Logger log = getLogger(Controller.class);
    private final EmailSender emailSender = new EmailSender(this);

    public void showView() {
        view.setVisible(true);
    }

    public void hideView() {
        view.setVisible(false);
    }

    public void exit() {
        System.exit(0);
    }

    public void startSending() {
        Map<String, String> viewData = getViewData();

        try {
            view.changeElementsState(false);
            emailSender.startSending(viewData, view.getRecipients());
        } catch (MessagingException e) {
            view.changeElementsState(true);
            log.error(e.getLocalizedMessage(), e);
        }
    }

    public void stopSending() {
        emailSender.stopSending();
        view.changeElementsState(true);
    }

    public Map<String, String> getViewData() {
        return view.getViewData();
    }

    public List<Recipient> getRecipientList() {
        try {
            return model.getRecipientList();
        } catch (SQLException e) {
            log.error(e.getLocalizedMessage(), e);
            return Collections.EMPTY_LIST;
        }
    }

    public void addRecipient() {
        Recipient recipient = view.showAddDialog();

        if (recipient != null) {
            try {
                Recipient newRecipient = model.addRecipient(recipient);
                view.updateList(newRecipient);
            } catch (SQLException e) {
                log.error(e.getLocalizedMessage(), e);
            }
        }
    }

    public void saveRecipient(Recipient recipient) {
        try {
            model.saveRecipient(recipient);
        } catch (SQLException e) {
            log.error(e.getLocalizedMessage(), e);
        }
    }

    public void removeRecipients() {
        try {
            List<Recipient> recipients = view.getSelectedRecipients();
            model.delete(recipients);
            view.updateList(model.getRecipientList());
        } catch (SQLException e) {
            log.error(e.getLocalizedMessage(), e);
        }
    }

    public void resetSent() {
        try {
            model.resetSent();
        } catch (SQLException e) {
            log.error(e.getLocalizedMessage(), e);
        }
    }

    public void setProgressValue(int value) {
        view.setProgressValue(value);
    }

    public View getView() {
        return view;
    }
}