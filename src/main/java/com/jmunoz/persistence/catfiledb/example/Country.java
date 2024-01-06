package com.jmunoz.persistence.catfiledb.example;

import lombok.Getter;
import lombok.Setter;

import java.util.Date;

@Getter
@Setter
public class Country {

    private String name;
    private String nameCode;
    private String currencyCode;
    private String capital;
    private double area;
    private Date dateCreation;
    private int quantityStates;
    private boolean isPartOfUN;
    private double centerLatitude;
    private double centerLongitude;
    private int phoneCode;
}
