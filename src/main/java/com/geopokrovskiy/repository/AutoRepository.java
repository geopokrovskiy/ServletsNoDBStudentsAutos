package com.geopokrovskiy.repository;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.geopokrovskiy.model.Auto;
import com.geopokrovskiy.util.Constants;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class AutoRepository {

    private List<Auto> autoList = new ArrayList<>();

    /**
     * Constructor with no repository arguments produces
     * loading data into a list from a json file (put the file names into the Constants class),
     * if the file does not exist, then leave the list empty.
     */
    public AutoRepository() {
        String fileName = Constants.AUTOS_DB;
        File file = new File(fileName);
        try (BufferedReader bufferedReader = new BufferedReader(new FileReader(file))) {
            ObjectMapper objectMapper = new ObjectMapper();
            this.autoList = objectMapper.readValue(bufferedReader, new TypeReference<>() {
            });
        } catch (IOException ignored) {}
    }

    /**
     * Saves the collection to the file
     */
    public void saveToFile() {
        String fileName = Constants.AUTOS_DB;
        File file = new File(fileName);
        ObjectMapper objectMapper = new ObjectMapper();
        try {
            objectMapper.writeValue(file, this.autoList);
        } catch (IOException ignored) {}
    }

    /**
     * The method of adding an object to the repository adds data to the list,
     * assigns an id to the passed object (takes the maximum id in the collection and adds 1)
     * and unloads the modified collection to a file.
     *
     * @param auto
     */
    public void add(Auto auto) {
        long maxId = autoList.stream().mapToLong(Auto::getId).max().orElse(0);
        auto.setId(++maxId);
        autoList.add(auto);
        this.saveToFile();
    }

    /**
     * The method for getting an object by id retrieves an object from the collection by its id, or null if there is no such object.
     *
     * @param id
     * @return
     */
    public Auto getAuto(long id) {
        return this.autoList.stream().filter(auto1 -> auto1.getId() == id).findFirst().orElse(null);
    }

    /**
     *  The update method takes a new object as input.
     *  Gets the old object from the collection by the id of the new object.
     *  If there is no such object in the collection,
     *  then returns false if the object is found,
     *  then replaces it with a new one in the list.
     *  Dumps the modified collection to a file, returning true
     *
     * @param auto
     * @return
     */
    public boolean update(Auto auto) {
        int idx = this.autoList.indexOf(auto);
        if (idx < 0)
            return false;
        this.autoList.set(idx, auto);
        this.saveToFile();
        return true;
    }

    /**
     * The delete method deletes an object by id and unloads the modified collection to a file
     * @param id
     */
    public Auto delete(long id) {
        Auto deleted = this.getAuto(id);
        if(deleted == null){
            return null;
        }
        this.autoList.remove(deleted);
        this.saveToFile();
        return deleted;
    }

    /**
     * Метод получения всех объектов должен вернуть коллекцию из репозитория
     *
     * @return
     */
    public List<Auto> getAutoList() {
        return this.autoList;
    }
}
