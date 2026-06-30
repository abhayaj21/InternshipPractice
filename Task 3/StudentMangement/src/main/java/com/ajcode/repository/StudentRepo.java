package com.ajcode.repository;

import com.ajcode.config.DbConfig;
import com.ajcode.entity.Student;

import java.sql.*;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class StudentRepo {

    private static final Connection con = DbConfig.getConnection();

    //get All students
    public List<Student> getAllStudents() {
        List<Student> students = new ArrayList<>();
        String sql = "SELECT * FROM students";
        try {
            Statement stmt = con.createStatement();
            ResultSet rs = stmt.executeQuery(sql);
            while (rs.next()) {
                String name = rs.getString(2);
                int roll = rs.getInt(3);
                int marks = rs.getInt(4);
                Student student = new Student(name, roll, marks);
                students.add(student);
            }
            DbConfig.close(stmt, rs);
            return students.isEmpty() ? Collections.emptyList() : students;
        } catch (Exception ex) {
            ex.printStackTrace();
            return Collections.emptyList();
        }

    }

    //check student is already exists or not with roll
    public boolean existsByRoll(int roll) {
        String sql = "SELECT COUNT(*) FROM students WHERE roll = ?";
        try {
            PreparedStatement ps = con.prepareStatement(sql);
            ps.setInt(1, roll);
            ResultSet rs = ps.executeQuery();
            int result = 0;
            if(rs.next())
                result = rs.getInt(1);

            DbConfig.close(ps,rs);
            return result > 0;
        } catch (Exception ex) {
            ex.printStackTrace();
            return false;
        }
    }

    //get student by roll
    public Student getStudentByRoll(int roll) {
        String sql = "SELECT * FROM students WHERE roll = ?";
        try {
            PreparedStatement ps = con.prepareStatement(sql);
            ps.setInt(1, roll);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                int rollno = rs.getInt(3);
                String name = rs.getString(2);
                int marks = rs.getInt(4);
                DbConfig.close(ps, rs);
                return new Student(name, rollno, marks);
            } else {
                return null;
            }
        } catch (Exception ex) {
            ex.printStackTrace();
            return null;
        }
    }


    //update student
    public boolean updateStudent(Student student) {

        String update = "UPDATE students SET name = ?, marks = ? WHERE roll = ?";
        try {
            PreparedStatement ps = con.prepareStatement(update);
            ps.setString(1, student.getName());
            ps.setInt(2, student.getMarks());
            ps.setInt(3, student.getRoll());
            int result = ps.executeUpdate();
            DbConfig.close(ps);
            return result > 0;
        } catch (Exception ex) {
            ex.printStackTrace();
            return false;
        }
    }

    //delete student
    public boolean deleteStudent(int roll) {
        String sql = "DELETE FROM students WHERE roll = ?";
        try {
            PreparedStatement ps = con.prepareStatement(sql);
            ps.setInt(1, roll);
            int result = ps.executeUpdate();
            DbConfig.close(ps);
            return result > 0;
        } catch (Exception ex) {
            ex.printStackTrace();
            return false;
        }
    }

    //add student
    public boolean addStudent(Student student) {
        String sql = "INSERT INTO students(name,roll,marks) VALUES(?,?,?)";
        try {
            PreparedStatement ps = con.prepareStatement(sql);
            ps.setString(1, student.getName());
            ps.setInt(2, student.getRoll());
            ps.setInt(3, student.getMarks());
            int result = ps.executeUpdate();
            DbConfig.close(ps);
            return result > 0;
        } catch (Exception ex) {
            ex.printStackTrace();
            return false;
        }
    }

    //close con
    public void closeCon() throws SQLException {
        con.close();
    }

}
