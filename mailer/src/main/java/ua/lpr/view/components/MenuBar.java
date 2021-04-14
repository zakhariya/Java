package ua.lpr.view.components;

import ua.lpr.util.Constants;

import javax.swing.*;
import java.awt.event.ActionListener;

public class MenuBar extends JMenuBar {
    private final JMenu menuFile;
    private final JMenuItem exit;
    private final JMenuItem toTray;

    public MenuBar() {
        menuFile = new JMenu();
        exit = new JMenuItem();
        toTray = new JMenuItem();

        toTray.setText("в трей");
        toTray.setIcon(Constants.ICON_TO_TRAY);
        exit.setText("выйти");
        exit.setIcon(Constants.ICON_CLOSE);

        menuFile.setText("Файл");
        menuFile.add(toTray);
        menuFile.add(exit);

        add(menuFile);
    }

    public void addActionListener(ActionListener listener) {
        toTray.addActionListener(listener);
        exit.addActionListener(listener);

    }
}
