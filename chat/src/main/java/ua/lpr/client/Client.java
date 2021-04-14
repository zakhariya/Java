package ua.lpr.client;

import java.io.IOException;
import java.net.Socket;

import ua.lpr.ConsoleHelper;
import ua.lpr.Connection;
import ua.lpr.Message;
import ua.lpr.MessageType;

public class Client {
    protected Connection connection;
    private volatile boolean clientConnected = false;

    protected String getServerAddress() {
        ConsoleHelper.writeMessage("Введите адрес сервера");
        return ConsoleHelper.readString();
    }

    protected int getServerPort() {
        ConsoleHelper.writeMessage("Введите порт");
        return ConsoleHelper.readInt();
    }

    protected String getUserName() {
        ConsoleHelper.writeMessage("Введите свое имя");
        return ConsoleHelper.readString();
    }

    protected boolean shouldSendTextFromConsole() {
        return true;
    }

    protected SocketThread getSocketThread() {
        return new SocketThread();
    }

    protected void sendTextMessage(String text) {
        try {
            connection.send(new Message(MessageType.TEXT, text));
        } catch (IOException e) {
            ConsoleHelper.writeMessage("Ошибка " + e.getMessage());
            clientConnected = false;
        }
    }

    public void run() {
        SocketThread socketThread = getSocketThread();
        socketThread.setDaemon(true);
        socketThread.start();

        try {
            synchronized (this) {
                wait();
            }
        } catch (InterruptedException e) {
            ConsoleHelper.writeMessage("Произошла ошибка");
            return;
        }

        String message = clientConnected ? "Соединение установлено. Для выхода наберите команду 'exit'."
                : "Произошла ошибка во время работы клиента.";

        ConsoleHelper.writeMessage(message);

        while (clientConnected) {
            String s = ConsoleHelper.readString();

            if (s.equalsIgnoreCase("exit")) {
                break;
            }

            if (shouldSendTextFromConsole()) {
                sendTextMessage(s);
            }
        }
    }

    public static void main(String[] args) {
        new Client().run();
    }


    public class SocketThread extends Thread {

        protected void clientHandshake() throws IOException, ClassNotFoundException {
            while (true) {
                Message message = connection.receive();

                if (message.getType() == MessageType.NAME_REQUEST) {
                    connection.send(new Message(MessageType.USER_NAME, getUserName()));
                } else if (message.getType() == MessageType.NAME_ACCEPTED) {
                    notifyConnectionStatusChanged(true);
                    return;
                } else {
                    throw new IOException("Unexpected MessageType");
                }
            }
        }

        protected void clientMainLoop() throws IOException, ClassNotFoundException {
            while (clientConnected) {
                Message message = connection.receive();

                if (message.getType() == MessageType.TEXT) {
                    processIncomingMessage(message.getData());
                } else if (MessageType.USER_ADDED == message.getType()) {
                    informAboutAddingNewUser(message.getData());
                } else if (MessageType.USER_REMOVED == message.getType()) {
                    informAboutDeletingNewUser(message.getData());
                } else {
                    throw new IOException("Unexpected MessageType");
                }
            }
        }

        protected void processIncomingMessage(String message) {
            ConsoleHelper.writeMessage(message);
        }

        protected void informAboutAddingNewUser(String userName) {
            ConsoleHelper.writeMessage("Учасник \"" + userName + "\" присоединился к чату.");
        }

        protected void informAboutDeletingNewUser(String userName) {
            ConsoleHelper.writeMessage("Учасник \"" + userName + "\" покинул чат.");
        }

        protected void notifyConnectionStatusChanged(boolean clientConnected) {
            Client.this.clientConnected = clientConnected;

            synchronized (Client.this) {
                Client.this.notify();
            }
        }

        @Override
        public void run() {
            String address = getServerAddress();
            int port = getServerPort();

            try (Socket socket = new Socket(address, port)) {
                connection = new Connection(socket);
                clientHandshake();
                clientMainLoop();
            } catch (IOException | ClassNotFoundException e) {
                notifyConnectionStatusChanged(false);
            }
        }
    }
}