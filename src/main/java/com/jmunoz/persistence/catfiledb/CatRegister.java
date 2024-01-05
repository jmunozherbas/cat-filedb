package com.jmunoz.persistence.catfiledb;

import com.google.gson.annotations.SerializedName;

import java.util.List;
import java.util.Objects;

public class CatRegister {

    @SerializedName("fi")
    private int fileIndex;
    @SerializedName("ib")
    private long indexFirstByte;
    @SerializedName("le")
    private long length;
    @SerializedName("id")
    private String registerFieldId;
    @SerializedName("sl")
    private List<CatSearchableField> searchableFieldList;

    public CatRegister() {

    }

    public CatRegister(String registerFieldId) {
        this.registerFieldId = registerFieldId;
    }

    public int getFileIndex() {
        return fileIndex;
    }

    public void setFileIndex(int fileIndex) {
        this.fileIndex = fileIndex;
    }

    public long getIndexFirstByte() {
        return indexFirstByte;
    }

    public void setIndexFirstByte(long indexFirstByte) {
        this.indexFirstByte = indexFirstByte;
    }

    public long getLength() {
        return length;
    }

    public void setLength(long length) {
        this.length = length;
    }

    public String getRegisterFieldId() {
        return registerFieldId;
    }

    public void setRegisterFieldId(String registerFieldId) {
        this.registerFieldId = registerFieldId;
    }

    public List<CatSearchableField> getSearchableFieldList() {
        return searchableFieldList;
    }

    public void setSearchableFieldList(List<CatSearchableField> searchableFieldList) {
        this.searchableFieldList = searchableFieldList;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        CatRegister that = (CatRegister) o;
        return registerFieldId.equals(that.registerFieldId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(registerFieldId);
    }
}
