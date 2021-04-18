package ua.lpr.view;

import com.mysql.cj.exceptions.WrongArgumentException;
import ua.lpr.controller.Controller;
import ua.lpr.entity.Recipient;
import ua.lpr.util.Constants;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Collections;
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
        trayItemUnwrap.addActionListener(this);
        trayItemExit.addActionListener(this);
    }

    @Override
    public Map<String, String> getViewData() {
        Map<String, String> allData = new HashMap<>();
        allData.put("html", textPanel.getHtml());
        allData.put("text", textPanel.getText());
        allData.put("unsubscribe", toolBar.getUnsubscribeUrl());
        allData.put("from", emailListPanel.getFrom());
        allData.put("subject", emailListPanel.getSubject());

        Map<String, String> smtpData = smtpPanel.getSmtpData();

        return Stream.concat(smtpData.entrySet().stream(), allData.entrySet().stream())
                .collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue, allData::put));
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
    public List<Recipient> getRecipients() {
        return emailListPanel.getList();
    }

    @Override
    public String getMessageHtml() {
        return textPanel.getHtml();
    }

    @Override
    public String getMessageText() {
        return textPanel.getText();
    }

    @Override
    public void setLog(String s) {
        progressPanel.log(s);
    }

    @Override
    public void setProgressValue(int value) {
        progressPanel.setProgressValue(value);
    }

    @Override
    public void changeElementsState(boolean b) {
        emailListPanel.setEnabled(b);
        smtpPanel.setEnabled(b);
        textPanel.setEnabled(b);
        toolBar.setEnabled(b);
        progressPanel.changeButtonIcon(b);
    }


    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getActionCommand().equals("выйти")) {
            controller.exit();
        } else if(e.getActionCommand().equals("в трей")) {
            controller.hideView();
        } else if(e.getActionCommand().equals("развернуть")) {
            controller.showView();
        } else if (e.getActionCommand().equals("Сброс отправки")) {
            String q = "Вы уверены, что хотите сбросить всем\nкому было отправлено письмо и добавть в список снова";
            int a = JOptionPane.showConfirmDialog(this, q, "", JOptionPane.YES_NO_OPTION);
            if (a == 0) {
                controller.resetSent();
                updateList(controller.getRecipientList());
            }
        } else {
            Component c = (Component) e.getSource();

            if (!(c instanceof JButton)) {
                return;
            }

            JButton button = (JButton) c;
            Icon icon = button.getIcon();

            if (icon.equals(Constants.ICON_START)) {
                controller.startSending();
            } else if (icon.equals(Constants.ICON_PAUSE)){
                controller.stopSending();
            } else if (icon.equals(Constants.ICON_ADD)) {
                try {
                    controller.addRecipient();
                } catch (WrongArgumentException ex) {
                    showMessage("Ошибка", ex.getMessage());
                }
            } else if (icon.equals(Constants.ICON_REMOVE)) {
                controller.removeRecipients();
            } else if (icon.equals(Constants.ICON_CLEAR)) {
                updateList(Collections.EMPTY_LIST);
            } else if (icon.equals(Constants.ICON_LOAD)) {
                updateList(controller.getRecipientList());
            }
        }

//        }else if(e.getSource().equals(toolB1)){
//            if(!Engine.inProgress){
//                Engine.writing = true;
//                new ThreadShowReadWriteProgress(textStatus, (Component) e.getSource()).start();
//                Engine.makeUnsubscribe(listRecipients);
//            }
//        }
    }
}
