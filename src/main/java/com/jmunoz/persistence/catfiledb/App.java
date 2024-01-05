package com.jmunoz.persistence.catfiledb;

import com.google.gson.Gson;
import com.jmunoz.persistence.catfiledb.example.Car;

import java.io.File;
import java.io.IOException;
import java.io.RandomAccessFile;

/**
 * To run tests in development mode
 *
 *
 */
public class App 
{
    public static void main( String[] args )
    {
        String str = "{" + System.currentTimeMillis() + "}";
        byte[] bytesToWrite = str.getBytes();
        String path = "D:" + File.separator + "Jorge" + File.separator + "hola.txt";
        try{
            RandomAccessFile archivo = new RandomAccessFile(path, "rw");
            archivo.seek(12);
            archivo.write(bytesToWrite);
            archivo.close();
        }catch (IOException e){
            e.printStackTrace();
        }

//        String str = "Linea escrita en tiempo : " + System.currentTimeMillis();
//        byte[] bytesToWrite = str.getBytes();
//        int lengthBytes  = bytesToWrite.length;
//        String path = "D:" + File.separator + "Jorge" + File.separator + "hola.txt";
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

    }
}
