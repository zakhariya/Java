package ua.od.zakhariya.serialization.jackson.model;

import lombok.Data;

import java.util.Date;

@Data
public class Car {
    private String color;
    private String type;
    private Date datePurchased;

    public Car() {
    }

    public Car(String color, String type) {
        this.color = color;
        this.type = type;
    }
}
