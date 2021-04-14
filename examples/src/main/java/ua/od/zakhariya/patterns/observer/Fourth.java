package ua.od.zakhariya.patterns.observer;

import java.util.Observable;
import java.util.Observer;

public class Fourth implements Observer {
    @Override
    public void update(Observable o, Object someObject) {
        System.out.println("Fourth class notified by " + o + " " + someObject);
    }
}
