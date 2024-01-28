package com.jmunoz.persistence.catfiledb;

public class CatSession {

    private String hashSession;
    private String description;
    private Long dateCreation;

    public String getHashSession() {
        return hashSession;
    }

    public void setHashSession(String hashSession) {
        this.hashSession = hashSession;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Long getDateCreation() {
        return dateCreation;
    }

    public void setDateCreation(Long dateCreation) {
        this.dateCreation = dateCreation;
    }
}
