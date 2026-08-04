package com.ajcode.springbootstudentmanagement.entity;

import jakarta.persistence.*;

@Entity
@Table(name = "students")
public class Student {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "student_id")
    private Long studentId;

    @Column(name = "student_name")
    private String studentName;

    @Column(name = "roll_no")
    private Integer rollNo;

    @Column(name = "marks")
    private Integer marks;

    @Column(name = "email",length = 200,nullable = false)
    private String email;

    @Column(name = "city",nullable = false,length = 50)
    private String city;

    @ManyToOne
    @JoinColumn(name = "dept_id")
    private Department department;

    @Column(name = "is_delete",nullable = false,columnDefinition = "BOOLEAN DEFAULT false")
    private Boolean isDelete = false;

    public Student(Long studentId, String studentName, Integer rollNo, Integer marks, String email, String city, Department department, Boolean isDelete) {
        this.studentId = studentId;
        this.studentName = studentName;
        this.rollNo = rollNo;
        this.marks = marks;
        this.email = email;
        this.city = city;
        this.department = department;
        this.isDelete = isDelete;
    }

    public Boolean getDelete() {
        return isDelete;
    }

    public void setDelete(Boolean delete) {
        isDelete = delete;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public Department getDepartment() {
        return department;
    }

    public void setDepartment(Department department) {
        this.department = department;
    }

    public Student() {
    }

    public Long getStudentId() {
        return studentId;
    }

    public void setStudentId(Long studentId) {
        this.studentId = studentId;
    }

    public String getStudentName() {
        return studentName;
    }

    public void setStudentName(String studentName) {
        this.studentName = studentName;
    }

    public Integer getRollNo() {
        return rollNo;
    }

    public void setRollNo(Integer rollNo) {
        this.rollNo = rollNo;
    }

    public Integer getMarks() {
        return marks;
    }

    public void setMarks(Integer marks) {
        this.marks = marks;
    }
}
