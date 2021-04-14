package ua.lpr.view.components;

import ua.lpr.util.Constants;

import javax.swing.*;
import javax.swing.event.ListDataEvent;
import javax.swing.event.ListDataListener;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class EmailListPanel extends JPanel {
    private final JTextField textFrom;
    private final JTextField textSubject;
    private final JButton buttAddEmail;
    private final JButton buttRemoveSelectedEmails;
    private final JButton buttClearList;
    private final JButton buttFillList;
    private final JList<String> listRecipients;


    public EmailListPanel() {
        textFrom = new JTextField();
        textSubject = new JTextField();
        buttAddEmail = new JButton();
        buttRemoveSelectedEmails = new JButton();
        buttClearList = new JButton();
        buttFillList = new JButton();
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
        add(buttAddEmail);
        buttAddEmail.setFocusPainted(false);
        buttAddEmail.setIcon(Constants.ICON_ADD);
        buttAddEmail.setToolTipText("Добавить адрес");
        buttAddEmail.setBounds(229, 75, 20, 20);
//        buttAddEmail.addActionListener(this);

        add(buttRemoveSelectedEmails);
        buttRemoveSelectedEmails.setFocusPainted(false);
        buttRemoveSelectedEmails.setIcon(Constants.ICON_REMOVE);
        buttRemoveSelectedEmails.setToolTipText("Удалить выбранные адреса");
//        buttRemoveSelectedEmails.addActionListener(this);
        buttRemoveSelectedEmails.setBounds(251, 75, 20, 20);

        add(buttClearList);
        buttClearList.setFocusPainted(false);
        buttClearList.setIcon(Constants.ICON_CLEAR);
        buttClearList.setToolTipText("Очистить список");
//        buttClearList.addActionListener(this);
        buttClearList.setBounds(273, 75, 20, 20);

        add(buttFillList);
        buttFillList.setIcon(Constants.ICON_DOWNLOAD);
        buttFillList.setToolTipText("Загрузить список");
        buttFillList.setFocusPainted(false);
        buttFillList.setBounds(295, 75, 20, 20);
//        buttFillList.addActionListener(this);

        listRecipients.setModel(new DefaultListModel<String>());
        JScrollPane scrollRecipients = new JScrollPane();
        add(scrollRecipients);
        scrollRecipients.setViewportView(listRecipients);
        scrollRecipients.setBounds(10, 100, 305, 245);

//        listRecipients.getModel().addListDataListener(new ListDataListener() {
//            public void intervalRemoved(ListDataEvent e) {
//                if(listRecipients.getModel().getSize() == 0) {
//                    textURL.setEditable(false);
//                } else if(textURL.isEnabled()) {
//                    textURL.setEditable(true);
//                }
//            }
//            public void intervalAdded(ListDataEvent e) {
//                if(listRecipients.getModel().getSize() == 0) {
//                    textURL.setEditable(false);
//                } else if(textURL.isEnabled()) {
//                    textURL.setEditable(true);
//                }
//            }
//            public void contentsChanged(ListDataEvent e) {}
//        });

//        listRecipients.addListSelectionListener(lsl);



//        textArea.setColumns(20);
//        textArea.setFont(new java.awt.Font("Arial Unicode MS", 0, 12)); // NOI18N
//        textArea.setRows(5);
//        textArea.setLineWrap(true);
//        textArea.setToolTipText("<html>Текст письма<br>(можно с тегами HTML)");
//        textArea.setCaretColor(new java.awt.Color(50, 150, 0));
//
//        scrollTextArea.setViewportView(textArea);
//        panelFullContent.add(scrollTextArea);
//        scrollTextArea.setBounds(10, y1+260, 570, 150);
    }
}
