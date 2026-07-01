package service;

import model.Student;
import repository.StudentRepo;

import java.util.List;
import java.util.Scanner;

public class StudentInputService {

    private final Scanner scanner;
    private final StudentRepo studentRepo;

    public StudentInputService(Scanner scanner, StudentRepo studentRepo) {
        this.scanner = scanner;
        this.studentRepo = studentRepo;
    }


    //show all students
    public void showStudent()
    {
        List<Student> students = studentRepo.readAllStudent();
        System.out.println("+----+---------+--------------+-------+");
        System.out.println("| Id | Roll No | Student Name | Marks |");
        System.out.println("+----+---------+--------------+-------+");

        if(students.isEmpty())
        {
            System.out.printf("|\t\t  Students Is Empty%-11s|","");
            System.out.println("\n+----+---------+--------------+-------+");
        }else{
            int i = 1;
            for (Student student : students)
            {
                System.out.printf("|  %-2s|    %-5s|      %-8s|   %-4s|",i++,student.getRoll(),student.getName(),student.getMarks());
                System.out.println("\n+----+---------+--------------+-------+");
            }
        }
    }

    public void addStudent(){}
    public void updateStudent(){}
    public void deleteStudent(){}
}
