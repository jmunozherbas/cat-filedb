package com.jmunoz.persistence.catfiledb.search.criteria;

import com.google.gson.Gson;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;

import java.util.Date;

public abstract class CatSearchCriteria{

    public abstract boolean checkCriteria(JsonObject jsonObject);

    public Integer getInt(String propertyName, JsonObject jsonObject){
        JsonElement je = getValidatedProperty(propertyName, jsonObject);
        if (je != null) {
            return je.getAsInt();
        }
        return null;
    }

    public String getString(String propertyName, JsonObject jsonObject){
        JsonElement je = getValidatedProperty(propertyName, jsonObject);
        if (je != null) {
            return je.getAsString();
        }
        return null;
    }

    public Date getDate(String propertyName, JsonObject jsonObject){
        JsonElement je = getValidatedProperty(propertyName, jsonObject);
        if (je != null) {
            return new Gson().fromJson(je, Date.class);
        }
        return null;
    }

    public Double getDouble(String propertyName, JsonObject jsonObject){
        JsonElement je = getValidatedProperty(propertyName, jsonObject);
        if (je != null) {
            return je.getAsDouble();
        }
        return null;
    }

    public Boolean getBoolean(String propertyName, JsonObject jsonObject){
        JsonElement je = getValidatedProperty(propertyName, jsonObject);
        if (je != null) {
            return je.getAsBoolean();
        }
        return null;
    }

    private JsonElement getValidatedProperty(String propertyName, JsonObject jsonObject) {
        if (jsonObject.has(propertyName)) {
            return jsonObject.get(propertyName);
        }
        return null;
    }
}
