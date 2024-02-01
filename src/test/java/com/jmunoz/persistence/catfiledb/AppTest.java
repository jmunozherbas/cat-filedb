package com.jmunoz.persistence.catfiledb;


import com.google.gson.JsonObject;
import com.google.gson.reflect.TypeToken;
import com.jmunoz.persistence.catfiledb.example.Car;
import com.jmunoz.persistence.catfiledb.search.CatFilter;
import com.jmunoz.persistence.catfiledb.search.builder.FilterIntegerBuilder;
import org.junit.Test;

import java.io.File;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;


/**
 * Unit test for simple App.
 */
public class AppTest {

    private static final String CLASS_NAME = "car";
    private static final String PATH_DB = "D:" + File.separator + "test" + File.separator + "mydb";
    private static final String SERIAL_TEST = "123456";

    private static CatFileDBInstance prepareInstanceDB() {
        CatFileDBInstance db = new CatFileDBInstance() {
            @Override
            protected List<CatClass> getListClasses() {
                List<CatClass> list = new ArrayList<>();
                list.add(new CatClass(CLASS_NAME, "serial", Car.class, new TypeToken<ArrayList<Car>>() {
                }.getType()));
                return list;
            }

            @Override
            protected String getPathFolderDB() {
                return PATH_DB;
            }
        };
        return db;
    }

    @Test
    public void test01SaveObject() {
        boolean result = false;
        try {
            CatFileDBInstance db = prepareInstanceDB();
            db.initDB();
            Car car = new Car();
            car.setSerial(String.valueOf(new Date().getTime()));
            car.setPlate("ABC-123");
            car.setColor("blue");
            car.setAxis(4);
            car.setMark("TOYOTA");
            car.setModel("corolla");
            car.setYear(1998);
            result = db.save(car, CLASS_NAME);
        } catch (Exception e) {
        }
        assertTrue(result);
    }

    @Test
    public void test02SaveObjectExistent() {
        boolean result = false;
        try {
            CatFileDBInstance db = prepareInstanceDB();
            db.initDB();
            Car car = new Car();
            car.setSerial(SERIAL_TEST);
            car.setPlate("ABC-123");
            car.setColor("blue");
            car.setAxis(4);
            car.setMark("TOYOTA");
            car.setModel("corolla");
            car.setYear(1998);
            result = db.save(car, CLASS_NAME);
        } catch (Exception e) {
            if (e instanceof CatException && ((CatException) e).getCodeException().equals(CatFileDB.Exception.DB_UNIQUE_ID_VIOLATED)) {
                result = true;
            }
        }
        assertTrue(result);

        try {
            CatFileDBInstance db = prepareInstanceDB();
            db.initDB();
            System.out.println("Find by id : " + SERIAL_TEST);
            Car car1 = (Car) db.findById(CLASS_NAME, SERIAL_TEST);
            System.out.println(car1);
            assertNotNull(car1);
        } catch (Exception e) {
        }

    }

    @Test
    public void test03FilterJsonObjects() {
        boolean result = false;
        try {
            CatFileDBInstance db = prepareInstanceDB();
            db.initDB();
            List<CatFilter> filters = new ArrayList<>();
            filters.add(new FilterIntegerBuilder("year").equalTo(1998).build());
            List<JsonObject> list = db.filterJsonObjects("car", filters);
            System.out.println(list);
            result = true;
        } catch (Exception e) {
            if (e instanceof CatException && ((CatException) e).getCodeException().equals(CatFileDB.Exception.DB_UNIQUE_ID_VIOLATED)) {
                result = true;
            }
        }
        assertTrue(result);

    }

}
