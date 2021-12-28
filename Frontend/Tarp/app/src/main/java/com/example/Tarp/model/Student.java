package com.example.Tarp.model;

import java.util.ArrayList;
import java.util.List;

public class Student {

    private int id;
    private String firstName;
    private String lastName;
    private String email;
    private String username;
    private String password;
    private List<Classes> courses;

    public Student() {
        courses = new ArrayList<>();
    }

    public Student(String username) {
        this.username = username;
    }

    public Student(String username, String password){
        this.username = username;
        this.password = password;
        courses = new ArrayList<>();
    }

    public Student(String firstName, String lastName, String email, String username, String password) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.username = username;
        this.email = email;
        this.password = password;
        courses = new ArrayList<>();
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getFirstName() { return firstName; }

    public void setFirstName(String firstName) { this.firstName = firstName; }

    public String getLastName() { return lastName; }

    public void setLastName(String lastName) { this.lastName = lastName; }

    public String getUserName() { return username; }

    public void setUserName(String username) { this.username = username; }

    public String getEmail() { return email; }

    public void setEmail(String email) { this.email = email; }

    public String getPassword() { return password; }

    public void setPassword(String password) { this.password = password; }

    public List<Classes> getCourses() { return this.courses; }

    public void setCourses(List<Classes> courses) { this.courses = courses; }

    public void addCourses(Classes course) { this.courses.add(course); }

    public void addClass(){

    }

    public void getClasses(){

    }
    public String printable(){
        return "\nName: " + getFirstName() + " " + getLastName() + " | Username: " + getUserName() + "\n";
    }
}
