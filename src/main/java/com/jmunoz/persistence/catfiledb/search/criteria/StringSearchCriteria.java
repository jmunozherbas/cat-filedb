package com.jmunoz.persistence.catfiledb.search.criteria;

import com.google.gson.JsonObject;

import java.util.List;

public abstract class StringSearchCriteria extends CatSearchCriteria {

    private String mainValue;
    private List<String> listValue;

    public abstract boolean check(JsonObject jsonObject);

    @Override
    public boolean checkCriteria(JsonObject jsonObject) {
        return check(jsonObject);
    }

    public String getMainValue() {
        return mainValue;
    }

    public void setMainValue(String mainValue) {
        this.mainValue = mainValue;
    }

    public List<String> getListValue() {
        return listValue;
    }

    public void setListValue(List<String> listValue) {
        this.listValue = listValue;
    }

}
