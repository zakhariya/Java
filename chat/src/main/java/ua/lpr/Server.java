package ua.lpr;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class Server {
    private static Map<String, Connection> connectionMap= new ConcurrentHashMap<>();

    public static void sendBroadcastMessage(Message message) {
        for (Connection connection : connectionMap.values()) {
            try {
                connection.send(message);
            } catch (IOException e) {
                ConsoleHelper.writeMessage("Не удалось отправить сообщение");
            }
        }
    }

    public static void main(String[] args) {
        ConsoleHelper.writeMessage("Введите порт сервера:");
        int port = ConsoleHelper.readInt();

        try (ServerSocket serverSocket = new ServerSocket(port)) {
            ConsoleHelper.writeMessage("Чат сервер запущен.");

            while (!serverSocket.isClosed()) {
                Socket socket = serverSocket.accept();
                new Handler(socket).start();
            }
        } catch (Exception e) {
            ConsoleHelper.writeMessage("Произошла ошибка при запуске или работе сервера.");
        }
    }

    private static class Handler extends Thread {

        private Socket socket;

        public Handler(Socket socket) {
            this.socket = socket;
        }

        private String serverHandshake(Connection connection) throws IOException, ClassNotFoundException {
            connection.send(new Message(MessageType.NAME_REQUEST));

            Message message = connection.receive();

            if (message.getType() == MessageType.USER_NAME) {
                String name = message.getData();

                if (name != null && name.length() > 0 && !connectionMap.containsKey(name)) {
                    connectionMap.put(name, connection);
                    connection.send(new Message(MessageType.NAME_ACCEPTED));

                    return name;
                }
            }

            return serverHandshake(connection);
        }

        private void serverMainLoop(Connection connection, String userName) throws IOException, ClassNotFoundException {
            while (true) {
                Message message = connection.receive();

                if (message.getType() == MessageType.TEXT) {
                    sendBroadcastMessage(
                            new Message(MessageType.TEXT, String.format("%s: %s", userName, message.getData())));
                } else {
                    ConsoleHelper.writeMessage("Ошибка");
                }
            }
        }

        private void notifyUsers(Connection connection, String userName) throws IOException {
            for (String name : connectionMap.keySet()) {
                if (!userName.equals(name)) {
                    connection.send(new Message(MessageType.USER_ADDED, name));
                }
            }
        }

        @Override
        public void run() {
            ConsoleHelper.writeMessage(String.format("Connection established: %s", socket.getRemoteSocketAddress()));

            String userName = null;

            try (Connection connection = new Connection(socket)) {
                userName = serverHandshake(connection);
                sendBroadcastMessage(new Message(MessageType.USER_ADDED, userName));
                notifyUsers(connection, userName);
                serverMainLoop(connection, userName);
            } catch (IOException | ClassNotFoundException e) {
                if (!e.getMessage().equalsIgnoreCase("Connection reset")){
                    e.printStackTrace();
                }
            }

            if (userName != null) {
                connectionMap.remove(userName);
                sendBroadcastMessage(new Message(MessageType.USER_REMOVED, userName));
            }

            ConsoleHelper.writeMessage("Соединение с " + socket.getRemoteSocketAddress() + " закрыто.");
        }
    }
}