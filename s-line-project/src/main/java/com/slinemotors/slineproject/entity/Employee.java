package com.slinemotors.slineproject.entity;

import lombok.Data;

import javax.persistence.*;

@Entity
@Table(name = "employees")
@Data
public class Employee {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    long id;
    private String firstName;
    private String secondName;
    private String lastName;
    private String passportCode;
    private String passportIssuedBy;
    private String phone;
    private String location;

    @ManyToOne
    @JoinColumn(name = "position")
    private Position position;
    private String code;

    @Column(name = "fired")
    private boolean isFired;
}
