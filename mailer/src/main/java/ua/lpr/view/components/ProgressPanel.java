package ua.lpr.view.components;

import ua.lpr.util.Constants;

import javax.swing.*;
import javax.swing.plaf.basic.BasicProgressBarUI;
import java.awt.*;
import java.awt.event.ActionListener;

public class ProgressPanel extends JPanel {
    private final JButton buttStart;
    private final JProgressBar progressBar;
    private final JTextArea textStatus;
    private final JScrollPane scrollStatus;

    public ProgressPanel() {
        buttStart = new JButton();
        progressBar = new JProgressBar();
        textStatus = new JTextArea();

        buttStart.setActionCommand("рассылка");
        buttStart.setIcon(Constants.ICON_START);
        add(buttStart);
        buttStart.setBounds(10, 10, 40, 40);

        add(progressBar);
        progressBar.setBounds(55, 12, 215, 15);
        progressBar.setUI(new BasicProgressBarUI());
        progressBar.setValue(0);
        progressBar.setBackground(Color.WHITE);
        progressBar.setForeground(new Color(6, 176, 37));

        textStatus.setEditable(false);
        textStatus.setName("статус");
        textStatus.setLineWrap(true);
        textStatus.setWrapStyleWord(true);
        textStatus.setBackground(new java.awt.Color(248, 248, 248));
        textStatus.setFont(new java.awt.Font("Tahoma", 0, 10)); // NOI18N
        textStatus.setBorder(BorderFactory.createCompoundBorder(BorderFactory.createEtchedBorder(0), null));

        scrollStatus = new JScrollPane();
        scrollStatus.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
        scrollStatus.setViewportView(textStatus);
        add(scrollStatus);
        scrollStatus.setBounds(10, 55, 255, 120);

        setLayout(null);
        setBounds(310, 170, 265, 175);
    }

    public void addActionListener(ActionListener listener) {
        buttStart.addActionListener(listener);
    }

    public void changeButtonIcon(boolean b) {
        if (b) {
            buttStart.setIcon(Constants.ICON_START);
        } else {
            buttStart.setIcon(Constants.ICON_PAUSE);
        }
    }

    public void log(String s) {
        new Thread(() -> {
            textStatus.append(String.format("%s%n", s));
            textStatus.setCaretPosition(textStatus.getText().length() - 1);
            textStatus.update(textStatus.getGraphics());
        }).start();
    }

    public void setProgressValue(int value) {
        progressBar.setValue(value);
    }
}
