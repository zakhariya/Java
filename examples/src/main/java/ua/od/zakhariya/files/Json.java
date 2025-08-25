package ua.od.zakhariya.files;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.core.JsonGenerationException;
import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;


public class Json {

    public static void main(String[] args) throws JsonGenerationException, JsonMappingException, IOException {
        Json example = new Json();

        example.writeJson();
        example.readJson();
    }

    private void writeJson() throws JsonGenerationException, JsonMappingException, IOException {
        ObjectMapper mapper = new ObjectMapper();
        List<String> messages = new ArrayList<>();

        messages.add("First message");
        messages.add("Second message");

        User user = new User("Tip", 55, messages);

        //Object to JSON in file
        mapper.writeValue(new File("d:\\user.json"), user);

        //Object to JSON in String
        String jsonInString = mapper.writeValueAsString(user);

        System.out.println("Write to string: " + jsonInString);
    }

    private void readJson() throws JsonParseException, JsonMappingException, IOException {
        ObjectMapper mapper = new ObjectMapper();

        //JSON from file to Object
        User user = mapper.readValue(new File("d:\\user.json"), User.class);

        System.out.println("From file: " + user.toString());

        //JSON from String to Object
        String jsonInString = "{\"name\" : \"mkyong\"}";
        user = mapper.readValue(jsonInString, User.class);

        System.out.println("From string: " + user.toString());
    }

    private class User {
        private String name;
        private int age;
        private List<String> messages;

        @JsonCreator
        public User(@JsonProperty("name") String name, @JsonProperty("age") int age,
                    @JsonProperty("messages") List<String> messages) {
            super();
            this.name = name;
            this.age = age;
            this.messages = messages;
        }

        @Override
        public String toString() {
            return "User{"
                    + "name=" + name
                    + ", age=" + age
                    + ", messages=" + messages
                    + "}";
        }
    }
}