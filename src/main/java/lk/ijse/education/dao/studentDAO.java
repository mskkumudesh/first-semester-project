package lk.ijse.education.dao;

import lk.ijse.education.model.student;
import lk.ijse.education.DBConnection;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class studentDAO {
    public boolean addStudent(student student) {
        String query = "INSERT INTO student (studID, studName, DOB, studAddress, NIC, telephone) VALUES (?, ?, ?, ?, ?, ?)";
        Connection connection = DBConnection.getInstance().getConnection(); // Get an open connection
        try (PreparedStatement stmt = connection.prepareStatement(query)) {
            stmt.setString(1, student.getstudID());
            stmt.setString(2, student.getstudName());
            stmt.setString(3, student.getDOB());
            stmt.setString(4, student.getStudAdress());
            stmt.setString(5, student.getNIC());
            stmt.setString(6, student.getTelephone());

            return stmt.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }


    public student getStudentById(String studID) {
        String query = "SELECT * FROM student WHERE studID = ?";
             Connection connection = DBConnection.getInstance().getConnection();
        try( PreparedStatement stmt = connection.prepareStatement(query)) {

            stmt.setString(1, studID);
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                return new student(
                        rs.getString("studID"),
                        rs.getString("studName"),
                        rs.getString("DOB"),
                        rs.getString("studAddress"),
                        rs.getString("NIC"),
                        rs.getString("telephone")
                );
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    public List<student> getAllStudents() {
        List<student> students = new ArrayList<>();
        String query = "SELECT * FROM student";

             Connection conn = DBConnection.getInstance().getConnection();
    try (    PreparedStatement stmt = conn.prepareStatement(query);
             ResultSet rs = stmt.executeQuery()) {

            while (rs.next()) {
                students.add(new student(
                        rs.getString("studID"),
                        rs.getString("studName"),
                        rs.getString("DOB"),
                        rs.getString("studAddress"),
                        rs.getString("NIC"),
                        rs.getString("telephone")
                ));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return students;
    }

    public boolean updateStudent(student student) {
        String query = "UPDATE student SET studName=?, DOB=?, studAddress=?, NIC=?, telephone=? WHERE studID=?";
        Connection conn = DBConnection.getInstance().getConnection();
try (   PreparedStatement stmt = conn.prepareStatement(query)) {

            stmt.setString(1, student.getstudName());
            stmt.setString(2, student.getDOB());
            stmt.setString(3, student.getStudAdress());
            stmt.setString(4, student.getNIC());
            stmt.setString(5, student.getTelephone());
            stmt.setString(6, student.getstudID());

            return stmt.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public boolean deleteStudent(String studID) {
        String query = "DELETE FROM student WHERE studID=?";
        Connection conn = DBConnection.getInstance().getConnection();
try (   PreparedStatement stmt = conn.prepareStatement(query)) {

            stmt.setString(1, studID);
            return stmt.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }
}

