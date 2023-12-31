package com.jmunoz.persistence.catfiledb;

import com.google.gson.Gson;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.reflect.TypeToken;

import java.io.File;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public abstract class CatFileDBInstance {

    private static HashMap<String, CatClass> HM_CLASSES = null;

    public abstract List<CatClass> getListClasses();

    public abstract String getPathFolderDB();

    public void initDB() throws IOException {
        initClasses();
        initFiles();
    }

    private void initClasses() {
        HM_CLASSES = new HashMap<>();
        for (CatClass catClass : getListClasses()) {
            HM_CLASSES.put(catClass.getName(), catClass);
        }
    }

    private void initFiles() throws IOException {
        /** Find or create folder for catDB files **/
        findOrCreateFolder(getPathFolderDB());

        /** Find or create each file, register and data for cat classes **/
        for (CatClass catClass : HM_CLASSES.values()) {
            String registerFilename = "reg_" + catClass.getName() + ".cat";
            File fileRegClass = findOrCreateFile(getPathFolderDB(), registerFilename);

            String data1Filename = "data_" + catClass.getName() + "1.cat";
            findOrCreateFile(getPathFolderDB(), data1Filename);
        }

        /** Find or create file register and folder for file storing **/
        findOrCreateFolder(getPathFolderDB() + File.separator + "filestoring");
        findOrCreateFile(getPathFolderDB(), "reg_filestoringcatdb.cat");
        findOrCreateFile(getPathFolderDB(), "data_filestoringcatdb1.cat");
    }

    private void findOrCreateFolder(String pathFolder) throws IOException {
        File baseFolder = new File(pathFolder);

        /** If folder path doesn't exists, then create the folder **/
        if (!baseFolder.exists()) {
            if (!baseFolder.mkdir()) {
                /** If folder can't be created, TODO **/
            } else {
                System.out.println("Folder was created: " + pathFolder);
            }
        } else {
            System.out.println("Folder was found: " + pathFolder);
        }
    }

    private File findOrCreateFile(String pathFolder, String fileName) throws IOException {
        File _file = new File(pathFolder + File.separator + fileName);
        /** If file doesn't exists, then create **/
        if (!_file.exists()) {
            if (!_file.createNewFile()) {
                /** If file can't be created, TODO **/
            } else {
                System.out.println("File was created: " + fileName);
            }
        } else {
            System.out.println("File was found: " + fileName);
        }
        return _file;
    }

    private int indexNextClassFile(String className) throws IOException {
        int index = 1;
        while (index < CatFileDB.MAX_QUANTITY_FILES_CLASS) {
            File _file = findOrCreateFile(getPathFolderDB(), "data_" + className + index + ".cat");
            if (_file.length() < CatFileDB.MAX_LENGTH_FILE_CLASS) {
                break;
            } else {
                index++;
            }
        }
        return index;
    }

    private CatRegister writeObjectData(String className, String strJsonObject, String idValue) throws IOException {

        /** Write the object to the end of the file **/
        int indexFile = indexNextClassFile(className);
        File _file = new File(getPathFolderDB() + File.separator + "data_" + className + indexFile + ".cat");
        RandomAccessFile fileAccess = new RandomAccessFile(_file.getPath(), "rw");
        long lenghtFile = _file.length();
        String separator = "";
        if (lenghtFile > 0) {
            separator = ",";
        }
        fileAccess.seek(lenghtFile);
        long indexRegister = lenghtFile + separator.getBytes().length;

        byte[] bytesToWrite = (separator + strJsonObject).getBytes();
        fileAccess.write(bytesToWrite);
        fileAccess.close();

        /** Create Register of the Object **/
        CatRegister catRegister = new CatRegister();
        catRegister.setIndexFirstByte(indexRegister);
        catRegister.setLength(bytesToWrite.length - separator.getBytes().length);
        catRegister.setRegisterFieldId(idValue);
        catRegister.setSearchableFieldList(new ArrayList<CatSearchableField>());//TODO load searchable fields
        catRegister.setFileIndex(indexFile);

        File _fileReg = new File(getPathFolderDB() + File.separator + "reg_" + className + ".cat");
        RandomAccessFile fileAccessReg = new RandomAccessFile(_fileReg.getPath(), "rw");
        String separator2 = "";
        if (_fileReg.length() > 0) {
            separator2 = ",";
        }
        fileAccessReg.seek(_fileReg.length());
        fileAccessReg.write((separator2 + new Gson().toJson(catRegister)).getBytes());
        fileAccessReg.close();

        return catRegister;
    }

    private String readObjectData(CatRegister register, String className) throws IOException {
        File _file = new File(getPathFolderDB() + File.separator + "data_" + className + register.getFileIndex() + ".cat");
        RandomAccessFile fileAccess = new RandomAccessFile(_file.getPath(), "r");
        fileAccess.seek(register.getIndexFirstByte());
        Long lengthRegister = register.getLength();
        byte[] readBytes = new byte[lengthRegister.intValue()];
        fileAccess.readFully(readBytes);
        fileAccess.close();
        return new String(readBytes);
    }

    private CatRegister findCatRegister(String className, String idObject) throws IOException {
        List<CatRegister> list = getListCatRegister(className);
        int pos = list.indexOf(new CatRegister(idObject));
        return list.get(pos);
    }

    private List<CatRegister> getListCatRegister(String className) throws IOException {
        String contentReg = CatFileDB.readFileString(getPathFolderDB() + File.separator + "reg_" + className + ".cat");
        Type listType = new TypeToken<ArrayList<CatRegister>>() {
        }.getType();
        return new Gson().fromJson("[" + contentReg + "]", listType);
    }

    private String validateToSave(JsonObject jsonObject, String className) throws IOException, CatException {
        CatClass catClass = HM_CLASSES.get(className);
        if (catClass == null) {
            //TODO: manage if catClass is null
            throw new CatException("NULL_CAT_CLASS");
        }

        JsonElement elementIdObject = jsonObject.get(catClass.getFieldId());
        if (elementIdObject == null) {
            //TODO: manage if id is null
            throw new CatException("NULL_ID_OBJECT");
        }

        String strIdObject = elementIdObject.getAsString();
        if (strIdObject.isEmpty()) {
            //TODO: manage if id is empty
            throw new CatException("EMPTY_ID_OBJECT");
        }

        List<CatRegister> list = getListCatRegister(catClass.getName());
        if (list.contains(new CatRegister(strIdObject))) {
            //TODO: manage if id is repeated
            throw new CatException("EXISTS_ID_OBJECT");
        }

        return strIdObject;
    }

    public boolean save(Object object, String className) throws IOException, CatException {
        String strJsonObject = new Gson().toJson(object);
        JsonObject jsonObject = new Gson().fromJson(strJsonObject, JsonObject.class);
        String strIdObject = validateToSave(jsonObject, className);
        CatRegister catRegister = writeObjectData(className, strJsonObject, strIdObject);

        return catRegister != null;
    }

    public boolean saveJson(String strJson, String className) throws IOException, CatException {
        JsonObject jsonObject = new Gson().fromJson(strJson, JsonObject.class);
        String strIdObject = validateToSave(jsonObject, className);
        CatRegister catRegister = writeObjectData(className, strJson, strIdObject);

        return catRegister != null;
    }

    public Object findById(String className, String idObject) throws IOException {
        CatRegister catRegister = findCatRegister(className, idObject);
        CatClass catClass = HM_CLASSES.get(className);
        String content = readObjectData(catRegister, className);
        return new Gson().fromJson(content, catClass.getTypeClass());
    }

    public String findJsonById(String className, String idObject) throws IOException {
        CatRegister catRegister = findCatRegister(className, idObject);
        String content = readObjectData(catRegister, className);
        return content;
    }

    public List<Object> findBySearchableFields(String className, List<CatSearchableField> listSearchFields) throws IOException {
        List<CatRegister> list = getListCatRegister(className);
        List<Object> listResult = new ArrayList<>();
        for (CatRegister catRegister : list) {

        }
        return null;
    }

}
