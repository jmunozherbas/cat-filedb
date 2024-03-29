package com.jmunoz.persistence.catfiledb;

import com.google.gson.Gson;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.reflect.TypeToken;
import com.jmunoz.persistence.catfiledb.search.CatDeletedObject;
import com.jmunoz.persistence.catfiledb.search.CatFilter;
import com.jmunoz.persistence.catfiledb.search.criteria.CatSearchCriteria;

import java.io.File;
import java.io.IOException;
import java.lang.reflect.Type;
import java.util.*;
import java.util.function.Predicate;
import java.util.stream.Collectors;

public abstract class CatFileDBInstance {

    private static HashMap<String, CatClass> HM_CLASSES = null;
    private static HashMap<String, HashMap<String, JsonObject>> HM_DATA_CLASS = null;
    //    private static HashMap<String, List<String>> HM_KEYS_DATA_CLASS = null;
    private static HashMap<String, Integer> HM_LAST_INDEX_CLASS = null;
    private static HashMap<String, Long> HM_LENGTH_BYTES_DATA_CLASS = null;
    private static HashMap<String, Long> HM_LENGTH_BYTES_DATA_DELETED_CLASS = null;
    private CatSession CAT_SESSION;

    protected abstract List<CatClass> getListClasses();

    protected abstract String getPathFolderDB();

    public void initDB() throws CatException {
        if (!CatFileManager.initDataBaseFiles(getPathFolderDB(), getListClasses())) {
            System.out.println("Database Files can't be created.");
            return;
        }
        initClasses();
    }

    private void initClasses() throws CatException {
        HM_CLASSES = new HashMap<>();
        HM_DATA_CLASS = new HashMap<>();
//        HM_KEYS_DATA_CLASS = new HashMap<>();
        HM_LAST_INDEX_CLASS = new HashMap<>();
        HM_LENGTH_BYTES_DATA_CLASS = new HashMap<>();
        HM_LENGTH_BYTES_DATA_DELETED_CLASS = new HashMap<>();

        for (CatClass catClass : getListClasses()) {
            HM_CLASSES.put(catClass.getName(), catClass);
            updateClassDataFromFile(catClass.getName());
        }
    }

    private String getPathFileDataClass(String className) {
        return getPathFolderDB() + File.separator + "data_" + className + ".cat";
    }

    private String getPathFileDeleteDataClass(String className) {
        return getPathFolderDB() + File.separator + "del_" + className + ".cat";
    }

    private List<JsonObject> stringContentAsListObjects(String strContent, String className) {
        return new Gson().fromJson(strContent, new TypeToken<ArrayList<JsonObject>>() {
        }.getType());
    }

    private void updateClassDataFromFile(String className) throws CatException {
        try {
            if (!HM_DATA_CLASS.containsKey(className)) {
                loadFullDataFromFile(className);
            } else {
                loadNewDataFromFile(className);
            }
        } catch (IOException e) {
            throw new CatException(CatFileDB.Exception.DB_FILE_MANAGING, "Error reading class data from file : " + className);
        }
    }

    private void loadFullDataFromFile(String className) throws IOException {
        byte[] byteDataClass = CatFileManager.readFileFull(getPathFileDataClass(className));
        String content = "[" + new String(byteDataClass) + "]";
        HM_LENGTH_BYTES_DATA_CLASS.put(className, (long) byteDataClass.length);

        byte[] byteDataDeletedClass = CatFileManager.readFileFull(getPathFileDeleteDataClass(className));
        String contentDel = "[" + new String(byteDataDeletedClass) + "]";
        HM_LENGTH_BYTES_DATA_DELETED_CLASS.put(className, (long) byteDataDeletedClass.length);
        Type type = new TypeToken<ArrayList<Integer>>() {
        }.getType();

        List<CatDeletedObject> listDeletedObjects = (List<CatDeletedObject>) new Gson().fromJson(contentDel, type);
        HashSet<Integer> setDeleted = new HashSet<>();
        for (CatDeletedObject cdo : listDeletedObjects) {
            setDeleted.add(cdo.getIndexFile());
        }
        List<JsonObject> listObjects = stringContentAsListObjects(content, className);
        HM_LAST_INDEX_CLASS.put(className, listObjects.size());

        HashMap<String, JsonObject> resultData = new HashMap<>();
        for (int i = 0; i < listObjects.size(); i++) {
            if (!setDeleted.contains(i)) {
                listObjects.get(i).addProperty("idxCat", i);
                resultData.put(listObjects.get(i).get(HM_CLASSES.get(className).getFieldId()).getAsString(), listObjects.get(i));
            }
        }

        listObjects.clear();
        setDeleted.clear();
        listDeletedObjects.clear();

        HM_DATA_CLASS.put(className, resultData);
    }

    private void loadNewDataFromFile(String className) throws IOException {
        File _file = new File(getPathFileDataClass(className));
        if (_file.length() > HM_LENGTH_BYTES_DATA_CLASS.get(className)) {
            byte[] readBytes = CatFileManager.readFileSection(_file.getPath(), HM_LENGTH_BYTES_DATA_CLASS.get(className), _file.length());
            String strNewContentData = "[" + new String(readBytes) + "]";
            HM_LENGTH_BYTES_DATA_CLASS.put(className, _file.length());

            List<JsonObject> listObjects = stringContentAsListObjects(strNewContentData, className);
            for (int i = 0; i < listObjects.size(); i++) {
                listObjects.get(i).addProperty("idxCat", HM_LAST_INDEX_CLASS.get(className) + i);
                HM_DATA_CLASS.get(className).put(listObjects.get(i).get(HM_CLASSES.get(className).getFieldId()).getAsString(), listObjects.get(i));
            }

            HM_LAST_INDEX_CLASS.put(className, HM_LAST_INDEX_CLASS.get(className) + listObjects.size());
        }

        File _fileDel = new File(getPathFileDeleteDataClass(className));
        if (_fileDel.length() > HM_LENGTH_BYTES_DATA_DELETED_CLASS.get(className)) {
            byte[] readBytes = CatFileManager.readFileSection(_fileDel.getPath(), HM_LENGTH_BYTES_DATA_DELETED_CLASS.get(className), _fileDel.length());
            String strNewContentDel = "[" + new String(readBytes) + "]";
            HM_LENGTH_BYTES_DATA_DELETED_CLASS.put(className, _fileDel.length());
            Type type = new TypeToken<ArrayList<CatDeletedObject>>() {
            }.getType();

            List<CatDeletedObject> listDeletedObjects = new Gson().fromJson(strNewContentDel, type);

            for (CatDeletedObject cdo : listDeletedObjects) {
                JsonObject jsonObject = HM_DATA_CLASS.get(className).get(cdo.getKeyObject());
                if (jsonObject != null && jsonObject.get("idxCat").getAsInt() == cdo.getIndexFile()) {
                    HM_DATA_CLASS.get(className).remove(cdo.getKeyObject());
                }
            }
        }
    }

    private String validateToSave(JsonObject jsonObject, String className) throws CatException {
        CatClass catClass = HM_CLASSES.get(className);
        if (catClass == null) {
            throw new CatException(CatFileDB.Exception.DB_PROCESSING, "class not found or null " + className);
        }

        JsonElement elementIdObject = jsonObject.get(catClass.getFieldId());
        if (elementIdObject == null) {
            throw new CatException(CatFileDB.Exception.DB_TRANSACTION, "Id object not found or null " + className + " " + catClass.getFieldId());
        }

        String strIdObject = elementIdObject.getAsString();
        if (strIdObject.isEmpty()) {
            throw new CatException(CatFileDB.Exception.DB_TRANSACTION, "Id object empty " + className + " " + catClass.getFieldId());
        }
        if (!elementIdObject.isJsonPrimitive()) {
            throw new CatException(CatFileDB.Exception.DB_TRANSACTION, "Id object needs to be a primitive value (integer,string,long)  in class:" + className + " " + catClass.getFieldId());
        }

        if (HM_DATA_CLASS.get(className).containsKey(strIdObject)) {
            throw new CatException(CatFileDB.Exception.DB_UNIQUE_ID_VIOLATED, "Id object already exists, unique id violated " + className + " " + catClass.getFieldId() + " --> " + strIdObject);
        }

        return strIdObject;
    }

    private int validateToUpdate(JsonObject jsonObject, String className) throws CatException {
        CatClass catClass = HM_CLASSES.get(className);
        if (catClass == null) {
            throw new CatException(CatFileDB.Exception.DB_PROCESSING, "class not found or null " + className);
        }

        JsonElement elementIdObject = jsonObject.get(catClass.getFieldId());
        if (elementIdObject == null) {
            throw new CatException(CatFileDB.Exception.DB_TRANSACTION, "Id object not found or null " + className + " " + catClass.getFieldId());
        }

        String strIdObject = elementIdObject.getAsString();
        if (strIdObject.isEmpty()) {
            throw new CatException(CatFileDB.Exception.DB_TRANSACTION, "Id object empty " + className + " " + catClass.getFieldId());
        }
        if (!elementIdObject.isJsonPrimitive()) {
            throw new CatException(CatFileDB.Exception.DB_TRANSACTION, "Id object needs to be a primitive value (integer,string,long)  in class:" + className + " " + catClass.getFieldId());
        }

        JsonObject jo = HM_DATA_CLASS.get(className).get(strIdObject);
        if (jo == null) {
            throw new CatException(CatFileDB.Exception.DB_TRANSACTION, "IdObject not found class: " + className + " id : " + strIdObject);
        }

        return jo.get("idxCat").getAsInt();
    }

    private int validateToDelete(String strIdObject, String className) throws CatException {
        CatClass catClass = HM_CLASSES.get(className);
        if (catClass == null) {
            throw new CatException(CatFileDB.Exception.DB_PROCESSING, "class not found or null " + className);
        }

        if (strIdObject.isEmpty()) {
            throw new CatException(CatFileDB.Exception.DB_TRANSACTION, "Id object empty " + className + " " + catClass.getFieldId());
        }
        JsonObject jo = HM_DATA_CLASS.get(className).get(strIdObject);
        if (jo == null) {
            throw new CatException(CatFileDB.Exception.DB_TRANSACTION, "IdObject not found class: " + className + " id : " + strIdObject);
        }

        return jo.get("idxCat").getAsInt();
    }

    private void removeNoPrimitiveValues(JsonObject jsonObject, String className) {
        if (HM_CLASSES.get(className).isSaveNoPrimitiveData()) {
            return;
        }
        Iterator var2 = jsonObject.entrySet().iterator();
        List<String> toRemove = new ArrayList<>();
        while (var2.hasNext()) {
            Map.Entry<String, JsonElement> entry = (Map.Entry) var2.next();
            if (!(entry.getValue()).isJsonPrimitive()) {
                toRemove.add(entry.getKey());
            }
        }

        Iterator var3 = toRemove.iterator();
        while (var3.hasNext()) {
            jsonObject.remove((String) var3.next());
        }
    }

    private List<Object> convertListJson_ToObject(String className, List<JsonObject> inputListJsonObject) {
        List<Object> result = new ArrayList<>();
        Iterator it = inputListJsonObject.iterator();
        Gson gson = new Gson();
        CatClass catClass = HM_CLASSES.get(className);
        while (it.hasNext()) {
            result.add(gson.fromJson((JsonObject) it.next(), catClass.getTypeClass()));
        }
        return result;
    }

    public boolean save(Object object, String className) throws CatException {
        String strJsonObject = new Gson().toJson(object);
        return saveJsonString(strJsonObject, className);
    }

    public boolean saveJsonString(String strJsonObject, String className) throws CatException {
        try {
            JsonObject jsonObject = new Gson().fromJson(strJsonObject, JsonObject.class);
            String strIdObject = validateToSave(jsonObject, className);
            removeNoPrimitiveValues(jsonObject, className);
            CatFileManager.writeAppendContent(jsonObject.getAsString(), getPathFileDataClass(className));
            return true;
        } catch (CatException e) {
            System.out.println("Object can't be inserted,  class : " + className + " strObj : " + strJsonObject + " " + e.getMessage());
            throw e;
        }
    }

    public Object findById(String className, String idObject) throws CatException {
        return new Gson().fromJson(findJsonById(className, idObject), HM_CLASSES.get(className).getTypeClass());
    }

    public JsonObject findJsonById(String className, String idObject) throws CatException {
        updateClassDataFromFile(className);
        JsonObject jo = HM_DATA_CLASS.get(className).get(idObject);
        if (jo == null) {
            System.out.println("Object not found class: " + className + " id : " + idObject);
        }
        return jo;
    }

    public List<Object> filterObjects(String className, List<CatFilter> catFilters) throws CatException {
        List<JsonObject> filteredList = filterJsonObjects(className, catFilters);
        return convertListJson_ToObject(className, filteredList);
    }

    public List<JsonObject> filterJsonObjects(String className, List<CatFilter> catFilters) throws CatException {
        updateClassDataFromFile(className);
        Predicate<JsonObject> predicate = new Predicate<JsonObject>() {
            @Override
            public boolean test(JsonObject jsonObject) {
                Iterator it1 = catFilters.iterator();
                while (it1.hasNext()) {
                    CatFilter cf = (CatFilter) it1.next();
                    Iterator it2 = cf.getListCriteria().iterator();
                    while (it2.hasNext()) {
                        CatSearchCriteria csc = (CatSearchCriteria) it2.next();
                        if (!csc.checkCriteria(jsonObject))
                            return false;
                    }
                }
                return true;
            }
        };

        List<JsonObject> filteredList = HM_DATA_CLASS.get(className).values()
                .stream()
                .filter(predicate)
                .collect(Collectors.toList());

        return filteredList;
    }

    public List<JsonObject> listAllObjects(String className) {
        return (List<JsonObject>) HM_DATA_CLASS.get(className).values();
    }

    public boolean deleteById(String strIdObject, String className) throws CatException {
        updateClassDataFromFile(className);
        int indexFileObject = validateToDelete(strIdObject, className);
        boolean result = CatFileManager.writeAppendContent(String.valueOf(indexFileObject), getPathFileDeleteDataClass(className));
        return result;
    }

    public boolean updateObject(Object obj, String className) throws CatException {
        JsonObject jsonObject = new Gson().fromJson(new Gson().toJson(obj), JsonObject.class);
        return updateJsonObject(jsonObject, className);
    }

    public boolean updateJsonObject(JsonObject jsonObject, String className) throws CatException {
        updateClassDataFromFile(className);
        int indexFileObject = validateToUpdate(jsonObject, className);
        removeNoPrimitiveValues(jsonObject, className);
        boolean resultDelete = CatFileManager.writeAppendContent(String.valueOf(indexFileObject), getPathFileDeleteDataClass(className));
        if (resultDelete) {
            return CatFileManager.writeAppendContent(jsonObject.getAsString(), getPathFileDataClass(className));
        } else {
            throw new CatException(CatFileDB.Exception.DB_PROCESSING, "Failed to Delete in Update process - class: " + className + " jsonObj : " + jsonObject);
        }
    }

}
