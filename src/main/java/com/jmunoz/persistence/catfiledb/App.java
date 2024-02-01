package com.jmunoz.persistence.catfiledb;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.reflect.TypeToken;
import com.jmunoz.persistence.catfiledb.example.Car;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.zip.Deflater;
import java.util.zip.GZIPOutputStream;

/**
 * To run tests in development mode
 *
 *
 */
public class App 
{
    public static void main( String[] args )
    {
//        String str = "{" + System.currentTimeMillis() + "}";
//        byte[] bytesToWrite = str.getBytes();
//        String path = "D:" + File.separator + "test" + File.separator + "hola.txt";
//        try{
//            RandomAccessFile archivo = new RandomAccessFile(path, "rw");
//            archivo.seek(12);
//            archivo.write(bytesToWrite);
//            archivo.close();
//        }catch (IOException e){
//            e.printStackTrace();
//        }

//        String str = "Linea escrita en tiempo : " + System.currentTimeMillis();
//        byte[] bytesToWrite = str.getBytes();
//        int lengthBytes  = bytesToWrite.length;
//        String path = "D:" + File.separator + "test" + File.separator + "hola.txt";
//        try{
//            File f = new File(path);
//            RandomAccessFile archivo = new RandomAccessFile(path, "rw");
//            archivo.seek(f.length());
//            archivo.write(bytesToWrite);
//            archivo.close();
//        }catch (IOException e){
//            e.printStackTrace();
//        }
//
//        try{
//            File f1 = new File(path);
//            RandomAccessFile archivo2 = new RandomAccessFile(path, "r");
//            archivo2.seek(f1.length()-lengthBytes);
//            byte[] readBytes = new byte[lengthBytes];
//            archivo2.readFully(readBytes);
//            archivo2.close();
//            String result = new String(readBytes);
//            System.out.println("---> " + result);
//        }catch (IOException e){
//            e.printStackTrace();
//        }
        JsonArray array = new JsonArray();

        String jsonArray = "{\"fi\":1,\"ib\":0,\"le\":114,\"id\":\"1704512006399\",\"sl\":[]},{\"fi\":1,\"ib\":115,\"le\":107,\"id\":\"123456\",\"sl\":[]},{\"fi\":1,\"ib\":223,\"le\":114,\"id\":\"1704512011179\",\"sl\":[]},{\"fi\":1,\"ib\":338,\"le\":114,\"id\":\"1704559893354\",\"sl\":[]},{\"fi\":1,\"ib\":453,\"le\":114,\"id\":\"1704559925143\",\"sl\":[]}";

        System.out.println(new Gson().toJson(new Date()));
    }
}
