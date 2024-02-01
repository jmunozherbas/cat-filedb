package com.jmunoz.persistence.catfiledb.search.builder;

import com.google.gson.JsonObject;
import com.jmunoz.persistence.catfiledb.search.CatFilter;
import com.jmunoz.persistence.catfiledb.search.criteria.DoubleSearchCriteria;

import java.util.ArrayList;
import java.util.List;

public class FilterDoubleBuilder implements FilterBuilder {

    private CatFilter catFilter;

    public FilterDoubleBuilder(String propertyName) {
        this.catFilter = new CatFilter(propertyName);
        this.catFilter.setListCriteria(new ArrayList<>());
    }

    public FilterDoubleBuilder equalTo(Double value) {
        DoubleSearchCriteria isc = new DoubleSearchCriteria() {
            @Override
            public boolean check(JsonObject jsonObject) {
                return getMainValue().equals(getDouble(catFilter.getPropertyName(), jsonObject));
            }
        };
        isc.setMainValue(value);
        this.catFilter.getListCriteria().add(isc);
        return this;
    }

    public FilterDoubleBuilder greaterThan(Double value) {
        DoubleSearchCriteria isc = new DoubleSearchCriteria() {
            @Override
            public boolean check(JsonObject jsonObject) {
                return getDouble(catFilter.getPropertyName(), jsonObject) > getMainValue();
            }
        };
        isc.setMainValue(value);
        this.catFilter.getListCriteria().add(isc);
        return this;
    }

    public FilterDoubleBuilder greaterOrEqualThan(Double value) {
        DoubleSearchCriteria isc = new DoubleSearchCriteria() {
            @Override
            public boolean check(JsonObject jsonObject) {
                return getDouble(catFilter.getPropertyName(), jsonObject) >= getMainValue();
            }
        };
        isc.setMainValue(value);
        this.catFilter.getListCriteria().add(isc);
        return this;
    }

    public FilterDoubleBuilder lowerThan(Double value) {
        DoubleSearchCriteria isc = new DoubleSearchCriteria() {
            @Override
            public boolean check(JsonObject jsonObject) {
                return getDouble(catFilter.getPropertyName(), jsonObject) < getMainValue();
            }
        };
        isc.setMainValue(value);
        this.catFilter.getListCriteria().add(isc);
        return this;
    }

    public FilterDoubleBuilder lowerOrEqualThan(Double value) {
        DoubleSearchCriteria isc = new DoubleSearchCriteria() {
            @Override
            public boolean check(JsonObject jsonObject) {
                return getDouble(catFilter.getPropertyName(), jsonObject) <= getMainValue();
            }
        };
        isc.setMainValue(value);
        this.catFilter.getListCriteria().add(isc);
        return this;
    }

    public FilterDoubleBuilder betweenValues(Double value1, Double value2) {
        DoubleSearchCriteria isc = new DoubleSearchCriteria() {
            @Override
            public boolean check(JsonObject jsonObject) {
                return getDouble(catFilter.getPropertyName(), jsonObject) >= getMainValue() && getDouble(catFilter.getPropertyName(), jsonObject) <= getSecondValue();
            }
        };
        isc.setMainValue(value1);
        isc.setSecondValue(value2);
        this.catFilter.getListCriteria().add(isc);
        return this;
    }

    public FilterDoubleBuilder isInList(List<Double> listValues) {
        DoubleSearchCriteria isc = new DoubleSearchCriteria() {
            @Override
            public boolean check(JsonObject jsonObject) {
                return getListValue().contains(getDouble(catFilter.getPropertyName(), jsonObject));
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
