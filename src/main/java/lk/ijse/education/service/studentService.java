package lk.ijse.education.service;

import lk.ijse.education.model.student;
import lk.ijse.education.dao.studentDAO;

import java.util.List;

public class studentService {
    private final studentDAO studentDAO = new studentDAO();

    public boolean registerStudent(student student) {
        // Basic validations
        if (student.getstudID().isEmpty() || student.getstudName().isEmpty() || student.getNIC().isEmpty()) {
            System.out.println("Invalid Student Data!");
            return false;
        }

        // Check if student ID already exists
        if (studentDAO.getStudentById(student.getstudID()) != null) {
            System.out.println("Student ID already exists!");
            return false;
        }

        return studentDAO.addStudent(student);
    }

    public student findStudentById(String studID) {
        return studentDAO.getStudentById(studID);
    }

    public List<student> getAllStudents() {
        return studentDAO.getAllStudents();
    }

    public boolean updateStudent(student student) {
        return studentDAO.updateStudent(student);
    }

    public boolean removeStudent(String studID) {
        return studentDAO.deleteStudent(studID);
    }
}
