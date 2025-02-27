package ua.od.zakhariya.system;

import java.io.IOException;
import java.net.InetAddress;
import java.time.Duration;
import java.time.Instant;
import java.util.concurrent.TimeUnit;

public class StatusCheck {
    private static final int TIMEOUT = 3 * 1000;
    private static final String host = "192.168.0.1";

    public static void main(String[] args) {
        System.out.println(pingVer1(host));

        try {
            System.out.println(pingVer2(host));
        } catch (IOException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        System.out.println(pingVer3(host));


    }

    private static boolean pingVer1(String host) {
        try {
            InetAddress address = InetAddress.getByName(host); // getByAddress(new byte[] { 127, 0, 0, 1 });

            return address.isReachable(TIMEOUT);
        } catch (IOException exc){
            return false;
        }
    }

    /**
     *
     * @param host
     * @return true means ping success,false means ping fail.
     * @throws IOException
     * @throws InterruptedException
     */
    private static boolean pingVer2(String host) throws IOException, InterruptedException {
        boolean isWindows = System.getProperty("os.name").toLowerCase().contains("win");

        ProcessBuilder processBuilder = new ProcessBuilder("ping", isWindows? "-n" : "-c", "1", host);
        Process proc = processBuilder.start();
        return proc.waitFor(200, TimeUnit.MILLISECONDS);
    }

//    https://docs.oracle.com/javase/8/docs/api/java/time/Duration.html
    public static Duration pingVer3(String host) {
        Instant startTime = Instant.now();
        try {
            InetAddress address = InetAddress.getByName(host);
            if (address.isReachable(1000)) {
                return Duration.between(startTime, Instant.now());
            }
        } catch (IOException e) {
            // Host not available, nothing to do here
        }
        return Duration.ofDays(1);
    }

    private boolean socketCheck() {
        // see later: https://www.mindprod.com/jgloss/ping.html

        return false;
    }

    private boolean somethingElseCheck() {
        return false;
    }
}
