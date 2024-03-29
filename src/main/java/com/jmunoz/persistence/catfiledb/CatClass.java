package com.jmunoz.persistence.catfiledb;

import java.lang.reflect.Type;
import java.util.Objects;

public class CatClass {

    private String name;
    private String fieldId;
    private boolean saveNoPrimitiveData;
    private Type typeClass;

    public CatClass() {
    }

    public CatClass(String name, String fieldId, boolean saveNoPrimitiveData, Type typeClass) {
        this.name = name;
        this.fieldId = fieldId;
        this.saveNoPrimitiveData = saveNoPrimitiveData;
        this.typeClass = typeClass;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getFieldId() {
        return fieldId;
    }

    public void setFieldId(String fieldId) {
        this.fieldId = fieldId;
    }

    public Type getTypeClass() {
        return typeClass;
    }

    public void setTypeClass(Type typeClass) {
        this.typeClass = typeClass;
    }

    public boolean isSaveNoPrimitiveData() {
        return saveNoPrimitiveData;
    }

    public void setSaveNoPrimitiveData(boolean saveNoPrimitiveData) {
        this.saveNoPrimitiveData = saveNoPrimitiveData;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        CatClass catClass = (CatClass) o;
        return name.equals(catClass.name) &&
                fieldId.equals(catClass.fieldId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name, fieldId);
    }
}
