package com.jmunoz.persistence.catfiledb;

import com.google.gson.Gson;

import java.io.File;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Random;

public class CatFileManager {


    public static synchronized boolean initDataBaseFiles(String pathFolderDB, List<CatClass> listClasses) throws CatException {
        /** Find or create folder for catDB files **/
        findOrCreateFolder(pathFolderDB);
        /** Find or create file to register and control the sequence of transactions **/
        findOrCreateFile(pathFolderDB, "queue.cat");
        /** Find or create file to register classes and file paging data **/
        findOrCreateFile(pathFolderDB, "classes.cat");

        /** Find or create each file, data and deleted for cat classes **/
        for (CatClass catClass : listClasses) {
            String dataFilename = "data_" + catClass.getName() + ".cat";
            findOrCreateFile(pathFolderDB, dataFilename);

            String deletedFilename = "del_" + catClass.getName() + ".cat";
            findOrCreateFile(pathFolderDB, deletedFilename);
        }

        /** Find or create file register and folder for file storing **/
        findOrCreateFolder(pathFolderDB + File.separator + "filestoring");
        findOrCreateFile(pathFolderDB, "data_filestoringcatdb.cat");
        findOrCreateFile(pathFolderDB, "del_filestoringcatdb.cat");
        return true;
    }

    /**
     * Register the session for the DB use.
     *
     * @param folderPathDB
     * @param description  specify the purpose of the session
     * @return
     */
    public static synchronized CatSession createSession(String folderPathDB, String description) throws CatException {
        CatSession catSession = new CatSession();
        catSession.setHashSession(generateRandomString(7));
        catSession.setDescription(description);
        catSession.setDateCreation(new Date().getTime());
        File _fileSession = findOrCreateFile(folderPathDB, "cat_session.cat");
        if (_fileSession != null) {
            String strContent = new Gson().toJson(catSession);
            if (writeAppendContent(strContent, _fileSession.getPath())) {
                return catSession;
            }
        }
        return null;
    }

    /**
     * Execute the transaction of insert, update or delete
     *
     * @return if execution was correct, return the new size of the file data class, else return -1
     * @throws CatException
     */
    public static synchronized int executeTransaction(CatTransaction transaction) throws CatException {
        switch (transaction.getTypeTransaction()) {
            case CatFileDB.TypeTransaction.INSERT:

                return 0;
            case CatFileDB.TypeTransaction.UPDATE:

                return 1;
            case CatFileDB.TypeTransaction.DELETE:

                return 2;
        }
        return -1;
    }


    /**
     * Find or create the File in the specific folder with the specific filename
     *
     * @param pathFolder
     * @param fileName
     * @return the file, or null
     */
    private static File findOrCreateFile(String pathFolder, String fileName) throws CatException {
        File _file = new File(pathFolder + File.separator + fileName);
        /** If file doesn't exists, then create **/
        if (!_file.exists()) {
            try {
                if (!_file.createNewFile()) {
                    /** If file can't be created**/
                    throw new CatException(CatFileDB.Exception.DB_FILE_MANAGING, "Error creating File : " + _file.getPath());
                } else {
                    System.out.println("File was created successfully: " + _file.getPath());
                }
            } catch (IOException e) {
                System.out.println("Error creating file: " + fileName + " " + e.getMessage());
                return null;
            }
        } else {
            System.out.println("File was found: " + fileName);
        }
        return _file;
    }

    /**
     * Find or create the folder specified in the pathFolder parameter
     *
     * @param pathFolder
     * @return the file folder, or null
     */
    private static File findOrCreateFolder(String pathFolder) throws CatException {
        File baseFolder = new File(pathFolder);

        /** If folder path doesn't exists, then create the folder **/
        if (!baseFolder.exists()) {
            if (!baseFolder.mkdir()) {
                /** If folder can't be created **/
                System.out.println("Error creating Folder : " + pathFolder);
                throw new CatException(CatFileDB.Exception.DB_FILE_MANAGING, "Error creating Folder : " + pathFolder);
            } else {
                System.out.println("Folder was created: " + pathFolder);
            }
        } else {
            System.out.println("Folder was found: " + pathFolder);
        }
        return baseFolder;
    }

    public static boolean writeAppendContent(String strContent, String filePath) {
        /** Write the object to the end of the file **/
        File _file = new File(filePath);
        long lengthFile = _file.length();
        String separator = "";
        if (lengthFile > 0) {
            separator = ",";
        }
        try {
            RandomAccessFile fileAccess = new RandomAccessFile(filePath, "rw");
            fileAccess.seek(lengthFile);
            fileAccess.write((separator + strContent).getBytes());
            fileAccess.close();
        } catch (IOException e) {
            System.out.println("Error writing file, the file : " + filePath + " " + e.getMessage());
            return false;
        }
        return true;
    }

    public static byte[] readFileSection(String filePath, long indexBegin, long indexEnd) throws IOException {
        RandomAccessFile fileAccess = new RandomAccessFile(filePath, "r");
        fileAccess.seek(indexBegin);
        Long length = indexEnd - indexBegin;
        byte[] readBytes = new byte[length.intValue()];
        fileAccess.readFully(readBytes);
        fileAccess.close();
        return readBytes;
    }

    public static byte[] readFileFull(String filePath) throws IOException {
        return Files.readAllBytes(Paths.get(filePath));
    }

    private static final char[] ALPHANUMERIC = {'0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'a', 'b', 'c', 'd', 'e', 'f', 'g', 'h', 'i', 'j', 'k', 'l', 'm', 'n', 'o', 'p', 'q', 'r', 's', 't', 'u', 'v', 'w', 'x', 'y', 'z'};

    public static String generateRandomString(int length) {
        Random random = new Random(new Date().getTime());
        StringBuilder buffer = new StringBuilder(length);
        for (int i = 0; i < length; i++) {
            buffer.append(ALPHANUMERIC[random.nextInt(ALPHANUMERIC.length)]);
        }
        return buffer.toString();
    }

}
