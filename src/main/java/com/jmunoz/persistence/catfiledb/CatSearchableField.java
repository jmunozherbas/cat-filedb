package com.jmunoz.persistence.catfiledb;

import com.google.gson.annotations.SerializedName;

public class CatSearchableField {

    @SerializedName("f")
    private String field;
    @SerializedName("v")
    private String value;
    @SerializedName("tv")
    private byte typeValue;

}
