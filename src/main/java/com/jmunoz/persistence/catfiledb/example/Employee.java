package com.jmunoz.persistence.catfiledb.example;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Employee {

    private String code;
    private String name;
    private int area;
    private double salary;
    private boolean married;
    private String gender;
    private String documentID;
    private String position;

}
