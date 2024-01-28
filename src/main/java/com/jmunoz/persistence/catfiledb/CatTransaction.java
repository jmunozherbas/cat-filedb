package com.jmunoz.persistence.catfiledb;

import com.google.gson.JsonObject;

public class CatTransaction {

    private JsonObject jsonObject;
    private Object object;
    private CatClass catClass;
    private int typeTransaction;
    private Long indexToDelete;

    public JsonObject getJsonObject() {
        return jsonObject;
    }

    public void setJsonObject(JsonObject jsonObject) {
        this.jsonObject = jsonObject;
    }

    public Object getObject() {
        return object;
    }

    public void setObject(Object object) {
        this.object = object;
    }

    public CatClass getCatClass() {
        return catClass;
    }

    public void setCatClass(CatClass catClass) {
        this.catClass = catClass;
    }

    public int getTypeTransaction() {
        return typeTransaction;
    }

    public void setTypeTransaction(int typeTransaction) {
        this.typeTransaction = typeTransaction;
    }

    public Long getIndexToDelete() {
        return indexToDelete;
    }

    public void setIndexToDelete(Long indexToDelete) {
        this.indexToDelete = indexToDelete;
    }
}
