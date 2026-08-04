package com.ajcode.springbootstudentmanagement.controller;

import com.ajcode.springbootstudentmanagement.dto.StudentDto;
import com.ajcode.springbootstudentmanagement.entity.Student;
import com.ajcode.springbootstudentmanagement.service.StudentService;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Collection;
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


    @PostMapping("/{deptId}")
    public ResponseEntity<Student> saveStudent(@PathVariable Long deptId,@RequestBody Student student)
    {
        return new ResponseEntity<>(studentService.saveStudent(deptId,student), HttpStatus.CREATED);
    }

    @DeleteMapping("delete/{roll}")
    public ResponseEntity<String> deleteStudent(@PathVariable Integer roll)
    {
        return ResponseEntity.status(HttpStatus.NO_CONTENT).body(studentService.deleteByRollNo(roll));
    }

    @PutMapping("/update/{roll}")
    public ResponseEntity<Student> updateStudent(@PathVariable Integer roll, @RequestBody StudentDto studentDto)
    {
        return ResponseEntity.ok(studentService.updateStudent(roll,studentDto));
    }

    @GetMapping("/{roll}")
    public ResponseEntity<Student> getStudentByRoll(@PathVariable Integer roll)
    {
        return ResponseEntity.ok(studentService.getStudentByRoll(roll));
    }

    @GetMapping("/sort")
    public ResponseEntity<List<Student>> sortStudentsBy(@RequestParam(defaultValue = "ASC") String direction,@RequestParam(defaultValue = "rollNo") String sortBy)
    {
        return ResponseEntity.ok(studentService.sortStudentBy(direction,sortBy));
    }

    @GetMapping("/page")
    public ResponseEntity<List<Student>> applyPagination(@RequestParam(defaultValue = "1") Integer page,@RequestParam(defaultValue = "2")Integer size)
    {
        return ResponseEntity.ok(studentService.applyPagination(page-1,size).toList());
    }

    @GetMapping("/search")
    public ResponseEntity<?> searchByName(
            @RequestParam(name = "studentName",required = false) String studentName,
            @RequestParam(name = "rollNo",required = false) Integer rollNo,
            @RequestParam(name = "cityName",required = false) String cityName,
            @RequestParam(name = "email",required = false) String email,
            @RequestParam(name = "departmentName",required = false) String departmentName
    )
    {
        if(studentName != null)
            return ResponseEntity.ok(studentService.searchByStudentName(studentName));
        else if(rollNo != null)
            return ResponseEntity.ok(studentService.searchByRollNo(rollNo));
        else if(cityName != null)
            return ResponseEntity.ok(studentService.searchByCity(cityName));
        else if(email != null)
            return ResponseEntity.ok(studentService.searchByEmail(email));
        else if(departmentName != null)
            return ResponseEntity.ok(studentService.searchByDepartment(departmentName));
        else
            return ResponseEntity.badRequest().body(HttpStatus.BAD_REQUEST);
    }
}
