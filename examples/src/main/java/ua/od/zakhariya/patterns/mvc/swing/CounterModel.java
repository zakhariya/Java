package ua.od.zakhariya.patterns.mvc.swing;

import java.util.Observable;

public class CounterModel extends Observable {
    private int count;

    public CounterModel() {
        this.count = 0;
    }

    public int getCount() {
        return count;
    }

    public void increment() {
        count++;
        setChanged(); // Mark that the model has changed
        notifyObservers(); // Notify registered observers (views)
    }

    public void decrement() {
        count--;
        setChanged();
        notifyObservers();
    }

    public void reset() {
        count = 0;
        setChanged();
        notifyObservers();
    }
}