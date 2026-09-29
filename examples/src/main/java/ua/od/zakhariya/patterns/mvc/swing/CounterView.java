package ua.od.zakhariya.patterns.mvc.swing;



import javax.swing.*;
import java.awt.event.ActionListener;
import java.util.Observable;
import java.util.Observer;

public class CounterView extends JFrame implements Observer {
    private JLabel countLabel;
    private JButton incrementButton;
    private JButton decrementButton;
    private JButton resetButton;

    public CounterView() {
        setTitle("MVC Counter");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(300, 200);
        setLocationRelativeTo(null); // Center the window

        JPanel panel = new JPanel();
        countLabel = new JLabel("Count: 0");
        incrementButton = new JButton("Increment");
        decrementButton = new JButton("Decrement");
        resetButton = new JButton("Reset");

        panel.add(countLabel);
        panel.add(incrementButton);
        panel.add(decrementButton);
        panel.add(resetButton);

        add(panel);
        setVisible(true);
    }

    public void addIncrementListener(ActionListener listener) {
        incrementButton.addActionListener(listener);
    }

    public void addDecrementListener(ActionListener listener) {
        decrementButton.addActionListener(listener);
    }

    public void addResetListener(ActionListener listener) {
        resetButton.addActionListener(listener);
    }

    @Override
    public void update(Observable o, Object arg) {
        if (o instanceof CounterModel) {
            CounterModel model = (CounterModel) o;
            countLabel.setText("Count: " + model.getCount());
        }
    }
}