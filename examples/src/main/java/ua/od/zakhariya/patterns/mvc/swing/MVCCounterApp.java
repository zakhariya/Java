package ua.od.zakhariya.patterns.mvc.swing;

import javax.swing.*;

public class MVCCounterApp {
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            CounterModel model = new CounterModel();
            CounterView view = new CounterView();
            CounterController controller = new CounterController(model, view);
        });
    }
}
