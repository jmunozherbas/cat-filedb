package com.jmunoz.persistence.catfiledb.search;

import java.util.List;

public abstract class CatSearchCriteria {

    private String name;
    public abstract List<Integer> filter();

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
