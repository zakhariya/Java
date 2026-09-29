package ua.od.zakhariya.patterns.mvc.swing;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class CounterController {
    private CounterModel model;
    private CounterView view;

    public CounterController(CounterModel model, CounterView view) {
        this.model = model;
        this.view = view;

        // Register the view as an observer of the model
        model.addObserver(view);

        // Attach listeners to view components
        this.view.addIncrementListener(new IncrementListener());
        this.view.addDecrementListener(new DecrementListener());
        this.view.addResetListener(new ResetListener());
    }

    class IncrementListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            model.increment();
        }
    }

    class DecrementListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            model.decrement();
        }
    }

    class ResetListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            model.reset();
        }
    }
}
