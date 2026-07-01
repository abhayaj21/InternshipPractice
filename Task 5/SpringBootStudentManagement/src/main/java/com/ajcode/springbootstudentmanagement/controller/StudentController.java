package com.ajcode.springbootstudentmanagement.controller;

import com.ajcode.springbootstudentmanagement.dto.StudentDto;
import com.ajcode.springbootstudentmanagement.entity.Student;
import com.ajcode.springbootstudentmanagement.service.StudentService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/students")
public class StudentController {

    private final StudentService studentService;

    public StudentController(StudentService studentService) {
        this.studentService = studentService;
    }

    @GetMapping
    public ResponseEntity<List<Student>> getAllStudents()
    {
        return ResponseEntity.ok(studentService.getAllStudents());
    }

    @PostMapping
    public ResponseEntity<Student> saveStudent(@RequestBody Student student)
    {
        return new ResponseEntity<>(studentService.saveStudent(student), HttpStatus.CREATED);
    }

    @DeleteMapping("delete/{roll}")
    public ResponseEntity<String> deleteStudent(@PathVariable Integer roll)
    {
        return ResponseEntity.ok(studentService.deleteByRollNo(roll));
    }

    @PutMapping("/update/{roll}")
    public ResponseEntity<Student> updateStudent(@PathVariable Integer roll, @RequestBody StudentDto studentDto)
    {
        Student student = studentService.getStudentByRoll(roll);
        student.setStudentName(studentDto.getName());
        student.setMarks(studentDto.getMarks());
        return ResponseEntity.ok(studentService.updateStudent(roll,student));
    }

    @GetMapping("/{roll}")
    public ResponseEntity<Student> getStudentByRoll(@PathVariable Integer roll)
    {
        return ResponseEntity.ok(studentService.getStudentByRoll(roll));
    }
}
