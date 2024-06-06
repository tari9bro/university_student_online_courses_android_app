package com.university.hafsa.model.entity;

public class Student {
    private int id; // to store the Firebase generated key
    private String name;
    private String password;
    private String level;
    private String specialty;

    // Default constructor required for calls to DataSnapshot.getValue(Student.class)
    public Student() {
    }

    public Student(int id, String name, String password, String level, String specialty) {
        this.id = id;
        this.name = name;
        this.password = password;
        this.level = level;
        this.specialty = specialty;
    }

    // Getters and Setters
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getLevel() {
        return level;
    }

    public void setLevel(String level) {
        this.level = level;
    }

    public String getSpecialty() {
        return specialty;
    }

    public void setSpecialty(String specialty) {
        this.specialty = specialty;
    }
}
