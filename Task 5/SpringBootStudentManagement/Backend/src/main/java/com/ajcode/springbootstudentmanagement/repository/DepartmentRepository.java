package com.ajcode.springbootstudentmanagement.repository;

import com.ajcode.springbootstudentmanagement.entity.Department;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface DepartmentRepository extends JpaRepository<Department,Long> {
    boolean existsByDepartmentName(String name);
}
