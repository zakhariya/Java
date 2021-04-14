package ua.lpr.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import org.springframework.stereotype.Component;
import javax.validation.constraints.Email;

@Component
public class User {

    private int id;

    private String name;

    @JsonProperty(access = JsonProperty.Access.AUTO)
    private String password;

    private String post;

    @Email
    private String email;

    private String viberId;

    private String phone;

    private boolean isDead;


    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPassword() { return password; }

    public void setPassword(String password) { this.password = password; }

    public String getPost() { return post; }

    public void setPost(String post) { this.post = post; }

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

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public boolean isDead() { return isDead; }

    public void setDead(boolean dead) { isDead = dead; }

    public boolean hasValidEmail(){
        if (email == null)
            return false;

        int size = email.length();

         if(size < 5 || !email.contains("@"))
            return false;

        if (email.indexOf("@") == 0 || email.indexOf("@") == size - 1)
            return false;

        if (email.contains("`") || email.contains("~") || email.contains("!") || email.contains("#") ||
                email.contains("$") || email.contains("%") || email.contains("^") || email.contains("&") ||
                email.contains("*") || email.contains("(") || email.contains(")") || email.contains("+") ||
                email.contains("=") || email.contains("{") || email.contains("}") || email.contains("[") ||
                email.contains("]") || email.contains("\\") || email.contains("|") || email.contains(":") ||
                email.contains(":") || email.contains("\"") || email.contains("'") || email.contains("<") ||
                email.contains(">") || email.contains(",") || email.contains("?") || email.contains("/") ||
                email.contains("№"))
            return false;

        String prefix = email.substring(0, email.indexOf("@"));
        int pEnd = prefix.length() - 1;

        if (prefix.indexOf(".") == 0 || prefix.indexOf("-") == 0 || prefix.indexOf("_") == 0)
            return false;

        if (prefix.lastIndexOf(".") == pEnd ||
                prefix.lastIndexOf("-") == pEnd || prefix.lastIndexOf("_") == pEnd)
            return false;

        String suffix = email.substring(email.indexOf("@") + 1, size);
        int sEnd = suffix.length() - 1;

        if (suffix.contains("@") ||
                !suffix.contains("."))
            return false;

        if (suffix.indexOf(".") == 0 || suffix.indexOf("-") == 0 || suffix.indexOf("_") == 0)
            return false;

        if (suffix.lastIndexOf(".") == sEnd ||
                suffix.lastIndexOf("-") == sEnd || suffix.lastIndexOf("_") == sEnd)
            return false;

        if (suffix.contains("1") || suffix.contains("2") || suffix.contains("3") || suffix.contains("4") ||
                suffix.contains("5") || suffix.contains("6") || suffix.contains("7") || suffix.contains("8") ||
                suffix.contains("9") || suffix.contains("0"))
            return false;

        return true;
    }

    @Override
    public String toString() {
        return "User{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", password='" + password + '\'' +
                ", post='" + post + '\'' +
                ", email='" + email + '\'' +
                ", phone='" + phone + '\'' +
                ", isDead=" + isDead +
                ", viberId=" + viberId +
                '}';
    }
}
