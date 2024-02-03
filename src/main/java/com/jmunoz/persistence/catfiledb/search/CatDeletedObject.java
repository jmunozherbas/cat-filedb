package com.jmunoz.persistence.catfiledb.search;

import com.google.gson.annotations.SerializedName;

import java.util.Date;

public class CatDeletedObject {

    @SerializedName("i")
    private int indexFile;
    @SerializedName("k")
    private String keyObject;
    @SerializedName("d")
    private Date dateDeleted;
    @SerializedName("r")
    private byte reasonDelete;

    public int getIndexFile() {
        return indexFile;
    }

    public void setIndexFile(int indexFile) {
        this.indexFile = indexFile;
    }

    public String getKeyObject() {
        return keyObject;
    }

    public void setKeyObject(String keyObject) {
        this.keyObject = keyObject;
    }

    public Date getDateDeleted() {
        return dateDeleted;
    }

    public void setDateDeleted(Date dateDeleted) {
        this.dateDeleted = dateDeleted;
    }

    public byte getReasonDelete() {
        return reasonDelete;
    }

    public void setReasonDelete(byte reasonDelete) {
        this.reasonDelete = reasonDelete;
    }
}
