package ua.lpr.view.components;

import ua.lpr.entity.Recipient;
import ua.lpr.util.Constants;
import ua.lpr.view.model.EmailListModel;

import javax.swing.*;
import java.awt.event.ActionListener;
import java.util.List;

public class EmailListPanel extends JPanel {
    private final JTextField textFrom;
    private final JTextField textSubject;
    private final JButton buttAdd;
    private final JButton buttRemove;
    private final JButton buttClear;
    private final JButton buttLoad;
    private final JList<String> listRecipients;
    private final EmailListModel listModel;


    public EmailListPanel() {
        textFrom = new JTextField();
        textSubject = new JTextField();
        buttAdd = new JButton();
        buttRemove = new JButton();
        buttClear = new JButton();
        buttLoad = new JButton();
        listRecipients = new JList<>();

        setLayout(null);
        setBounds(0, 0, 320, 345);

        JLabel labFrom = new JLabel("От кого:");
        add(labFrom);
        labFrom.setBounds(15, 25, 80, 20);
        add(textFrom);
        textFrom.setBounds(95, 25, 220, 20);

        JLabel labSubject = new JLabel("Тема:");
        add(labSubject);
        labSubject.setBounds(15, 50, 80, 20);
        add(textSubject);
        textSubject.setBounds(95, 50, 220, 20);

        JLabel labRecipients = new JLabel("Получатели:");
        add(labRecipients);
        labRecipients.setBounds(15, 75, 100, 20);
        add(buttAdd);
        buttAdd.setFocusPainted(false);
        buttAdd.setIcon(Constants.ICON_ADD);
        buttAdd.setToolTipText("Добавить адрес");
        buttAdd.setBounds(229, 75, 20, 20);

        add(buttRemove);
        buttRemove.setFocusPainted(false);
        buttRemove.setIcon(Constants.ICON_REMOVE);
        buttRemove.setToolTipText("Удалить выбранные адреса");
        buttRemove.setBounds(251, 75, 20, 20);

        add(buttClear);
        buttClear.setFocusPainted(false);
        buttClear.setIcon(Constants.ICON_CLEAR);
        buttClear.setToolTipText("Очистить список");
        buttClear.setBounds(273, 75, 20, 20);

        add(buttLoad);
        buttLoad.setIcon(Constants.ICON_LOAD);
        buttLoad.setToolTipText("Загрузить список");
        buttLoad.setFocusPainted(false);
        buttLoad.setBounds(295, 75, 20, 20);

        listModel = new EmailListModel();
        listRecipients.setModel(listModel);
        JScrollPane scrollRecipients = new JScrollPane();
        add(scrollRecipients);
        scrollRecipients.setViewportView(listRecipients);
        scrollRecipients.setBounds(10, 100, 305, 245);
    }

    public String getFrom() {
        return textFrom.getText();
    }

    public String getSubject() {
        return textSubject.getText();
    }

    public void addActionListener(ActionListener listener) {
        buttAdd.addActionListener(listener);
        buttRemove.addActionListener(listener);
        buttClear.addActionListener(listener);
        buttLoad.addActionListener(listener);
    }

    public void updateList(Recipient recipient) {
        listModel.addEntity(recipient);
        updateListUI();
    }

    public void updateList(List<Recipient> recipients) {
        listModel.clear();
        listModel.addEntities(recipients);
        updateListUI();
    }

    public List<Recipient> getSelected() {
        return listModel.getEntities(listRecipients.getSelectedIndices());
    }

    private void updateListUI() {
        listRecipients.updateUI();
        listRecipients.clearSelection();
    }

    public List<Recipient> getList() {
        return listModel.getEntities();
    }
}
