package com.ajcode.springbootstudentmanagement.controller;


import com.ajcode.springbootstudentmanagement.dto.DepartmentDto;
import com.ajcode.springbootstudentmanagement.entity.Department;
import com.ajcode.springbootstudentmanagement.service.DepartmentService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/departments")
public class DepartmentController {

    private final DepartmentService departmentService;

    public DepartmentController(DepartmentService departmentService) {
        this.departmentService = departmentService;
    }

    @GetMapping
    public List<Department> getAllDepartments()
    {
        return departmentService.getAllDepartments();
    }

    @PostMapping
    public ResponseEntity<Department> addDepartment(@Valid @RequestBody DepartmentDto departmentDto)
    {
        return ResponseEntity.status(HttpStatus.CREATED).body(departmentService.addDepartment(departmentDto));
    }

    @GetMapping("/{deptId}")
    public Department getDepartmentById(@PathVariable Long deptId)
    {
        return departmentService.getDepartmentById(deptId);
    }

    @DeleteMapping("/{deptId}")
    public ResponseEntity<Void> deleteDepartment(@PathVariable Long deptId){
        departmentService.deleteDepartment(deptId);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }

    @PutMapping("/{deptId}")
    public ResponseEntity<Department> updateDepartment(@PathVariable Long deptId,@RequestBody DepartmentDto departmentDto){
        return ResponseEntity.status(HttpStatus.OK).body(departmentService.updateDepartment(deptId,departmentDto));
    }
}
