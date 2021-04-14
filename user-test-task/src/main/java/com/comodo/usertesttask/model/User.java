package com.comodo.usertesttask.model;

import javax.persistence.*;
import java.sql.Date;

@Entity
@Table(name="users")
public class User {

    @Id
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    @Column(name="id")
    private long userId;

    @Column(name="first_name")
    private String firstName;

    @Column(name="last_name")
    private String lastName;

    @Column(name="birth_day")
    private  Date birthDay;

    private String gender;


    public long getUserId() { return userId; }

    public void setUserId(long userId) { this.userId = userId; }

    public String getFirstName() { return firstName; }

    public void setFirstName(String firstName) { this.firstName = firstName; }

    public String getLastName() { return lastName; }

    public void setLastName(String lastName) { this.lastName = lastName; }

    public Date getBirthDay() { return birthDay; }

    public void setBirthDay(Date birthDay) { this.birthDay = birthDay; }

    public String getGender() { return gender; }

    public void setGender(String gender) { this.gender = gender; }
}
