package ua.lpr.view;

import org.slf4j.Logger;
import ua.lpr.controller.Controller;
import ua.lpr.util.Constants;
import ua.lpr.view.components.*;
import ua.lpr.view.components.MenuBar;

import javax.swing.*;
import java.awt.*;

import static org.slf4j.LoggerFactory.getLogger;

public class ViewComponents extends JFrame {

    private static final Logger log = getLogger(ViewComponents.class);

    final Controller controller;
    final MenuBar menuBar;
    final EmailListPanel emailListPanel;
    final SMTPPanel smtpPanel;
    final ProgressPanel progressPanel;
    final TextPanel textPanel;
    final ToolBar toolBar;
    final AddDialog addDialog;
    final MenuItem trayItemExit;
    final MenuItem trayItemUnwrap;

    public ViewComponents(Controller controller) {
        setLAF();

        this.controller = controller;
        emailListPanel = new EmailListPanel();
        smtpPanel = new SMTPPanel();
        progressPanel = new ProgressPanel();
        textPanel = new TextPanel();
        toolBar = new ToolBar();
        menuBar = new MenuBar();
        addDialog = new AddDialog();
        trayItemExit = new MenuItem("выйти");
        trayItemUnwrap = new MenuItem("развернуть");

        setTrayIcon();
        initView();
    }

    private void initView() {
        JPanel contentPane = new JPanel(null);
        contentPane.setPreferredSize(new Dimension(590, 500));

        contentPane.add(emailListPanel);
        contentPane.add(smtpPanel);
        contentPane.add(progressPanel);
        contentPane.add(textPanel);
        contentPane.add(toolBar);

        addDialog.setLocationRelativeTo(contentPane);

        setResizable(true);
        setIconImage(Constants.LOGO);
        setTitle("Рассылка почты");
        setJMenuBar(menuBar);
        setMinimumSize(new Dimension(610, 620));
        setSize(610, 620);
        getContentPane().add(new JScrollPane(contentPane), BorderLayout.CENTER);
        setDefaultCloseOperation(WindowConstants.HIDE_ON_CLOSE);
        setLocationRelativeTo(null);

//        addComponentListener(cl);
//        addMouseListener(ml);
    }

    private void setLAF() {
        try {
            for (UIManager.LookAndFeelInfo info : UIManager.getInstalledLookAndFeels()) {
                if ("Windows".equals(info.getName())) {
                    UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }

            UIManager.put("ProgressBar.cellSpacing", 2);
            UIManager.put("ProgressBar.cellLength", 5);

            UIManager.put("OptionPane.yesButtonText"   , "Да"    );
            UIManager.put("OptionPane.noButtonText"    , "Нет"   );
            UIManager.put("OptionPane.cancelButtonText", "Отмена");
            UIManager.put("OptionPane.okButtonText"    , "Ок");
        } catch (ClassNotFoundException | InstantiationException | IllegalAccessException | UnsupportedLookAndFeelException ex) {
            log.error(ex.getLocalizedMessage(), ex);
        }
    }

    private void setTrayIcon() {
        if(!SystemTray.isSupported()) {
            return;
        }

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
            log.error(e.getLocalizedMessage(), e);
        }

//	    trayIcon.displayMessage(APPLICATION_NAME, "Приложение запущено", TrayIcon.MessageType.INFO);

        trayIcon.addActionListener(e -> setVisible(true));
    }

}