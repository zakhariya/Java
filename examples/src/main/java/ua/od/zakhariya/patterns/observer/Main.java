package ua.od.zakhariya.patterns.observer;

public class Main {
    public static void main(String[] args) {
        First first = new First();
        Second second = new Second();
        Third third = new Third();
        Fourth fourth = new Fourth();

        first.addObserver(second);
        second.addObserver(third);

        first.addObserver(fourth);
        second.addObserver(fourth);

        first.someMethod();
    }
}
