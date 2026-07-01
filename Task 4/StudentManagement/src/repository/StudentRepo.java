package repository;

import model.Student;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class StudentRepo {
    private static final String FILE_DB = System.getProperty("user.dir") + File.separator + "src" + File.separator + "filedb" + File.separator + "students.dat";

    //loadData
    private static List<Student> loadData() {
        File file = new File(FILE_DB);

        if (!file.exists()) {
            try {
                File parentDir = file.getParentFile();
                if (!parentDir.exists()) {
                    parentDir.mkdirs();
                }

                // Create new file
                file.createNewFile();

                // Initialize with empty list
                saveStudent(new ArrayList<>());
            } catch (IOException ex) {
                ex.printStackTrace();
            }
            return new ArrayList<>();
        }

        // Read existing list
        try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(file))) {
            Object obj = ois.readObject();
            if (obj instanceof List) {
                return (List<Student>) obj;
            } else {
                // If file is corrupted, reset it
                saveStudent(new ArrayList<>());
                return new ArrayList<>();
            }
        } catch (Exception ex) {
            ex.printStackTrace();
            // Reset file if unreadable
            saveStudent(new ArrayList<>());
            return new ArrayList<>();
        }
    }



    //save all student
    private static boolean saveStudent(List<Student> students)
    {
        try(ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(FILE_DB)))
        {
            oos.writeObject(students);
            return true;
        }catch (Exception exception)
        {
            exception.printStackTrace();
            return false;
        }
    }


    public boolean existsByRoll(int roll)
    {
        List<Student> students = loadData();
        return students.stream().anyMatch(s -> s.getRoll() == roll);
    }

    public boolean addStudent(Student student) {
        List<Student> students = loadData();
        students.add(student);
        if(saveStudent(students))
            return true;
        else
            return false;
    }

    public List<Student> readAllStudent()
    {
        return loadData();
    }

    //update
    public boolean updateStudent(int roll,Student student)
    {
        List<Student> students = loadData();
        boolean isUpdate = false;
        for(Student std : students)
        {
            if(std.getRoll() == roll)
            {
                std.setName(student.getName());
                std.setMarks(student.getMarks());
                saveStudent(students);
                isUpdate = true;
                break;
            }
        }
        return isUpdate;
    }


    //delete student
    public boolean deleteStudent(int roll)
    {
        List<Student> students = loadData();
        boolean isDelete = students.removeIf(s->s.getRoll() == roll);
        saveStudent(students);
        return isDelete;
    }
}
