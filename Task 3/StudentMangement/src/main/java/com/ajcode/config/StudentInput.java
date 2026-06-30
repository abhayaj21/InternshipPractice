package com.ajcode.config;

import com.ajcode.entity.Student;
import com.ajcode.repository.StudentRepo;

import java.util.List;
import java.util.Scanner;

public class StudentInput {
    private StudentRepo repo;
    private Scanner scanner;

    public StudentInput(StudentRepo repo,Scanner scanner)
    {
        this.repo = repo;
        this.scanner = scanner;
    }

    //add new student
    public void addStudent()
    {
        System.out.print("Enter Student Name: ");
        String name = scanner.next();
        System.out.print("Enter Student Roll: ");
        int roll = scanner.nextInt();
        System.out.println("Enter Student Marks: ");
        int marks = scanner.nextInt();
        if(repo.existsByRoll(roll))
        {
            System.err.println("Student Already Exists With Roll");
        }
        else {
            Student student = new Student(name,roll,marks);
            if(repo.addStudent(student))
            {
                System.out.println("Student Added Successfully");
            }
            else {
                System.err.println("Failed To Add Student try again");
            }
        }
    }

    //show all students
    public void showStudents()
    {
        List<Student> students = repo.getAllStudents();
        System.out.println("+-------+--------------+------+-------+");
        System.out.println("| sr_no | Student Name | Roll | Marks |");
        System.out.println("+-------+--------------+------+-------+");
        if(students.isEmpty())
        {
            System.out.printf("|%13sStudents Not Available%-12s|","","");
            System.out.println("\n+-------+--------------+------+-------+");
        }
        else{
            int i = 1;
            for(Student student : students)
            {
                System.out.printf("|   %-4s|    %-10s|  %-4s|  %-5s|",i++,student.getName(),student.getRoll(),student.getMarks());
                System.out.println("\n+-------+--------------+------+-------+");
            }
        }
    }

    //update student
    public void updateStudent()
    {
        System.out.print("Enter Roll to Update: ");
        int roll = scanner.nextInt();
        if(repo.existsByRoll(roll))
        {
            Student student = repo.getStudentByRoll(roll);
            System.out.print("Update Student Name: ");
            String name = scanner.next();
            System.out.println("Update Marks: ");
            int marks = scanner.nextInt();
            student.setName(name);
            student.setMarks(marks);
            if(repo.updateStudent(student))
            {
                System.out.println("Student Updated Successfully");
            }
            else{
                System.err.println("Failed To Update Student");
            }
        }else {
            System.err.println("Student Not Exists with this roll");
        }
    }

    //delete student
    public void deleteStudent()
    {
        System.out.println("Enter Roll To Delete");
        int roll = scanner.nextInt();
        if(repo.existsByRoll(roll))
        {
            if(repo.deleteStudent(roll))
            {
                System.out.println("Student Deleted Successfully");
            }else {
                System.err.println("Failed To Delete Student");
            }
        }else {
            System.err.println("Student Not Exists With Roll");
        }
    }

}
