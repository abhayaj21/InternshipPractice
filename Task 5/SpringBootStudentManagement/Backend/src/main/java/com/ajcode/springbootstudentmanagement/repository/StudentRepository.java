package com.ajcode.springbootstudentmanagement.repository;

import com.ajcode.springbootstudentmanagement.entity.Student;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;
import java.util.Set;

public interface StudentRepository extends JpaRepository<Student,Long> {

    //get All students is deleted false

    List<Student> findByIsDeleteFalse();

    boolean existsByRollNo(Integer roll);
    Optional<Student> findByRollNo(Integer roll);
    boolean existsByEmail(String email);

    //searching methods
    List<Student> findByStudentNameIgnoreCase(String name);
    Set<Student> findByEmail(String email);
    List<Student> findByCity(String city);
    List<Student> findByDepartmentDepartmentName(String name);

    @Transactional
    long deleteByRollNo(Integer roll);

    @Transactional
    @Modifying(clearAutomatically = true)
    @Query("UPDATE Student s SET s.isDelete = :status WHERE s.rollNo = :roll")
    int updateStudentDeletionStatus(@Param("status") Boolean status,@Param("roll") Integer roll);
}
