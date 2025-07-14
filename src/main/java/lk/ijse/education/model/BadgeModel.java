package lk.ijse.education.model;

import lk.ijse.education.dto.BadgeDTO;
import lk.ijse.education.util.CrudUtil;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class BadgeModel {

    public  boolean save(BadgeDTO dto) throws SQLException, ClassNotFoundException {
        String sql = "INSERT INTO badge (badgeID, badgeNo, courseID, courseName) VALUES (?, ?, ?, ?)";
        return CrudUtil.execute(sql,
                dto.getBadgeID(),
                dto.getBadgeNo(),
                dto.getCourseID(),
                dto.getCourseName());
    }

    public static boolean update(BadgeDTO dto) throws SQLException, ClassNotFoundException {
        String sql = "UPDATE badge SET badgeNo = ?, courseID = ?, courseName = ? WHERE badgeID = ?";
        return CrudUtil.execute(sql,
                dto.getBadgeNo(),
                dto.getCourseID(),
                dto.getCourseName(),
                dto.getBadgeID());
    }

    public static boolean delete(String badgeID) throws SQLException, ClassNotFoundException {
        return CrudUtil.execute("DELETE FROM badge WHERE badgeID = ?", badgeID);
    }

    public static BadgeDTO search(String badgeID) throws SQLException, ClassNotFoundException {
        ResultSet rst = CrudUtil.execute("SELECT * FROM badge WHERE badgeID = ?", badgeID);
        if (rst.next()) {
            return new BadgeDTO(
                    rst.getString("courseID"),
                    rst.getString("courseName"),
                    rst.getString("badgeID"),
                    rst.getString("badgeNo")
            );
        }
        return null;
    }

    public static List<BadgeDTO> getAll() throws SQLException, ClassNotFoundException {
        ResultSet rst = CrudUtil.execute("SELECT * FROM badge");
        List<BadgeDTO> badgeList = new ArrayList<>();

        while (rst.next()) {
            badgeList.add(new BadgeDTO(
                    rst.getString("courseID"),
                    rst.getString("courseName"),
                    rst.getString("badgeID"),
                    rst.getString("badgeNo")
            ));
        }
        return badgeList;
    }

    public static List<BadgeDTO> getBadgesByCourseID(String courseID) throws SQLException, ClassNotFoundException {
        ResultSet rst = CrudUtil.execute("SELECT * FROM badge WHERE courseID = ?", courseID);
        List<BadgeDTO> badgeList = new ArrayList<>();

        while (rst.next()) {
            badgeList.add(new BadgeDTO(
                    rst.getString("courseID"),
                    rst.getString("courseName"),
                    rst.getString("badgeID"),
                    rst.getString("badgeNo")
            ));
        }
        return badgeList;
    }
}
