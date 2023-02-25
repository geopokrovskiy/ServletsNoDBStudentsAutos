package com.geopokrovskiy.model;

import java.util.Objects;

public class Student {
    private long id;
    private int age;
    private String fio;
    private int num;
    private int salary;

    public Student(){

    }

    public Student(long id, int age, String fio, int num, int salary) {
        this.id = id;
        this.age = age;
        this.fio = fio;
        this.num = num;
        this.salary = salary;
    }

    public Student(int age, String fio, int num, int salary) {
        this.age = age;
        this.fio = fio;
        this.num = num;
        this.salary = salary;
    }


    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public String getFio() {
        return fio;
    }

    public void setFio(String fio) {
        this.fio = fio;
    }

    public int getNum() {
        return num;
    }

    public void setNum(int num) {
        this.num = num;
    }

    public int getSalary() {
        return salary;
    }

    public void setSalary(int salary) {
        this.salary = salary;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Student student = (Student) o;
        return id == student.id;
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }

    @Override
    public String toString() {
        return "Student{" +
                "id=" + id +
                ", age=" + age +
                ", fio='" + fio + '\'' +
                ", num=" + num +
                ", salary=" + salary +
                '}';
    }
}
