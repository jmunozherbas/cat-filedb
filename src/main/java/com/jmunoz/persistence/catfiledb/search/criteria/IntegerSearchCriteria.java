package com.jmunoz.persistence.catfiledb.search.criteria;

import com.google.gson.JsonObject;

import java.util.List;

public abstract class IntegerSearchCriteria extends CatSearchCriteria {

    private Integer mainValue;
    private Integer secondValue;
    private List<Integer> listValue;

    public abstract boolean check(JsonObject jsonObject);

    @Override
    public boolean checkCriteria(JsonObject jsonObject) {
        return check(jsonObject);
    }

    public Integer getMainValue() {
        return mainValue;
    }

    public void setMainValue(Integer mainValue) {
        this.mainValue = mainValue;
    }

    public Integer getSecondValue() {
        return secondValue;
    }

    public void setSecondValue(Integer secondValue) {
        this.secondValue = secondValue;
    }

    public List<Integer> getListValue() {
        return listValue;
    }

    public void setListValue(List<Integer> listValue) {
        this.listValue = listValue;
    }

}
