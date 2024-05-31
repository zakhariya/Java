package ua.od.zakhariya;

import java.io.IOException;

public class Shutdown {
    public static void main(String arg[]) throws IOException {
        Runtime runtime = Runtime.getRuntime();
        Process proc = runtime.exec("shutdown -s -t 0");
        System.exit(0);
    }
}
