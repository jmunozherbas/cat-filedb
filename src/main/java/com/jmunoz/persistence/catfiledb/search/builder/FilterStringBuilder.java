package com.jmunoz.persistence.catfiledb.search.builder;

import com.google.gson.JsonObject;
import com.jmunoz.persistence.catfiledb.search.CatFilter;
import com.jmunoz.persistence.catfiledb.search.criteria.StringSearchCriteria;

import java.util.ArrayList;
import java.util.List;

public class FilterStringBuilder implements FilterBuilder {

    private CatFilter catFilter;

    public FilterStringBuilder(String propertyName) {
        this.catFilter = new CatFilter(propertyName);
        this.catFilter.setListCriteria(new ArrayList<>());
    }

    public FilterStringBuilder equalTo(String value) {
        StringSearchCriteria isc = new StringSearchCriteria() {
            @Override
            public boolean check(JsonObject jsonObject) {
                return getMainValue().equals(getString(catFilter.getPropertyName(), jsonObject));
            }
        };
        isc.setMainValue(value);
        this.catFilter.getListCriteria().add(isc);
        return this;
    }

    public FilterStringBuilder equalIgnoreCase(String value) {
        StringSearchCriteria isc = new StringSearchCriteria() {
            @Override
            public boolean check(JsonObject jsonObject) {
                return getMainValue().equalsIgnoreCase(getString(catFilter.getPropertyName(), jsonObject));
            }
        };
        isc.setMainValue(value);
        this.catFilter.getListCriteria().add(isc);
        return this;
    }

    public FilterStringBuilder startsWith(String value) {
        StringSearchCriteria isc = new StringSearchCriteria() {
            @Override
            public boolean check(JsonObject jsonObject) {
                return getMainValue().startsWith(getString(catFilter.getPropertyName(), jsonObject));
            }
        };
        isc.setMainValue(value);
        this.catFilter.getListCriteria().add(isc);
        return this;
    }

    public FilterStringBuilder endsWith(String value) {
        StringSearchCriteria isc = new StringSearchCriteria() {
            @Override
            public boolean check(JsonObject jsonObject) {
                return getMainValue().endsWith(getString(catFilter.getPropertyName(), jsonObject));
            }
        };
        isc.setMainValue(value);
        this.catFilter.getListCriteria().add(isc);
        return this;
    }

    public FilterStringBuilder contains(String value) {
        StringSearchCriteria isc = new StringSearchCriteria() {
            @Override
            public boolean check(JsonObject jsonObject) {
                return getMainValue().contains(getString(catFilter.getPropertyName(), jsonObject));
            }
        };
        isc.setMainValue(value);
        this.catFilter.getListCriteria().add(isc);
        return this;
    }

    public FilterStringBuilder isInList(List<String> listValues) {
        StringSearchCriteria isc = new StringSearchCriteria() {
            @Override
            public boolean check(JsonObject jsonObject) {
                return getListValue().contains(getString(catFilter.getPropertyName(), jsonObject));
            }
        };
        isc.setListValue(listValues);
        this.catFilter.getListCriteria().add(isc);
        return this;
    }

    @Override
    public CatFilter build() {
        return catFilter;
    }
}
