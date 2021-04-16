package ua.lpr.view;

import ua.lpr.entity.Recipient;

import java.util.List;
import java.util.Map;

public interface View {
    Map<String, String> getViewData();

    Recipient showAddDialog();

    void showMessage(String title, String message);

    void updateList(Recipient recipient);

    void updateList(List<Recipient> recipients);

    void setVisible(boolean b);

    List<Recipient> getSelectedRecipients();

    List<Recipient> getRecipients();

    String getMessageHtml();

    String getMessageText();

    void setLog(String s);
}
