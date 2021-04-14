package ua.lpr.entity;

import java.util.Objects;

public class Recipient {
    private int id;
    private String name;
    private String company;
    private String city;
    private String email;
    private boolean sent;
    private boolean subscribed;
    private String md5;

    public Recipient(String name, String company, String city, String email) {
        this.name = name;
        this.company = company;
        this.city = city;
        this.email = email;
        this.md5 = generateMD5(this.email);
    }

    public Recipient(int id, String name, String company, String city, String email, boolean sent, boolean subscribed) {
        this.id = id;
        this.name = name;
        this.company = company;
        this.city = city;
        this.email = email;
        this.sent = sent;
        this.subscribed = subscribed;
        this.md5 = generateMD5(this.email);

    }

    private String generateMD5(String email) {
        return "";
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getCompany() {
        return company;
    }

    public String getCity() {
        return city;
    }

    public String getEmail() {
        return email;
    }

    public boolean isSent() {
        return sent;
    }

    public void setSent(boolean sent) {
        this.sent = sent;
    }

    public boolean isSubscribed() {
        return subscribed;
    }

    public String getMd5() {
        return md5;
    }

    public void setMd5(String md5) {
        this.md5 = md5;
    }

    @Override
    public String toString() {
        return "Recipient{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", company='" + company + '\'' +
                ", city='" + city + '\'' +
                ", email='" + email + '\'' +
                ", sent=" + sent +
                ", subscribed=" + subscribed +
                ", md5='" + md5 + '\'' +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Recipient recipient = (Recipient) o;
        return id == recipient.id &&
                sent == recipient.sent &&
                subscribed == recipient.subscribed &&
                Objects.equals(name, recipient.name) &&
                Objects.equals(company, recipient.company) &&
                Objects.equals(city, recipient.city) &&
                Objects.equals(email, recipient.email) &&
                Objects.equals(md5, recipient.md5);
    }

    @Override
    public int hashCode() {

        return Objects.hash(id, name, company, city, email, sent, subscribed, md5);
    }
}
