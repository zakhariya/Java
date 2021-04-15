package ua.lpr.view.components;

import ua.lpr.util.Constants;

import javax.swing.*;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.HashMap;
import java.util.Map;

public class AddDialog extends JDialog {
    private JPanel contentPane;
    private JButton buttonOK;
    private JButton buttonCancel;
    private JTextField textEmail;
    private JTextField textName;
    private JTextField textCompany;
    private JTextField textCity;

    private Map<String, String> recipientData;

    public AddDialog() {
        setIconImage(Constants.LOGO);
        setTitle("Добавление");
        setContentPane(contentPane);
        setModal(true);
        getRootPane().setDefaultButton(buttonOK);
        pack();

        buttonOK.addActionListener(e -> onOK());

        buttonCancel.addActionListener(e -> onCancel());

        // call onCancel() when cross is clicked
        setDefaultCloseOperation(DO_NOTHING_ON_CLOSE);
        addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                onCancel();
            }
        });

        // call onCancel() on ESCAPE
        contentPane.registerKeyboardAction(e -> onCancel(),
                KeyStroke.getKeyStroke(KeyEvent.VK_ESCAPE, 0),
                JComponent.WHEN_ANCESTOR_OF_FOCUSED_COMPONENT);
    }

    private void onOK() {
        recipientData = new HashMap<>();

        recipientData.put("email", textEmail.getText());
        recipientData.put("name", textName.getText());
        recipientData.put("company", textCompany.getText());
        recipientData.put("city", textCity.getText());

        dispose();
    }

    private void onCancel() {
        recipientData = null;

        dispose();
    }


    public void addActionListener(ActionListener listener) {

    }

    public Map<String,String> getRecipientData() {
        return recipientData;
    }
}
