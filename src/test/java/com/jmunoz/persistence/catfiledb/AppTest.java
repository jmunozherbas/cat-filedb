package com.jmunoz.persistence.catfiledb;


import com.jmunoz.persistence.catfiledb.example.Car;
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
    private static final String PATH_DB = "D:" + File.separator + "Jorge" + File.separator + "mydb";
    private static final String SERIAL_TEST = "123456";

    private static CatFileDBInstance prepareInstanceDB() {
        CatFileDBInstance db = new CatFileDBInstance() {
            @Override
            public List<CatClass> getListClasses() {
                List<CatClass> list = new ArrayList<>();
                list.add(new CatClass(CLASS_NAME, "serial", new ArrayList<String>(), Car.class));
                return list;
            }

            @Override
            public String getPathFolderDB() {
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
            if (e instanceof CatException && e.getMessage().equals("EXISTS_ID_OBJECT")) {
                result = true;
            }
        }
        assertTrue(result);

        try{
            CatFileDBInstance db = prepareInstanceDB();
            db.initDB();
            System.out.println("Find by id : " + SERIAL_TEST);
            Car car1 = (Car) db.findById(CLASS_NAME, SERIAL_TEST);
            System.out.println(car1);
            assertNotNull(car1);
        } catch (Exception e){}

    }

}
