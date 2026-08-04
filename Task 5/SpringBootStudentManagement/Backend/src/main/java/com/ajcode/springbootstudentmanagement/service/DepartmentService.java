package com.ajcode.springbootstudentmanagement.service;

import com.ajcode.springbootstudentmanagement.dto.DepartmentDto;
import com.ajcode.springbootstudentmanagement.entity.Department;
import com.ajcode.springbootstudentmanagement.repository.DepartmentRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class DepartmentService {
    private final DepartmentRepository departmentRepository;

    public DepartmentService(DepartmentRepository departmentRepository) {
        this.departmentRepository = departmentRepository;
    }

    public Department addDepartment(DepartmentDto departmentDto)
    {
        String deptName = departmentDto.getDepartmentName();
        if(departmentRepository.existsByDepartmentName(deptName))
        {
            throw new RuntimeException("Department Already Exists");
        }
        Department department = new Department();
        department.setDepartmentName(deptName);
        return departmentRepository.save(department);
    }

    public Department getDepartmentById(Long deptId)
    {
        return departmentRepository.findById(deptId)
                .orElseThrow(()->{
                   throw new RuntimeException("Department Not Found");
                });
    }

    public List<Department> getAllDepartments()
    {
        return departmentRepository.findAll();
    }

    public void deleteDepartment(Long deptId)
    {
        departmentRepository.deleteById(deptId);
    }

    public Department updateDepartment(Long deptId,DepartmentDto departmentDto){
        Department oldDepartment = getDepartmentById(deptId);
        oldDepartment.setDepartmentName(departmentDto.getDepartmentName());
        return departmentRepository.save(oldDepartment);
    }
}
