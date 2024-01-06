package com.jmunoz.persistence.catfiledb.example;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Car {

    private String serial;
    private String plate;
    private String color;
    private int year;
    private int axis;
    private String model;
    private String mark;

    @Override
    public String toString() {
        return "Car{" +
                "serial='" + serial + '\'' +
                ", plate='" + plate + '\'' +
                ", color='" + color + '\'' +
                ", year=" + year +
                ", axis=" + axis +
                ", model='" + model + '\'' +
                ", mark='" + mark + '\'' +
                '}';
    }
}
