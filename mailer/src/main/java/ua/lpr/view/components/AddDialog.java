package ua.lpr.view.components;

import ua.lpr.util.Constants;
import ua.lpr.view.View;

import javax.swing.*;
import java.awt.event.*;
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

    private final Map<String, String> recipientData = new HashMap<>();

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
            public void windowClosing(WindowEvent e) {
                onCancel();
            }
        });

        // call onCancel() on ESCAPE
        contentPane.registerKeyboardAction(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                onCancel();
            }
        }, KeyStroke.getKeyStroke(KeyEvent.VK_ESCAPE, 0), JComponent.WHEN_ANCESTOR_OF_FOCUSED_COMPONENT);
    }

    private void onOK() {
        recipientData.put("email", textEmail.getText());
        recipientData.put("name", textName.getText());
        recipientData.put("company", textCompany.getText());
        recipientData.put("city", textCity.getText());

        dispose();
    }

    private void onCancel() {
        recipientData.put("email", "");
        recipientData.put("name", "");
        recipientData.put("company", "");
        recipientData.put("city", "");

        dispose();
    }


    public void addActionListener(ActionListener listener) {

    }

    public Map<String,String> getRecipientData() {
        return recipientData;
    }
}
