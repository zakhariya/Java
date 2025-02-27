package ua.od.zakhariya.system;

import java.io.IOException;
import java.net.InetAddress;

public class StatusCheck {
    private static final int TIMEOUT = 3 * 1000;

    public static void main(String[] args) {
        long t = System.currentTimeMillis();
        if (ping("192.168.0.1"))
            System.out.println("Сервер доступен");
        else
            System.out.println("Сервер недоступен");
        System.out.println(System.currentTimeMillis() - t);
    }

    private static boolean ping(String addr) {
        try {
            InetAddress address = InetAddress.getByName(addr);

            return address.isReachable(TIMEOUT);
        } catch (IOException exc){
            return false;
        }
    }

    private boolean socketCheck() {
        return false;
    }

    private boolean somethingElseCheck() {
        return false;
    }
}
