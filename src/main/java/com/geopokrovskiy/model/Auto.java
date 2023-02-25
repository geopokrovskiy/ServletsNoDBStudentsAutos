package com.geopokrovskiy.model;

import java.util.Objects;

public class Auto {
    private long id;
    private String brand;
    private int power;
    private int year;
    private long idStudent;

    public Auto() {
    }

    public Auto(long id, String brand, int power, int year, long idStudent) {
        this.id = id;
        this.brand = brand;
        this.power = power;
        this.year = year;
        this.idStudent = idStudent;
    }

    public Auto(String brand, int power, int year, long idStudent) {
        this.brand = brand;
        this.power = power;
        this.year = year;
        this.idStudent = idStudent;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getBrand() {
        return brand;
    }

    public void setBrand(String brand) {
        this.brand = brand;
    }

    public int getPower() {
        return power;
    }

    public void setPower(int power) {
        this.power = power;
    }

    public int getYear() {
        return year;
    }

    public void setYear(int year) {
        this.year = year;
    }

    public long getIdStudent() {
        return idStudent;
    }

    public void setIdStudent(long idStudent) {
        this.idStudent = idStudent;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Auto auto = (Auto) o;
        return id == auto.id;
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}
