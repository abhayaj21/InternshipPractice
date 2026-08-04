package com.ajcode.springbootstudentmanagement.dto;


public class StudentDto {

    private String name;
    private String email;
    private Integer marks;
    private String city;
    private Long departmentId;

    public Long getDepartmentId() {
        return departmentId;
    }

    public void setDepartmentId(Long departmentId) {
        this.departmentId = departmentId;
    }

    public StudentDto() {
    }

    public StudentDto(String name, String email, Integer marks, String city, Long departmentId) {
        this.name = name;
        this.email = email;
        this.marks = marks;
        this.city = city;
        this.departmentId = departmentId;
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

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getMarks() {
        return marks;
    }

    public void setMarks(Integer marks) {
        this.marks = marks;
    }
}
