package ua.od.zakhariya.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonKey;
import lombok.Data;
import ua.od.zakhariya.util.CustomHttpClient;

import java.io.IOException;
import java.net.InetAddress;
import java.security.KeyManagementException;
import java.security.NoSuchAlgorithmException;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class Computer {
    @JsonKey
    private String name;
    private String host;
    private int port;
    private String url;
    private boolean online;

    public Computer() {
        //TODO:remove
//        System.out.println("First constructor");
//        try {
//            Thread.sleep(500);
//        } catch (InterruptedException e) {
//            e.printStackTrace();
//        }

        startCheckingThread();
    }

    public Computer(String name, String host, int port, String url) {
        this.name = name;
        this.host = host;
        this.port = port;
        this.url = url;

        //TODO:remove
//        System.out.println("Second constructor");
//        try {
//            Thread.sleep(500);
//        } catch (InterruptedException e) {
//            e.printStackTrace();
//        }

        startCheckingThread();
    }

    @JsonIgnore
    public String shutdown() {
        String url = "https://" + host + ":" + port + "/" + this.url;
        String message = "";

        int code = 0;

        try {
            code = CustomHttpClient.simpleGet(url);
        } catch (NoSuchAlgorithmException | KeyManagementException | IOException e) {
            e.printStackTrace();

            return "Something went wrong. " + e.getLocalizedMessage();
        }

        switch (code){
            case 200:
                message = "Successful";
                break;
            case 400:
                message = "Bad request";
                break;
            case 403:
                message = "Forbidden";
                break;
            case 404:
                message = "Not found";
                break;
            default:
                message = "No any response";
                break;
        }

        System.out.println(url + " " + code);


        return message;
    }

    @JsonIgnore
    public boolean isOnline() {
        return online;
    }

    private void startCheckingThread() {

        //TODO:remove
//        System.out.println("Computer: checking started " + name);
//        try {
//            Thread.sleep(1000);
//        } catch (InterruptedException e) {
//            e.printStackTrace();
//        }

        new Thread(new Runnable() {
            @Override
            public void run() {
                //TODO: change loop conditions
                while (!Thread.currentThread().isInterrupted()) {
                    for (int i = 0; i < 1; i++) {
                        try {
                            InetAddress address = InetAddress.getByName(host);

                            if (!address.isReachable(100)) {
                                online = false;
                            } else {
                                online = true;
                            }
                        } catch (IOException ex){
                            online = false;
                        }
                    }

                    try {
                        Thread.sleep(250);
                    } catch (InterruptedException ex) {
                        ex.printStackTrace();
                    }

                    //TODO: remove
//                    System.out.println(name + " checking in thread: " + Thread.currentThread().getId() + " object != null: " + (this != null));
//                    try {
//                        Thread.sleep(500);
//                    } catch (InterruptedException e) {
//                        e.printStackTrace();
//                    }
                }
            }
        }).start();
    }
}
