package com.jmunoz.persistence.catfiledb.search.builder;

import com.google.gson.JsonObject;
import com.jmunoz.persistence.catfiledb.search.CatFilter;
import com.jmunoz.persistence.catfiledb.search.criteria.IntegerSearchCriteria;

import java.util.ArrayList;
import java.util.List;

public class FilterIntegerBuilder implements FilterBuilder {

    private CatFilter catFilter;

    public FilterIntegerBuilder(String propertyName) {
        this.catFilter = new CatFilter(propertyName);
        this.catFilter.setListCriteria(new ArrayList<>());
    }

    public FilterIntegerBuilder equalTo(Integer value) {
        IntegerSearchCriteria isc = new IntegerSearchCriteria() {
            @Override
            public boolean check(JsonObject jsonObject) {
                return getMainValue().equals(getInt(catFilter.getPropertyName(), jsonObject));
            }
        };
        isc.setMainValue(value);
        this.catFilter.getListCriteria().add(isc);
        return this;
    }

    public FilterIntegerBuilder greaterThan(Integer value) {
        IntegerSearchCriteria isc = new IntegerSearchCriteria() {
            @Override
            public boolean check(JsonObject jsonObject) {
                return getInt(catFilter.getPropertyName(), jsonObject) > getMainValue();
            }
        };
        isc.setMainValue(value);
        this.catFilter.getListCriteria().add(isc);
        return this;
    }

    public FilterIntegerBuilder greaterOrEqualThan(Integer value) {
        IntegerSearchCriteria isc = new IntegerSearchCriteria() {
            @Override
            public boolean check(JsonObject jsonObject) {
                return getInt(catFilter.getPropertyName(), jsonObject) >= getMainValue();
            }
        };
        isc.setMainValue(value);
        this.catFilter.getListCriteria().add(isc);
        return this;
    }

    public FilterIntegerBuilder lowerThan(Integer value) {
        IntegerSearchCriteria isc = new IntegerSearchCriteria() {
            @Override
            public boolean check(JsonObject jsonObject) {
                return getInt(catFilter.getPropertyName(), jsonObject) < getMainValue();
            }
        };
        isc.setMainValue(value);
        this.catFilter.getListCriteria().add(isc);
        return this;
    }

    public FilterIntegerBuilder lowerOrEqualThan(Integer value) {
        IntegerSearchCriteria isc = new IntegerSearchCriteria() {
            @Override
            public boolean check(JsonObject jsonObject) {
                return getInt(catFilter.getPropertyName(), jsonObject) <= getMainValue();
            }
        };
        isc.setMainValue(value);
        this.catFilter.getListCriteria().add(isc);
        return this;
    }

    public FilterIntegerBuilder betweenValues(Integer value1, Integer value2) {
        IntegerSearchCriteria isc = new IntegerSearchCriteria() {
            @Override
            public boolean check(JsonObject jsonObject) {
                return getInt(catFilter.getPropertyName(), jsonObject) >= getMainValue() && getInt(catFilter.getPropertyName(), jsonObject) <= getSecondValue();
            }
        };
        isc.setMainValue(value1);
        isc.setSecondValue(value2);
        this.catFilter.getListCriteria().add(isc);
        return this;
    }

    public FilterIntegerBuilder isInList(List<Integer> listValues) {
        IntegerSearchCriteria isc = new IntegerSearchCriteria() {
            @Override
            public boolean check(JsonObject jsonObject) {
                return getListValue().contains(getInt(catFilter.getPropertyName(), jsonObject));
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
