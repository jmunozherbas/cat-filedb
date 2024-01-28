package com.jmunoz.persistence.catfiledb;

import java.lang.reflect.Type;
import java.util.Objects;

public class CatClass {

    private String name;
    private String fieldId;
    private Type typeClass;
    private Type typeClassList;

    public CatClass() {
    }

    public CatClass(String name, String fieldId, Type typeClass, Type typeClassList) {
        this.name = name;
        this.fieldId = fieldId;
        this.typeClass = typeClass;
        this.typeClassList = typeClassList;
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

    public Type getTypeClassList() {
        return typeClassList;
    }

    public void setTypeClassList(Type typeClassList) {
        this.typeClassList = typeClassList;
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
