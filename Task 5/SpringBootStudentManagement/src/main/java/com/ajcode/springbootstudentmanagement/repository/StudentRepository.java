package com.ajcode.springbootstudentmanagement.repository;

import com.ajcode.springbootstudentmanagement.entity.Student;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface StudentRepository extends JpaRepository<Student,Long> {

    boolean existsByRollNo(Integer roll);
    Optional<Student> findByRollNo(Integer roll);

    @Transactional
    long deleteByRollNo(Integer roll);
}
