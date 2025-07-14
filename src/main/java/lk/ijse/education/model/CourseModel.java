package lk.ijse.education.model;

import lk.ijse.education.dto.CourseDTO;
import lk.ijse.education.util.CrudUtil;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class CourseModel {

    public boolean saveCourse(CourseDTO courseDTO) throws SQLException, ClassNotFoundException {
        return CrudUtil.execute("INSERT INTO Course VALUES (?, ?, ?, ?, ?, ?)",
                courseDTO.getCourseID(),
                courseDTO.getCourseName(),
                courseDTO.getDuration(),
                courseDTO.getNumberOfSemesters(),
                courseDTO.getDepartID(),
                courseDTO.getFee()
        );
    }

    public boolean updateCourse(CourseDTO courseDTO) throws SQLException, ClassNotFoundException {
        return CrudUtil.execute("UPDATE Course SET courseName = ?, duration = ?, numberOfSemesters = ?, fee = ?, departID = ? WHERE courseID = ?",
                courseDTO.getCourseName(),
                courseDTO.getDuration(),
                courseDTO.getNumberOfSemesters(),
                courseDTO.getFee(),
                courseDTO.getDepartID(),
                courseDTO.getCourseID()
        );
    }

    public boolean deleteCourse(String courseID) throws SQLException, ClassNotFoundException {
        return CrudUtil.execute("DELETE FROM Course WHERE courseID = ?", courseID);
    }

    public CourseDTO searchCourse(String courseID) throws SQLException, ClassNotFoundException {
        ResultSet rst = CrudUtil.execute("SELECT * FROM Course WHERE courseID = ?", courseID);
        if (rst.next()) {
            return new CourseDTO(
                    rst.getString("courseID"),
                    rst.getString("courseName"),
                    rst.getString("duration"),
                    rst.getString("numberOfSemesters"),
                    rst.getString("departID"),
                    rst.getDouble("fee")
            );
        }
        return null;
    }

    public static ArrayList<CourseDTO> getAll() throws SQLException, ClassNotFoundException {
        ResultSet rst = CrudUtil.execute("SELECT * FROM Course");
        ArrayList<CourseDTO> list = new ArrayList<>();

        while (rst.next()) {
            list.add(new CourseDTO(
                    rst.getString("courseID"),
                    rst.getString("courseName"),
                    rst.getString("duration"),
                    rst.getString("numberOfSemesters"),
                    rst.getString("departID"),
                    rst.getDouble("fee")
            ));
        }
        return list;
    }

    public String loadNextID() throws SQLException, ClassNotFoundException {
        ResultSet rs = CrudUtil.execute("SELECT courseID FROM Course ORDER BY courseID DESC LIMIT 1");
        return rs.next() ? rs.getString(1) : null;
    }

    public static List<CourseDTO> coursesByDepartID(String departmentId) throws SQLException, ClassNotFoundException {
        ResultSet rst = CrudUtil.execute("SELECT * FROM course WHERE departID = ?", departmentId);

        List<CourseDTO> courseList = new ArrayList<>();
        while (rst.next()) {
            courseList.add(new CourseDTO(
                    rst.getString("courseID"),
                    rst.getString("courseName"),
                    rst.getString("duration"),
                    rst.getString("numberOfSemesters"),
                    rst.getString("departID"),
                    rst.getDouble("fee")
            ));
        }
        return courseList;
    }

    public static String getCourseName(String courseID) throws SQLException, ClassNotFoundException {
        ResultSet rst = CrudUtil.execute("SELECT courseName FROM course WHERE courseID = ?", courseID);

        if (rst.next()) {
            return rst.getString("courseName");
        }
        return null;
    }
    public static String getCourseID(String badgeID) throws SQLException, ClassNotFoundException {
        ResultSet rst = CrudUtil.execute("""
            SELECT c.courseName
            FROM course c
            JOIN badge b ON c.courseID = b.courseID
            WHERE b.badgeID = ?
        """, badgeID);

        if (rst.next()) {
            return rst.getString("courseName");
        }
        return null;
    }


}




