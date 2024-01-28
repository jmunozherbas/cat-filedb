package com.jmunoz.persistence.catfiledb.search;

import java.util.List;

public class IntegerSearchCriteria extends CatSearchCriteria{

    public enum Criteria {
        EQUAL,GREATER,LOWER,BETWEEN
    }

    @Override
    public List<Integer> filter() {
        return null;
    }

}
