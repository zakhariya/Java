package ua.lpr.view;

import com.mysql.cj.exceptions.WrongArgumentException;
import ua.lpr.controller.Controller;
import ua.lpr.entity.Recipient;
import ua.lpr.util.Constants;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class ViewImpl extends ViewComponents implements View, ActionListener {

    public ViewImpl(Controller controller) {
        super(controller);

        menuBar.addActionListener(this);
        emailListPanel.addActionListener(this);
        progressPanel.addActionListener(this);
        toolBar.addActionListener(this);
        addDialog.addActionListener(this);
        trayItemUnwrap.addActionListener(this);
        trayItemExit.addActionListener(this);
    }

    @Override
    public Map<String, String> getViewData() {
        Map<String, String> smtpData = smtpPanel.getSmtpData();
        Map<String, String> messageData = textPanel.getMessageData();

        Map<String, String> allData = new HashMap<>(smtpData);
        allData.put("unsubscribe", toolBar.getUnsubscribeUrl());
        allData.put("from", emailListPanel.getFrom());
        allData.put("subject", emailListPanel.getSubject());

        return Stream.concat(messageData.entrySet().stream(), allData.entrySet().stream())
                .collect(Collectors.toMap(
                        Map.Entry::getKey,
                        Map.Entry::getValue,
                        allData::put));
    }

    @Override
    public Recipient showAddDialog() {
        addDialog.setVisible(true);

        Map<String, String> data = addDialog.getRecipientData();

        if (data == null) {
            return null;
        }

        return new Recipient(data.get("name"), data.get("company"), data.get("city"), data.get("email"));
    }

    @Override
    public void showMessage(String title, String message) {
        JOptionPane.showMessageDialog(this, message, title, JOptionPane.INFORMATION_MESSAGE);
    }

    @Override
    public void updateList(Recipient recipient) {
        emailListPanel.updateList(recipient);
    }

    @Override
    public void updateList(List<Recipient> recipients) {
        emailListPanel.updateList(recipients);
    }

    @Override
    public List<Recipient> getSelectedRecipients() {
        return emailListPanel.getSelected();
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getActionCommand().equals("выйти")) {
            controller.exit();
        } else if(e.getActionCommand().equals("в трей")) {
            controller.hideView();
        } else if(e.getActionCommand().equals("развернуть")) {
            controller.showView();
        } else if (e.getActionCommand().equals("детали")) {
            controller.showDetails();
        } else if (e.getActionCommand().equals("md5")) {
            //TODO: md5 or clear
        } else {
            Component c = (Component) e.getSource();

            if (!(c instanceof JButton)) {
                return;
            }

            JButton button = (JButton) c;
            Icon icon = button.getIcon();

            if (icon.equals(Constants.ICON_START)) {
                controller.startSending();
                button.setIcon(Constants.ICON_PAUSE);
            } else if (icon.equals(Constants.ICON_PAUSE)){
                controller.stopSending();
                button.setIcon(Constants.ICON_START);
            } else if (icon.equals(Constants.ICON_ADD)) {
                try {
                    controller.addRecipient();
                } catch (SQLException | WrongArgumentException ex) {
                    showMessage("Ошибка", ex.getMessage());
                }
            } else if (icon.equals(Constants.ICON_REMOVE)) {
                try {
                    controller.removeRecipients();
                } catch (SQLException e1) {
                    e1.printStackTrace();
                }
            } else if (icon.equals(Constants.ICON_LOAD)) {
                try {
                    List<Recipient> recipients = controller.getRecipientList();
                    updateList(recipients);
                } catch (SQLException e1) {
                    e1.printStackTrace();
                }
            }
        }

//        if(e.getSource().equals(buttAddEmail)){
//            if(!Engine.inProgress)
//                Engine.addRecipient(listModel);
//        }else if(e.getSource().equals(buttRemoveSelectedEmails)){
//            if(!Engine.inProgress){
//                int[] idx = listRecipients.getSelectedIndices();
//                for(int i=0; i<idx.length; i++)
//                    listModel.removeElementAt(idx[i]-i);
//            }
//        }else if(e.getSource().equals(buttClearList)){
//            if(!Engine.inProgress)
//                listModel.clear();
//        }else if(e.getSource().equals(buttFillList)){
//            if(!Engine.inProgress){
//                Engine.reading = true;
//                new ThreadShowReadWriteProgress(listRecipients, (Component) e.getSource()).start();
//                Engine.fillList(listModel);
//            }
//        }else if(e.getSource().equals(toolB1)){
//            if(!Engine.inProgress){
//                Engine.writing = true;
//                new ThreadShowReadWriteProgress(textStatus, (Component) e.getSource()).start();
//                Engine.makeUnsubscribe(listRecipients);
//            }
//        }
    }
}
