package com.ajcode;

import com.ajcode.config.DbConfig;
import com.ajcode.config.StudentInput;
import com.ajcode.repository.StudentRepo;

import java.sql.SQLException;
import java.util.Scanner;

public class StudentApp {
    public static void main(String[] args)
    {
        Scanner scanner = new Scanner(System.in);
        StudentRepo repo = new StudentRepo();
        StudentInput studentInput = new StudentInput(repo,scanner);
        System.out.println("=========================================");
        System.out.println("Welcome To Student Management System");
        System.out.println("=========================================");
        int choice;

        do {
            System.out.println("\n---------------------");
            System.out.println("1. Add Student");
            System.out.println("---------------------");
            System.out.println("2. View Students");
            System.out.println("---------------------");
            System.out.println("3. Update Student");
            System.out.println("---------------------");
            System.out.println("4. Delete Student");
            System.out.println("---------------------");
            System.out.println("5. Exit");
            System.out.println("---------------------");
            System.out.print("Enter Your Choice: ");
            choice = scanner.nextInt();
            System.out.println("---------------------");

            switch (choice)
            {
                case 1:
                    studentInput.addStudent();
                    break;
                case 2:
                    studentInput.showStudents();
                    break;
                case 3:
                    studentInput.updateStudent();
                    break;
                case 4:
                    studentInput.deleteStudent();
                    break;
                case 5:
                    Thread t1 = new Thread(()->{
                        System.out.println("Closing....");
                        try {
                            repo.closeCon();
                            Thread.sleep(1000);
                            System.out.println("Exited");
                            System.exit(0);
                        } catch (InterruptedException | SQLException ex) {
                            ex.printStackTrace();
                        }
                    });
                    t1.start();
                    break;
                default:
                    System.out.println("Invalid Choice");
                    break;
            }
        }while (choice != 5);
    }
}
