package lk.ijse.education.model;

import lk.ijse.education.db.DBConnection;
import lk.ijse.education.dto.StudentDTO;
import lk.ijse.education.util.CrudUtil;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class StudentModel {

    public boolean saveStudent(StudentDTO studentDTO) throws SQLException, ClassNotFoundException {
        return CrudUtil.execute("INSERT INTO Student VALUES (?, ?, ?, ?, ?, ?)",
                studentDTO.getStudID(),
                studentDTO.getStudName(),
                studentDTO.getDOB(),
                studentDTO.getStudAddress(),
                studentDTO.getNIC(),
                studentDTO.getTelephone()
        );
    }

    public boolean updateStudent(StudentDTO studentDTO) throws SQLException, ClassNotFoundException {
        return CrudUtil.execute("UPDATE Student SET studName = ?, DOB = ?, studAddress = ?, NIC = ?, telephone = ? WHERE studID = ?",
                studentDTO.getStudName(),
                studentDTO.getDOB(),
                studentDTO.getStudAddress(),
                studentDTO.getNIC(),
                studentDTO.getTelephone(),
                studentDTO.getStudID()
        );
    }

    public  boolean deleteStudent(String studID) throws SQLException, ClassNotFoundException {
        return CrudUtil.execute("DELETE FROM Student WHERE studID = ?", studID);
    }

    public StudentDTO searchStudent(String studID) throws SQLException, ClassNotFoundException {
        ResultSet rst = CrudUtil.execute("SELECT * FROM Student WHERE studID = ?", studID);
        if (rst.next()) {
            return new StudentDTO(
                    rst.getString("studID"),
                    rst.getString("studName"),
                    rst.getDate("DOB"),
                    rst.getString("studAddress"),
                    rst.getString("NIC"),
                    rst.getString("telephone")
            );
        }
        return null;
    }

    public static ArrayList<StudentDTO> getAll() throws SQLException, ClassNotFoundException {
        ResultSet rst = CrudUtil.execute("SELECT * FROM Student");
        ArrayList<StudentDTO> list = new ArrayList<>();

        while (rst.next()) {
            list.add(new StudentDTO(
                    rst.getString("studID"),
                    rst.getString("studName"),
                    rst.getDate("DOB"),
                    rst.getString("studAddress"),
                    rst.getString("NIC"),
                    rst.getString("telephone")
            ));
        }
        return list;
    }
    public String loadNextID() throws SQLException, ClassNotFoundException {
        ResultSet rs = CrudUtil.execute("SELECT studID FROM student ORDER BY studID DESC LIMIT 1");
        return rs.next() ? rs.getString(1) : null;
    }
    public static List<StudentDTO>getStudentsByBadgeID(String badgeID) throws SQLException, ClassNotFoundException {
        ResultSet rst = CrudUtil.execute("SELECT s.*\n" +
                "FROM student s\n" +
                "JOIN enrollment e ON s.studID = e.studID\n" +
                "WHERE e.badgeID = ?;\n", badgeID);
        List<StudentDTO> list = new ArrayList<>();
        while (rst.next()) {
            list.add(new StudentDTO(
                    rst.getString("studID"),
                    rst.getString("studName"),
                    rst.getDate("DOB"),
                    rst.getString("studAddress"),
                    rst.getString("NIC"),
                    rst.getString("telephone")
            ));
        }
        return list;
    }
}
