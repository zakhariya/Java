package ua.od.zakhariya.patterns.chain_of_responsibility;

public interface Logger {
    void inform(String message, int level);

    void setNext(Logger next);

    void info(String message);
}