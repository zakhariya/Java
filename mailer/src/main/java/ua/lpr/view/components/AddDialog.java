package ua.lpr.view.components;

import ua.lpr.util.Constants;

import javax.swing.*;
import java.awt.*;
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
        init();

        setContentPane(contentPane);
        setModal(true);
        setLocationRelativeTo(null);
        getRootPane().setDefaultButton(buttonOK);
        setIconImage(Constants.LOGO);
        setTitle("Добавление");
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

    public Map<String,String> getRecipientData() {
        return recipientData;
    }

    private void init() {
        contentPane = new JPanel();
        contentPane.setLayout(new GridBagLayout());
        final JPanel panel1 = new JPanel();
        panel1.setLayout(new BorderLayout(0, 0));
        GridBagConstraints gbc;
        gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.gridy = 1;
        gbc.weightx = 1.0;
        gbc.fill = GridBagConstraints.BOTH;
        contentPane.add(panel1, gbc);
        final JPanel panel2 = new JPanel();
        panel2.setLayout(new FlowLayout(FlowLayout.RIGHT, 5, 5));
        panel1.add(panel2, BorderLayout.CENTER);
        buttonOK = new JButton();
        buttonOK.setPreferredSize(new Dimension(54, 24));
        buttonOK.setText("OK");
        panel2.add(buttonOK);
        buttonCancel = new JButton();
        buttonCancel.setText("Cancel");
        panel2.add(buttonCancel);
        final JPanel panel3 = new JPanel();
        panel3.setLayout(new BorderLayout(0, 0));
        gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.weightx = 1.0;
        gbc.weighty = 1.0;
        gbc.fill = GridBagConstraints.BOTH;
        contentPane.add(panel3, gbc);
        final JPanel panel4 = new JPanel();
        panel4.setLayout(new GridBagLayout());
        panel3.add(panel4, BorderLayout.WEST);
        final JLabel label1 = new JLabel();
        label1.setHorizontalAlignment(11);
        label1.setPreferredSize(new Dimension(66, 24));
        label1.setText("Email");
        gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.gridy = 0;
        panel4.add(label1, gbc);
        final JPanel spacer1 = new JPanel();
        gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.gridy = 4;
        gbc.fill = GridBagConstraints.VERTICAL;
        panel4.add(spacer1, gbc);
        final JLabel label2 = new JLabel();
        label2.setHorizontalAlignment(11);
        label2.setPreferredSize(new Dimension(66, 24));
        label2.setText("Имя");
        gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.gridy = 1;
        panel4.add(label2, gbc);
        final JLabel label3 = new JLabel();
        label3.setHorizontalAlignment(11);
        label3.setPreferredSize(new Dimension(66, 24));
        label3.setText("Компания");
        gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.gridy = 2;
        panel4.add(label3, gbc);
        final JLabel label4 = new JLabel();
        label4.setHorizontalAlignment(11);
        label4.setPreferredSize(new Dimension(66, 24));
        label4.setText("Город");
        gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.gridy = 3;
        panel4.add(label4, gbc);
        final JPanel panel5 = new JPanel();
        panel5.setLayout(new GridBagLayout());
        panel3.add(panel5, BorderLayout.CENTER);
        textEmail = new JTextField();
        textEmail.setMinimumSize(new Dimension(200, 24));
        gbc = new GridBagConstraints();
        gbc.gridx = 1;
        gbc.gridy = 0;
        gbc.anchor = GridBagConstraints.WEST;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        panel5.add(textEmail, gbc);
        final JPanel spacer2 = new JPanel();
        gbc = new GridBagConstraints();
        gbc.gridx = 1;
        gbc.gridy = 4;
        gbc.fill = GridBagConstraints.VERTICAL;
        panel5.add(spacer2, gbc);
        textName = new JTextField();
        textName.setPreferredSize(new Dimension(200, 24));
        gbc = new GridBagConstraints();
        gbc.gridx = 1;
        gbc.gridy = 1;
        gbc.anchor = GridBagConstraints.WEST;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        panel5.add(textName, gbc);
        textCompany = new JTextField();
        textCompany.setPreferredSize(new Dimension(200, 24));
        gbc = new GridBagConstraints();
        gbc.gridx = 1;
        gbc.gridy = 2;
        gbc.anchor = GridBagConstraints.WEST;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        panel5.add(textCompany, gbc);
        textCity = new JTextField();
        textCity.setPreferredSize(new Dimension(200, 24));
        gbc = new GridBagConstraints();
        gbc.gridx = 1;
        gbc.gridy = 3;
        gbc.anchor = GridBagConstraints.WEST;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        panel5.add(textCity, gbc);
        final JPanel spacer3 = new JPanel();
        gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        panel5.add(spacer3, gbc);
        final JPanel spacer4 = new JPanel();
        gbc = new GridBagConstraints();
        gbc.gridx = 2;
        gbc.gridy = 0;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        panel5.add(spacer4, gbc);
    }
}
