package com.example.Tarp.model;

import java.util.ArrayList;
import java.util.List;
import com.example.Tarp.model.Teacher;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.example.Tarp.Api.ApiClientFactory.GetTeacherApi;


public class Classes {
    private String className;
    private String classSection;
    private String classTime;
    private String classDescription;
    private String teacherName;
    private int teachID;
    private int classID;
    private Teacher teacher;
    private List<Student> students;

    public Classes() {
        students = new ArrayList<>();
    }

    public Classes(String className) {
        this.className = className;
        students = new ArrayList<>();
    }

    public Classes(String className, String classTime, String classSection, String classDescription, String teacherName, int teachID) {
        this.className = className;
        this.classTime = classTime;
        this.classSection = classSection;
        this.classDescription = classDescription;
        this.teacherName = teacherName;
        this.teachID = teachID;
        students = new ArrayList<>();
    }

    public int getClassID() {
        return classID;
    }

    public void setClassID(int classID) {
        this.classID = classID;
    }

    public int getTeachID() {
        return teachID;
    }

    public void setTeachID(int teachID) {
        this.teachID = teachID;
    }

    public String getClassName() {
        return className;
    }

    public void setClassName(String className) {
        this.className = className;
    }

    public String getClassSection() {
        return classSection;
    }

    public void setClassSection(String classSection) {
        this.classSection = classSection;
    }

    public String getClassTime() {
        return classTime;
    }

    public void setClassTime(String classTime) {
        this.classTime = classTime;
    }

    public String getClassDescription() {
        return classDescription;
    }

    public void setClassDescription(String classDescription) {
        this.classDescription = classDescription;
    }
    public String getTeacherName() { return teacherName; }

    public void setTeacherName(String teacherName) {
        this.teacherName = teacherName;
    }

    public Teacher getTeacher() { return this.teacher; }

    public String getTeachName() { return this.teacher.getLastName(); }

    public void setTeacher(Teacher teacher) { this.teacher = teacher; }

    public List<Student> getStudents() { return this.students; }

    public void setStudents(List<Student> students) { this.students = students; }

    public void addStudents(Student student) { this.students.add(student); }

    public String classPrintable(){
        return "\n" + getClassName() + " | Section: " + getClassSection() + "\n";
    }

    public String teachPrintable(){
        return "\n" + getClassName() + " | Section " + getClassSection() + " | " + getClassTime() + "\n";
    }

    public String stuPrintable(){
        return "\n" + getClassTime() + " | " + getClassName() + " | Sect: "  + getClassSection() + " | " + teacher.getLastName() + "\n";
    }

    public String adminPrintable(){
        return "\n" + getClassName() + " | Sect: " + getClassSection() + " | " + getClassTime() + "\n";
    }
}
