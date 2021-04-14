package ua.od.zakhariya.patterns.observer;

import java.util.Observable;

public class First extends Observable {

    public void someMethod() {
        SomeObject object = new SomeObject();
        System.out.println("First class created some object & notify observers");

        setChanged();
        notifyObservers(object);
    }

    @Override
    public String toString() {
        return "First{}";
    }

    private class SomeObject {
        @Override
        public String toString() {
            return "SomeObject{}";
        }
    }
}
