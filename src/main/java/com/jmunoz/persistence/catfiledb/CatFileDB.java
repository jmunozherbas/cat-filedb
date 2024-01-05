package com.jmunoz.persistence.catfiledb;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;

public class CatFileDB {


    public static final long MAX_LENGTH_FILE_CLASS = 5242880L;
    public static final int MAX_QUANTITY_FILES_CLASS = 100;

    public static final byte TYPE_VALUE_INT = 1;
    public static final byte TYPE_VALUE_STRING = 2;
    public static final byte TYPE_VALUE_LONG = 3;
    public static final byte TYPE_VALUE_DOUBLE = 4;
    public static final byte TYPE_VALUE_BOOLEAN = 5;

    public static final CatFileDBInstance findOrCreateInstance(String pathFolderDB, List<CatClass> includedObjects){

        return null;
    }

//
//    private static CatObject readObject(CatRegister register) throws  IOException{
//        File f1 = new File(register.getFileName());
//        RandomAccessFile archivo2 = new RandomAccessFile(path, "r");
//        archivo2.seek(f1.length()-lengthBytes);
//        byte[] readBytes = new byte[lengthBytes];
//        archivo2.readFully(readBytes);
//        archivo2.close();
//        String result = new String(readBytes);
//    }

    public static synchronized String readFileString(String filePath) throws IOException {
        return new String(Files.readAllBytes(Paths.get(filePath)));
    }

    public static synchronized boolean writeFileString(String filePath, String content) throws IOException {
        Path pathResult = Files.write(Paths.get(filePath), content.getBytes());
        return pathResult!=null;
    }

}
