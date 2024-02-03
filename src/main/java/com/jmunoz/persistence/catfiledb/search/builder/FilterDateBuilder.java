package com.jmunoz.persistence.catfiledb.search.builder;

import com.google.gson.JsonObject;
import com.jmunoz.persistence.catfiledb.search.CatFilter;
import com.jmunoz.persistence.catfiledb.search.criteria.DateSearchCriteria;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

public class FilterDateBuilder implements FilterBuilder {

    private CatFilter catFilter;

    public FilterDateBuilder(String propertyName) {
        this.catFilter = new CatFilter(propertyName);
        this.catFilter.setListCriteria(new ArrayList<>());
    }

    public FilterDateBuilder equalToDate(Date value) {
        DateSearchCriteria isc = new DateSearchCriteria() {
            @Override
            public boolean check(JsonObject jsonObject) {
                return equalDate(getMainValue(), getDate(catFilter.getPropertyName(), jsonObject));
            }
        };
        isc.setMainValue(value);
        this.catFilter.getListCriteria().add(isc);
        return this;
    }

    public FilterDateBuilder equalToDateTime(Date value) {
        DateSearchCriteria isc = new DateSearchCriteria() {
            @Override
            public boolean check(JsonObject jsonObject) {
                return equalDateTime(getMainValue(), getDate(catFilter.getPropertyName(), jsonObject));
            }
        };
        isc.setMainValue(value);
        this.catFilter.getListCriteria().add(isc);
        return this;
    }

    public FilterDateBuilder equalToDateTimeMillis(Date value) {
        DateSearchCriteria isc = new DateSearchCriteria() {
            @Override
            public boolean check(JsonObject jsonObject) {
                return equalDateTimeMillis(getMainValue(), getDate(catFilter.getPropertyName(), jsonObject));
            }
        };
        isc.setMainValue(value);
        this.catFilter.getListCriteria().add(isc);
        return this;
    }

    public FilterDateBuilder afterThanDate(Date value) {
        DateSearchCriteria isc = new DateSearchCriteria() {
            @Override
            public boolean check(JsonObject jsonObject) {
                return valueDate_yyyyMMdd(getDate(catFilter.getPropertyName(), jsonObject)) > valueDate_yyyyMMdd(getMainValue());
            }
        };
        isc.setMainValue(value);
        this.catFilter.getListCriteria().add(isc);
        return this;
    }

    public FilterDateBuilder afterOrEqualThanDate(Date value) {
        DateSearchCriteria isc = new DateSearchCriteria() {
            @Override
            public boolean check(JsonObject jsonObject) {
                return valueDate_yyyyMMdd(getDate(catFilter.getPropertyName(), jsonObject)) >= valueDate_yyyyMMdd(getMainValue());
            }
        };
        isc.setMainValue(value);
        this.catFilter.getListCriteria().add(isc);
        return this;
    }

    public FilterDateBuilder beforeThanDate(Date value) {
        DateSearchCriteria isc = new DateSearchCriteria() {
            @Override
            public boolean check(JsonObject jsonObject) {
                return valueDate_yyyyMMdd(getDate(catFilter.getPropertyName(), jsonObject)) < valueDate_yyyyMMdd(getMainValue());
            }
        };
        isc.setMainValue(value);
        this.catFilter.getListCriteria().add(isc);
        return this;
    }

    public FilterDateBuilder beforeOrEqualThanDate(Date value) {
        DateSearchCriteria isc = new DateSearchCriteria() {
            @Override
            public boolean check(JsonObject jsonObject) {
                return valueDate_yyyyMMdd(getDate(catFilter.getPropertyName(), jsonObject)) <= valueDate_yyyyMMdd(getMainValue());
            }
        };
        isc.setMainValue(value);
        this.catFilter.getListCriteria().add(isc);
        return this;
    }

    public FilterDateBuilder betweenDate(Date value1, Date value2) {
        DateSearchCriteria isc = new DateSearchCriteria() {
            @Override
            public boolean check(JsonObject jsonObject) {
                Long date1 = valueDate_yyyyMMdd(getMainValue());
                Long date2 = valueDate_yyyyMMdd(getSecondValue());
                Long objDate = valueDate_yyyyMMdd(getDate(catFilter.getPropertyName(), jsonObject));
                return objDate >= date1 && objDate <= date2;
            }
        };
        isc.setMainValue(value1);
        isc.setSecondValue(value2);
        this.catFilter.getListCriteria().add(isc);
        return this;
    }

    public FilterDateBuilder betweenDateTime(Date value1, Date value2) {
        DateSearchCriteria isc = new DateSearchCriteria() {
            @Override
            public boolean check(JsonObject jsonObject) {
                Long datetime1 = valueDate_yyyyMMddHHmmss(getMainValue());
                Long datetime2 = valueDate_yyyyMMddHHmmss(getSecondValue());
                Long objDatetime = valueDate_yyyyMMddHHmmss(getDate(catFilter.getPropertyName(), jsonObject));
                return objDatetime >= datetime1 && objDatetime <= datetime2;
            }
        };
        isc.setMainValue(value1);
        isc.setSecondValue(value2);
        this.catFilter.getListCriteria().add(isc);
        return this;
    }

    private Long valueDate_yyyyMMdd(Date date) {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
        return Long.valueOf(sdf.format(date));
    }

    private Long valueDate_yyyyMMddHHmmss(Date date) {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddHHmmss");
        return Long.valueOf(sdf.format(date));
    }

    private boolean equalDate(Date d1, Date d2) {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
        return sdf.format(d1).equals(sdf.format(d2));
    }

    private boolean equalDateTime(Date d1, Date d2) {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddHHmmss");
        return sdf.format(d1).equals(sdf.format(d2));
    }

    private boolean equalDateTimeMillis(Date d1, Date d2) {
        return d1.getTime() == d2.getTime();
    }

    @Override
    public CatFilter build() {
        return catFilter;
    }
}
