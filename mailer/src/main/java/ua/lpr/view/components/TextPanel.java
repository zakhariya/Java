package ua.lpr.view.components;

import ua.lpr.util.parser.HtmlParser;

import javax.swing.*;
import java.awt.*;

public class TextPanel extends JPanel {
    private final JTextArea htmlTextArea;
    private final JTextArea textArea;

    public TextPanel() {
        setLayout(null);
        setBounds(10, 350, 570, 150);

        JTabbedPane tabbedPane = new JTabbedPane();
        tabbedPane.setBounds(0, 0, 570, 150);
        add(tabbedPane);

        htmlTextArea = new JTextArea();
        htmlTextArea.setColumns(20);
        htmlTextArea.setFont(new java.awt.Font("Arial Unicode MS", 0, 12)); // NOI18N
        htmlTextArea.setRows(5);
        htmlTextArea.setLineWrap(true);
        htmlTextArea.setWrapStyleWord(true);
        htmlTextArea.setToolTipText("<html>Текст письма<br>(можно с тегами HTML)");
        htmlTextArea.setCaretColor(new java.awt.Color(50, 150, 0));

        JScrollPane scrollHtmlArea = new JScrollPane();
        scrollHtmlArea.setViewportView(htmlTextArea);
        tabbedPane.add("HTML", scrollHtmlArea);

        textArea = new JTextArea();
        textArea.setColumns(20);
        textArea.setFont(new java.awt.Font("Arial Unicode MS", 0, 12)); // NOI18N
        textArea.setRows(5);
        textArea.setLineWrap(true);
        textArea.setWrapStyleWord(true);

        tabbedPane.addChangeListener(e -> fillTextPane());

        JScrollPane scrollTextArea = new JScrollPane();
        scrollTextArea.setViewportView(textArea);
        tabbedPane.add("plain/text", scrollTextArea);
    }

    @Override
    public void setEnabled(boolean b) {
        htmlTextArea.setEnabled(b);
        textArea.setEnabled(b);
    }

    public String getHtml() {
        return htmlTextArea.getText();
    }

    public String getText() {
        return textArea.getText();
    }

    public void fillTextPane() {
        if (textArea.getText().length() == 0) {
            textArea.setText(HtmlParser.getTextFromHtml(htmlTextArea.getText()));
        }
    }
}
