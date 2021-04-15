package ua.lpr.view.model;

import ua.lpr.entity.Recipient;

public class EmailListModel extends DefaultListModel<Recipient> {

    @Override
    public Object getElementAt(int index) {
        Recipient recipient = entities.get(index);

        return recipient.getEmail();
    }
}
