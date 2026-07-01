import model.Student;
import repository.StudentRepo;
import service.StudentInputService;

import java.util.Scanner;

public class StudentApp {

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        StudentRepo repo = new StudentRepo();
        StudentInputService studentInputService = new StudentInputService(scanner,repo);
        int choice;
        do{
            System.out.println("=====================================");
            System.out.println("Welcome To Student Management System");
            System.out.println("=====================================");

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
                    studentInputService.addStudent();
                    break;
                case 2:
                    studentInputService.showStudent();
                    break;
                case 3:
                    studentInputService.updateStudent();
                    break;
                case 4:
                    studentInputService.deleteStudent();
                    break;
                case 5:
                    System.out.println("Exited");
                    System.exit(0);
                    break;
                default:
                    System.out.println("Invalid Choice !!");
                    System.out.println("---------------------");
                    break;
            }


        }while (choice != 5);
    }
}
