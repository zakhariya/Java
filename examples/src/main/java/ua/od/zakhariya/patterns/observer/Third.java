package ua.od.zakhariya.patterns.observer;

import java.util.Observable;
import java.util.Observer;

public class Third implements Observer {

    @Override
    public void update(Observable o, Object someObject) {
        System.out.println("Third class notified by " + o + " " + someObject);
    }
}
