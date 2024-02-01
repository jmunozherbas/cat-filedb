package com.jmunoz.persistence.catfiledb.search;

import com.jmunoz.persistence.catfiledb.search.criteria.CatSearchCriteria;

import java.util.List;

public class CatFilter {

    private String propertyName;
    private List<CatSearchCriteria> listCriteria;

    public CatFilter(String propertyName) {
        this.propertyName = propertyName;
    }

    public String getPropertyName() {
        return propertyName;
    }

    public void setPropertyName(String propertyName) {
        this.propertyName = propertyName;
    }

    public List<CatSearchCriteria> getListCriteria() {
        return listCriteria;
    }

    public void setListCriteria(List<CatSearchCriteria> listCriteria) {
        this.listCriteria = listCriteria;
    }
}
