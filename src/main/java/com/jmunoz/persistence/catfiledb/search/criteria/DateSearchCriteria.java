package com.jmunoz.persistence.catfiledb.search.criteria;

import com.google.gson.JsonObject;

import java.util.Date;

public abstract class DateSearchCriteria extends CatSearchCriteria {

    private Date mainValue;
    private Date secondValue;

    public abstract boolean check(JsonObject jsonObject);

    @Override
    public boolean checkCriteria(JsonObject jsonObject) {
        return check(jsonObject);
    }

    public Date getMainValue() {
        return mainValue;
    }

    public void setMainValue(Date mainValue) {
        this.mainValue = mainValue;
    }

    public Date getSecondValue() {
        return secondValue;
    }

    public void setSecondValue(Date secondValue) {
        this.secondValue = secondValue;
    }

}
