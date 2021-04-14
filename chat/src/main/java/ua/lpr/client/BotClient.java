package ua.lpr.client;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Calendar;

import ua.lpr.ConsoleHelper;

public class BotClient extends Client {

    @Override
    protected SocketThread getSocketThread() {
        return new BotSocketThread();
    }

    @Override
    protected boolean shouldSendTextFromConsole() {
        return false;
    }

    @Override
    protected String getUserName() {
        return "date_bot_" + (int) (Math.random() * 100.0);
    }

    public static void main(String[] args) {
        new BotClient().run();
    }

    public class BotSocketThread extends SocketThread {

        @Override
        protected void clientMainLoop() throws IOException, ClassNotFoundException {
            sendTextMessage("Привет чатику. Я бот. Понимаю команды: дата, день, месяц, год, время, час, минуты, секунды.");
            super.clientMainLoop();
        }

        @Override
        protected void processIncomingMessage(String message) {
            ConsoleHelper.writeMessage(message);

            if (message == null || !message.contains(": ")) {
                return;
            }

            String sender = message.substring(0, message.indexOf(": "));
            String text = message.substring(message.indexOf(": ") + 2);
            String format = null;

            switch (text) {
                case "дата": {
                    format = "d.MM.YYYY";
                    break;
                }
                case "день": {
                    format = "d";
                    break;
                }
                case "месяц": {
                    format = "MMMM";
                    break;
                }
                case "год": {
                    format = "YYYY";
                    break;
                }
                case "время": {
                    format = "H:mm:ss";
                    break;
                }
                case "час": {
                    format = "H";
                    break;
                }
                case "минуты": {
                    format = "m";
                    break;
                }
                case "секунды": {
                    format = "s";
                    break;
                }
            }

            if (format != null) {
                String answer = new SimpleDateFormat(format).format(Calendar.getInstance().getTime());
                BotClient.this.sendTextMessage("Информация для " + sender + ": " + answer);
            }
        }
    }
}