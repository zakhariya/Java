package ua.lpr.notificationservice.entity;

import com.fasterxml.jackson.annotation.JsonProperty;

public class Recipient {
    private long id;
    private boolean active;
    private String name;
    private String phone;

    @JsonProperty("viber_id")
    private String viberId;

    private String email;

    @JsonProperty("email_notify")
    private boolean emailNotify;

    @JsonProperty("phone_notify")
    private boolean phoneNotify;

    @JsonProperty("viber_notify")
    private boolean viberNotify;


    //TODO: toString
    @Override
    public String toString() {
        return "Recipient{" +
                "id=" + id +
                ", active=" + active +
                ", name='" + name + '\'' +
                ", phone='" + phone + '\'' +
                ", viberId='" + viberId + '\'' +
                ", email='" + email + '\'' +
                ", emailNotify='" + emailNotify + '\'' +
                ", phoneNotify='" + phoneNotify + '\'' +
                ", viberNotify=" + viberNotify +
                '}';
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public boolean isActive() {
        return active;
    }

    public void setActive(boolean active) {
        this.active = active;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getViberId() {
        return viberId;
    }

    public void setViberId(String viberId) {
        this.viberId = viberId;
    }

    public boolean isEmailNotify() {
        return emailNotify;
    }

    public void setEmailNotify(boolean emailNotify) {
        this.emailNotify = emailNotify;
    }

    public boolean isPhoneNotify() {
        return phoneNotify;
    }

    public void setPhoneNotify(boolean phoneNotify) {
        this.phoneNotify = phoneNotify;
    }

    public boolean isViberNotify() {
        return viberNotify;
    }

    public void setViberNotify(boolean viberNotify) {
        this.viberNotify = viberNotify;
    }
}
