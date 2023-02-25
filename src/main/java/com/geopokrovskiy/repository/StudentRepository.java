package com.geopokrovskiy.repository;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.geopokrovskiy.model.Student;
import com.geopokrovskiy.util.Constants;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class StudentRepository {
    private List<Student> studentList = new ArrayList<>();

    /**
     * Constructor with no repository arguments produces
     * loading data into a list from a json file (put the file names into the Constants class),
     * if the file does not exist, then leave the list empty.
     */
    public StudentRepository() {
        String fileName = Constants.STUDENTS_DB;
        File file = new File(fileName);
        try (BufferedReader bufferedReader = new BufferedReader(new FileReader(file))) {
            ObjectMapper objectMapper = new ObjectMapper();
            this.studentList = objectMapper.readValue(bufferedReader, new TypeReference<List<Student>>() {
            });
        } catch (IOException ignored) {}
    }

    /**
     * Saves the collection to the file
     */
    public void saveToFile() {
        String fileName = Constants.STUDENTS_DB;
        File file = new File(fileName);
        ObjectMapper objectMapper = new ObjectMapper();
        try {
            objectMapper.writeValue(file, this.studentList);
        } catch (IOException ignored) {}
    }

    /**
     * The method of adding an object to the repository adds data to the list,
     * assigns an id to the passed object (takes the maximum id in the collection and adds 1)
     * and unloads the modified collection to a file.
     * @param student
     */
    public void add(Student student) {
        long maxId = studentList.stream().mapToLong(Student::getId).max().orElse(0);
        student.setId(++maxId);
        this.studentList.add(student);
        this.saveToFile();
    }

    /**
     * The method for getting an object by id retrieves an object from the collection by its id, or null if there is no such object.
     * @param id
     * @return
     */
    public Student getStudent(long id) {
        return this.studentList.stream().filter(student -> student.getId() == id)
                .findFirst().
                orElse(null);
    }

    /**
     *  The update method takes a new object as input.
     *  Gets the old object from the collection by the id of the new object.
     *  If there is no such object in the collection,
     *  then returns false if the object is found,
     *  then replaces it with a new one in the list.
     *  Dumps the modified collection to a file, returning true
     *
     * @param student
     * @return
     */
    public boolean update(Student student) {
        int idx = this.studentList.indexOf(student);
        if(idx < 0)
            return false;
        this.studentList.set(idx, student);
        this.saveToFile();
        return true;
    }

    /**
     * The delete method deletes an object by id and unloads the modified collection to a file
     *
     * @param id
     */
    public Student delete(long id) {
        Student deleted = this.getStudent(id);
        if(deleted == null)
            return null;
        this.studentList.remove(deleted);
        this.saveToFile();
        return deleted;
    }

    /**
     * The getter of all objects must return a collection from the repository
     * @return
     */
    public List<Student> getStudentList() {
        return this.studentList;
    }
}
