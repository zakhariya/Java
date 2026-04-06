package ua.od.zakhariya.system;

import java.util.Scanner;

public class TerminalApp {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        String command;

        System.out.println("Java Terminal Started. Type 'exit' to quit.");

        while (true) {
            System.out.print("> "); // Промпт
            command = scanner.nextLine();

            if ("exit".equalsIgnoreCase(command)) {
                break;
            } else if ("help".equalsIgnoreCase(command)) {
                System.out.println("Available commands: help, exit");
            } else {
                System.out.println("Unknown command: " + command);
            }
        }
        System.out.println("Goodbye!");
    }
}

