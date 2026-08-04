package com.ajcode.springbootstudentmanagement.service;

import com.ajcode.springbootstudentmanagement.dto.StudentDto;
import com.ajcode.springbootstudentmanagement.entity.Department;
import com.ajcode.springbootstudentmanagement.entity.Student;
import com.ajcode.springbootstudentmanagement.repository.StudentRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class StudentService {

    private final StudentRepository studentRepository;
    private final DepartmentService departmentService;

    public StudentService(StudentRepository studentRepository, DepartmentService departmentService) {
        this.studentRepository = studentRepository;
        this.departmentService = departmentService;
    }

    public boolean existsByRoll(Integer roll){
        return studentRepository.existsByRollNo(roll);
    }

    public List<Student> getAllStudents()
    {
        return studentRepository.findByIsDeleteFalse();
    }

    public Student getStudentByRoll(Integer roll)
    {
        return studentRepository.findByRollNo(roll).orElseThrow(()->{
           throw  new RuntimeException("Student Not Found");
        });
    }


    public Student saveStudent(Long deptId,Student student)
    {
        if(existsByRoll(student.getRollNo()))
            throw new RuntimeException("Student Already Exists with roll");
        if(existsByEmail(student.getEmail()))
            throw new RuntimeException("Student Already Exists with Email");

        Department department = departmentService.getDepartmentById(deptId);
        student.setDepartment(department);
        return studentRepository.save(student);
    }

    public boolean existsByEmail(String email)
    {
        return studentRepository.existsByEmail(email);
    }

    public String deleteByRollNo(Integer roll)
    {
        if(!existsByRoll(roll))
            throw new RuntimeException("Student Not Found");

        if(studentRepository.updateStudentDeletionStatus(true,roll)>0)
        {
            return "Student Deleted Successfully";
        }else {
            return "Student Failed To Delete";
        }
    }


    public Student updateStudent(Integer roll, StudentDto student) {
        Student oldStudent = getStudentByRoll(roll);
        Department department = departmentService.getDepartmentById(student.getDepartmentId());
        oldStudent.setStudentName(student.getName());
        oldStudent.setMarks(student.getMarks());
        oldStudent.setCity(student.getCity());
        oldStudent.setEmail(student.getEmail());
        oldStudent.setDepartment(department);
        return studentRepository.save(oldStudent);
    }


    public List<Student> sortStudentBy(String direction,String sortBy)
    {
        Sort sorting = direction.equalsIgnoreCase("asc") ?
                Sort.by(sortBy).ascending() : Sort.by(sortBy).descending();

        return studentRepository.findAll(sorting);
    }

    public Page<Student> applyPagination(Integer page, Integer size)
    {
        Pageable pageable = PageRequest.of(page,size);
        return studentRepository.findAll(pageable);
    }

    public List<Student> searchByStudentName(String name)
    {
        return studentRepository.findByStudentNameIgnoreCase(name);
    }

    public List<Student> searchByRollNo(Integer roll)
    {
        return studentRepository.findByRollNo(roll)
                .stream().collect(Collectors.toList());
    }

    public List<Student> searchByCity(String city)
    {
        return studentRepository.findByCity(city);
    }

    public Set<Student> searchByEmail(String email)
    {
        return studentRepository.findByEmail(email);
    }

    public List<Student> searchByDepartment(String department)
    {
        return studentRepository.findByDepartmentDepartmentName(department);
    }
}
