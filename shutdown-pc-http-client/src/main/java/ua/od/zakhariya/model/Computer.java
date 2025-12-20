package ua.od.zakhariya.model;

import lombok.Data;

@Data
public class Computer {
    private String name;
    private String ip;
    private int port;
    private String url;

    public Computer() {
    }

    public Computer(String name, String ip, int port, String url) {
        this.name = name;
        this.ip = ip;
        this.port = port;
        this.url = url;
    }
}
