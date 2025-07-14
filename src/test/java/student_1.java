

import lk.ijse.education.model.student;
import lk.ijse.education.service.studentService;

public class student_1 {
    public static void main(String[] args) {
        studentService studentService = new studentService();

        //  Add a Student
        student newStudent = new student("S100", "John Doe", "2000-05-15", "123 Street", "990011223V", "0771234567");
        if (studentService.registerStudent(newStudent)) {
            System.out.println("Student Added Successfully!");
        } else {
            System.out.println("Failed to Add Student!");
        }

        //  Get Student by ID
        student retrievedStudent = studentService.findStudentById("S001");
        if (retrievedStudent != null) {
            System.out.println("Retrieved Student: " + retrievedStudent);
        } else {
            System.out.println("Student Not Found!");
        }

        // Update Student
        newStudent.setStudAdress("456 Updated Street");
        if (studentService.updateStudent(newStudent)) {
            System.out.println("Student Updated Successfully!");
        } else {
            System.out.println("Failed to Update Student!");
        }

        // Get All Students
        System.out.println("\nAll Students in Database:");
        studentService.getAllStudents().forEach(System.out::println);

        // Delete Student
        if (studentService.removeStudent("S001")) {
            System.out.println("Student Deleted Successfully!");
        } else {
            System.out.println("Failed to Delete Student!");
        }
    }
 }

