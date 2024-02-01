package com.jmunoz.persistence.catfiledb.search.criteria;

import com.google.gson.JsonObject;

import java.util.List;

public abstract class DoubleSearchCriteria extends CatSearchCriteria {

    private Double mainValue;
    private Double secondValue;
    private List<Double> listValue;

    public abstract boolean check(JsonObject jsonObject);

    @Override
    public boolean checkCriteria(JsonObject jsonObject) {
        return check(jsonObject);
    }

    public Double getMainValue() {
        return mainValue;
    }

    public void setMainValue(Double mainValue) {
        this.mainValue = mainValue;
    }

    public Double getSecondValue() {
        return secondValue;
    }

    public void setSecondValue(Double secondValue) {
        this.secondValue = secondValue;
    }

    public List<Double> getListValue() {
        return listValue;
    }

    public void setListValue(List<Double> listValue) {
        this.listValue = listValue;
    }

}
