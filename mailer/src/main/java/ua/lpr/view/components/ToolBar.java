package ua.lpr.view.components;

import ua.lpr.util.Constants;

import javax.swing.*;
import javax.swing.event.DocumentListener;
import java.awt.*;
import java.awt.event.ActionListener;
import java.awt.event.ItemListener;
import java.awt.event.MouseListener;

public class ToolBar extends JToolBar {
    private final JButton toolB1;
    private final JTextField textURL;
    private final JLabel labURL;
    private final JButton buttExit;


    public ToolBar() {
        toolB1 = new JButton("Сброс отправки");
        textURL = new JTextField();
        labURL = new JLabel();
        buttExit = new JButton(Constants.ICON_EXIT);

        setLayout(new BoxLayout(this, BoxLayout.X_AXIS));
        setBounds(10, 510, 450, 25);
        setEnabled(false);
        setRollover(true);

        buttExit.setToolTipText("Свернуть в трей");
        buttExit.setActionCommand("в трей");
        add(buttExit);
        buttExit.setHorizontalTextPosition(SwingConstants.RIGHT);
        buttExit.setVerticalTextPosition(SwingConstants.BOTTOM);
        buttExit.setBounds(500, 435, 80, 40);

        add(Box.createHorizontalStrut(5));

        toolB1.setToolTipText("<html>сбросить всем кому было отправлено письмо<br>и добавть в список снова");
        toolB1.setFocusable(false);
        toolB1.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        toolB1.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        add(toolB1);
        add(Box.createHorizontalStrut(5));

        textURL.setText("https://");
        textURL.setToolTipText("адрес страницы отписки");
        textURL.setMinimumSize(new Dimension(150, 20));
        textURL.setMaximumSize(new Dimension(250, 20));
        add(textURL);
        add(Box.createHorizontalStrut(5));

        labURL.setForeground(Color.BLUE);
        add(labURL);
        add(Box.createHorizontalStrut(5));
    }

    public void addActionListener(ActionListener listener) {
        toolB1.addActionListener(listener);
        buttExit.addActionListener(listener);
    }

    public void addMouseListener(MouseListener listener) {
//        textURL.addMouseListener(listener);
//        labURL.addMouseListener(listener);
    }

    public void addDocumentListener(DocumentListener listener) {
        textURL.getDocument().addDocumentListener(listener);
    }

    public String getUnsubscribeUrl() {
        return textURL.getText();
    }
}
