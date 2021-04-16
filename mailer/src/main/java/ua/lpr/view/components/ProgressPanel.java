package ua.lpr.view.components;

import ua.lpr.util.Constants;

import javax.swing.*;
import javax.swing.plaf.basic.BasicProgressBarUI;
import java.awt.*;
import java.awt.event.ActionListener;
import java.awt.event.MouseListener;

public class ProgressPanel extends JPanel {
    private final JButton buttStart;
    private final JProgressBar progressBar;
    private final JButton buttDetais;
    private final JTextArea textStatus;

    public ProgressPanel() {
        buttStart = new JButton();
        progressBar = new JProgressBar();
        buttDetais = new JButton();
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

        buttDetais.setIcon(Constants.ICON_DETAILS);
        buttDetais.setActionCommand("детали");
        buttDetais.setToolTipText("Просмотреть детали выполнения");
        add(buttDetais);
        buttDetais.setBounds(225, 30, 18, 18);
        buttDetais.setFocusPainted(false);
        buttDetais.setContentAreaFilled(false);

        textStatus.setEditable(false);
        textStatus.setName("статус");
        textStatus.setLineWrap(true);
        textStatus.setBackground(new java.awt.Color(248, 248, 248));
        textStatus.setFont(new java.awt.Font("Tahoma", 0, 10)); // NOI18N
        textStatus.setBorder(javax.swing.BorderFactory.createCompoundBorder(javax.swing.BorderFactory.createEtchedBorder(0), null));

        JScrollPane scrollStatus = new JScrollPane();
        scrollStatus.setViewportView(textStatus);
        add(scrollStatus);
        scrollStatus.setBounds(10, 55, 255, 120);

        setLayout(null);
        setBounds(310, 170, 265, 175);
    }

    public void addActionListener(ActionListener listener) {
        buttStart.addActionListener(listener);
        buttDetais.addActionListener(listener);
    }

    public void addMouseListener(MouseListener listener) {
//        textStatus.addMouseListener(listener);
    }

    public void log(String s) {
        textStatus.append(String.format("%s%n", s));
    }
}
