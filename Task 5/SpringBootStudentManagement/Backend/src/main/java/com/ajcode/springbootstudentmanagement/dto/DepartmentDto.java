package com.ajcode.springbootstudentmanagement.dto;


import jakarta.validation.constraints.NotBlank;

public class DepartmentDto {
    @NotBlank(message = "enter department name")
    private String departmentName;

    public DepartmentDto() {
    }

    public DepartmentDto(String departmentName) {
        this.departmentName = departmentName;
    }

    public String getDepartmentName() {
        return departmentName;
    }

    public void setDepartmentName(String departmentName) {
        this.departmentName = departmentName;
    }
}
