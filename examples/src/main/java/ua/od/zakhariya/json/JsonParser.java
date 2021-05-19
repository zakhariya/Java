package ua.od.zakhariya.json;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.IOException;
import java.io.StringWriter;
import java.util.List;

public class JsonParser {

    public static void main(String[] args) throws IOException {
        String json = "{" +
                "\"id\":3," +
                "\"login\":\"Sasha\"," +
                "\"password\":\"VerySecuredPassword\"" +
                "}";

        StringWriter writer = new StringWriter();
        writer.append(json);

        User user = new User(5, "Olha", "qwerty");

        convertToJSON(writer, user);

        System.out.println(writer.toString());
        System.out.println(convertToUser(json));

        json = "[{\"id\":3,\"login\":\"Sasha\",\"password\":\"VerySecuredPassword\"},{\"id\":5,\"login\":\"Olha\",\"password\":\"qwerty\"}]";

        System.out.println(convertToUserList(json));
    }

    private static User convertToUser(String json) throws IOException {
        return new ObjectMapper().readValue(json, User.class);
    }

    private static List<User> convertToUserList(String json) throws IOException {
        return new ObjectMapper().readValue(json, new TypeReference<List<User>>(){});
    }

    private static void convertToJSON(StringWriter writer, Object object) throws IOException {
        ObjectMapper mapper = new ObjectMapper();
        mapper.writeValue(writer, object);
    }



    private static class User {
        private int id;
        private String login;
        private String password;

        public User() {
        }

        public User(int id, String login, String password) {
            this.id = id;
            this.login = login;
            this.password = password;
        }

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public String getLogin() {
            return login;
        }

        public void setLogin(String login) {
            this.login = login;
        }

        public String getPassword() {
            return password;
        }

        public void setPassword(String password) {
            this.password = password;
        }

        @Override
        public String toString() {
            return "User{" +
                    "id=" + id +
                    ", login='" + login + '\'' +
                    ", password='" + password + '\'' +
                    '}';
        }
    }
}
