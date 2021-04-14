package com.slinemotors.slineproject.entity;

import lombok.Data;

import javax.persistence.*;

@Entity
@Table(name = "positions")
@Data
public class Position {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int id;
    private String name;
}
