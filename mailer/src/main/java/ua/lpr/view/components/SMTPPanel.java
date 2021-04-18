package ua.lpr.view.components;

import javax.swing.*;
import javax.swing.border.TitledBorder;
import java.awt.*;
import java.util.HashMap;
import java.util.Map;

public class SMTPPanel extends JPanel {
    private final JTextField textSMTP;
    private final JTextField textPort;
    private final JComboBox<String> comPort;
    private final JTextField textLogin;
    private final JPasswordField textPassword;
    private final JSpinner spinDelay;
    private final JComboBox<String> comDelay;

    public SMTPPanel() {
        String title = "Настройки отправки";
        Font font = new Font("Century", 0, 10);
        Color color = new Color(153, 153, 153);
        TitledBorder border =
                BorderFactory.createTitledBorder(null, title, TitledBorder.DEFAULT_JUSTIFICATION, TitledBorder.DEFAULT_POSITION, font, color);

        setBorder(border);
        setForeground(color);
        setLayout(null);
        setBounds(318, 10, 265, 155);

        JLabel labSMTP = new JLabel("SMTP-сервер");
        add(labSMTP);
        labSMTP.setBounds(15, 20, 90, 20);
        textSMTP = new JTextField();
        add(textSMTP);
        textSMTP.setBounds(105, 20, 145, 20);

        JLabel labPort = new JLabel("Порт");
        add(labPort);
        labPort.setBounds(15, 45, 90, 20);
        textPort = new JTextField();
        add(textPort);
        textPort.setBounds(105, 45, 80, 20);

        comPort = new JComboBox<>();
        comPort.setModel(new DefaultComboBoxModel<>(new String[] { "без шифрования", "SSL", "TLS" }));
        add(comPort);
        comPort.setBounds(190, 45, 60, 20);

        JLabel labLogin = new JLabel("Логин");
        add(labLogin);
        labLogin.setBounds(15, 70, 90, 20);
        textLogin = new JTextField();
        add(textLogin);
        textLogin.setBounds(105, 70, 145, 20);

        JLabel labPassword = new JLabel("Пароль");
        add(labPassword);
        labPassword.setBounds(15, 95, 90, 20);
        textPassword = new JPasswordField();
        add(textPassword);
        textPassword.setBounds(105, 95, 145, 20);

        JLabel labDelay = new JLabel("Задержка");
        add(labDelay);
        labDelay.setBounds(15, 120, 90, 20);
        spinDelay = new JSpinner(new SpinnerNumberModel(1.0, 0, 60.0, 0.5));
        add(spinDelay);
        spinDelay.setBounds(105, 120, 80, 20);

        comDelay = new JComboBox<>();
        comDelay.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "сек.", "мин." }));
        add(comDelay);
        comDelay.setBounds(190, 120, 60, 20);
    }

    @Override
    public void setEnabled(boolean b) {
        Component[] components = this.getComponents();

        for (Component component : components) {
            component.setEnabled(b);
        }
    }

    public Map<String,String> getSmtpData() {
        Map<String, String> smtpData = new HashMap<>();

        double delay = Double.parseDouble(spinDelay.getValue().toString());
        boolean sec = comDelay.getSelectedItem().toString().equalsIgnoreCase("сек.");
        long millis = sec ? (long) (delay * 1000) : (long) (delay * 60 * 1000);

        smtpData.put("delay", String.valueOf(millis));
        smtpData.put("host", textSMTP.getText());
        smtpData.put("port", textPort.getText());
        smtpData.put("protocol", comPort.getSelectedItem().toString());
        smtpData.put("login", textLogin.getText());
        smtpData.put("password", String.valueOf(textPassword.getPassword()));

        return smtpData;
    }
}
