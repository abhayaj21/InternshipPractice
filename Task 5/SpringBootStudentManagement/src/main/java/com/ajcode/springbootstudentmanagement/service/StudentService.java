package com.ajcode.springbootstudentmanagement.service;

import com.ajcode.springbootstudentmanagement.entity.Student;
import com.ajcode.springbootstudentmanagement.repository.StudentRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class StudentService {

    private final StudentRepository studentRepository;

    public StudentService(StudentRepository studentRepository) {
        this.studentRepository = studentRepository;
    }

    public boolean existsByRoll(Integer roll){
        return studentRepository.existsByRollNo(roll);
    }

    public List<Student> getAllStudents()
    {
        return studentRepository.findAll();
    }

    public Student getStudentByRoll(Integer roll)
    {
        return studentRepository.findByRollNo(roll).orElseThrow(()->{
           throw  new RuntimeException("Student Not Found");
        });
    }


    public Student saveStudent(Student student)
    {
        if(existsByRoll(student.getRollNo()))
            throw new RuntimeException("Student Already Exists with roll");

        return studentRepository.save(student);
    }

    public String deleteByRollNo(Integer roll)
    {
        if(!existsByRoll(roll))
            throw new RuntimeException("Student Not Found");

        if(studentRepository.deleteByRollNo(roll)>0)
        {
            return "Student Deleted Successfully";
        }else {
            return "Student Failed To Delete";
        }
    }


    public Student updateStudent(Integer roll,Student student) {

        Student oldStudent = getStudentByRoll(roll);
        oldStudent.setStudentName(student.getStudentName());
        oldStudent.setMarks(student.getMarks());
        return studentRepository.save(oldStudent);
    }
}
