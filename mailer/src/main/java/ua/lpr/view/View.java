package ua.lpr.view;

import ua.lpr.controller.Controller;
import ua.lpr.entity.Recipient;
import ua.lpr.util.Constants;
import ua.lpr.view.components.*;
import ua.lpr.view.components.MenuBar;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class View extends JFrame implements ActionListener {

    private final Controller controller;
    private final MenuBar menuBar;
    private final EmailListPanel emailListPanel;
    private final SMTPPanel smtpPanel;
    private final ProgressPanel progressPanel;
    private final TextPanel textPanel;
    private final ToolBar toolBar;
    private final AddDialog addDialog;

    public View(Controller controller) throws HeadlessException {
        setLAF();
        setTrayIcon();

        this.controller = controller;
        emailListPanel = new EmailListPanel();
        smtpPanel = new SMTPPanel();
        progressPanel = new ProgressPanel();
        textPanel = new TextPanel();
        toolBar = new ToolBar();
        menuBar = new MenuBar();
        addDialog = new AddDialog();

        initView();
    }

    private void initView() {
        JPanel panelFullContent = new JPanel(null);
        panelFullContent.setPreferredSize(new Dimension(590, 500));

        panelFullContent.add(emailListPanel);
        panelFullContent.add(smtpPanel);
        panelFullContent.add(progressPanel);
        panelFullContent.add(textPanel);
        panelFullContent.add(toolBar);

        menuBar.addActionListener(this);
        emailListPanel.addActionListener(this);
        progressPanel.addActionListener(this);
        toolBar.addActionListener(this);
        addDialog.addActionListener(this);

        setResizable(true);
        setIconImage(Constants.LOGO);
        setTitle("Рассылка почты");
        setJMenuBar(menuBar);
        setMinimumSize(new Dimension(610, 620));
        setSize(610, 620);
        getContentPane().add(new JScrollPane(panelFullContent), BorderLayout.CENTER);
        setDefaultCloseOperation(WindowConstants.HIDE_ON_CLOSE);
        setLocation(300, 50);
//        addComponentListener(cl);
//        addMouseListener(ml);
    }

    private void setLAF() {
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Windows".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }

            UIManager.put("ProgressBar.cellSpacing", new Integer(2));
            UIManager.put("ProgressBar.cellLength", new Integer(5));

            UIManager.put("OptionPane.yesButtonText"   , "Да"    );
            UIManager.put("OptionPane.noButtonText"    , "Нет"   );
            UIManager.put("OptionPane.cancelButtonText", "Отмена");
            UIManager.put("OptionPane.okButtonText"    , "Ок");
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(View.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(View.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(View.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(View.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
    }

    private void setTrayIcon() {
        if(! SystemTray.isSupported() )
            return;

        MenuItem trayItemExit = new MenuItem("выйти");
        MenuItem trayItemUnwrap = new MenuItem("развернуть");

        trayItemUnwrap.addActionListener(this);
        trayItemExit.addActionListener(this);

        PopupMenu trayMenu = new PopupMenu();
        trayMenu.add(trayItemUnwrap);
        trayMenu.addSeparator();
        trayMenu.add(trayItemExit);

        TrayIcon trayIcon = new TrayIcon(Constants.TRAY_LOGO_I, "Рассылка почты", trayMenu);
        trayIcon.setImageAutoSize(true);

        SystemTray tray = SystemTray.getSystemTray();
        try {
            tray.add(trayIcon);
        } catch (AWTException e) {
            e.printStackTrace();
        }

//	    trayIcon.displayMessage(APPLICATION_NAME, "Приложение запущено", TrayIcon.MessageType.INFO);

        trayIcon.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                setVisible(true);
            }
        });
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
                controller.addRecipient();
            } else if (icon.equals(Constants.ICON_REMOVE)) {
                controller.removeRecipients();
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
                       (key, val) -> allData.put(key, val)));
    }

    public Recipient showAddDialog() {
        addDialog.setVisible(true);

        while (addDialog.isVisible()) {

        }

        Map<String, String> data = addDialog.getRecipientData();

        return new Recipient(data.get("name"), data.get("company"), data.get("city"), data.get("email"));
    }
}