package ua.od.zakhariya.patterns.observer;

import java.util.Observable;
import java.util.Observer;

public class Second extends Observable implements Observer {

    @Override
    public void update(Observable o, Object someObject) {
        System.out.println("Second class notified by " + o + " " + someObject);

        setChanged();
        notifyObservers(someObject);
    }

    @Override
    public String toString() {
        return "Second{}";
    }
}
