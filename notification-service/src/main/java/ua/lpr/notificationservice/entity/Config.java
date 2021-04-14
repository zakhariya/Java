package ua.lpr.notificationservice.entity;

import com.fasterxml.jackson.annotation.JsonProperty;
import org.springframework.stereotype.Component;

@Component
public class Config {

    @JsonProperty("param")
    private String name;

    @JsonProperty("value")
    private String value;

    //TODO: toString
    @Override
    public String toString() {
        return "Config{" +
                "name='" + name + '\'' +
                ", value='" + value + '\'' +
                '}';
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }
}