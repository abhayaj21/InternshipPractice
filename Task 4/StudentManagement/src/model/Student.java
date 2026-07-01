package model;

import java.io.Serializable;

public class Student implements Serializable {

    private Long id;
    private String name;
    private int roll;
    private int marks;

    public Student(Long id, String name, int roll, int marks) {
        this.id = id;
        this.name = name;
        this.roll = roll;
        this.marks = marks;
    }

    public Student() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getRoll() {
        return roll;
    }

    public void setRoll(int roll) {
        this.roll = roll;
    }

    public int getMarks() {
        return marks;
    }

    public void setMarks(int marks) {
        this.marks = marks;
    }
}
