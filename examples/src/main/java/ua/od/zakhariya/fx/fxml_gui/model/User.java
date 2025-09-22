package ua.od.zakhariya.fx.fxml_gui.model;

public class User {
    private static long usersCount = 0;

    private long id;
    private String name;
    private int age;


    public User(String name, int age) {
        usersCount++;

        this.id = usersCount;
        this.name = name;
        this.age = age;
    }

    public long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public int getAge() {
        return age;
    }
}
