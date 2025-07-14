package lk.ijse.education.model;

import lk.ijse.education.db.DBConnection;
import lk.ijse.education.dto.ModuleDTO;
import lk.ijse.education.util.CrudUtil;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class ModuleModel {

    public String saveModule(ModuleDTO moduleDTO) throws SQLException, ClassNotFoundException {
        String sql = "INSERT INTO module VALUES (?,?,?,?,?)";
        boolean isSaved = CrudUtil.execute(
                sql,
                moduleDTO.getModuleID(),
                moduleDTO.getModuleNo(),
                moduleDTO.getModuleName(),
                moduleDTO.getSemNo(),
                moduleDTO.getCourseID()
        );
        return isSaved ? "saved" : "failed";
    }

    public String updateModule(ModuleDTO moduleDTO) throws SQLException, ClassNotFoundException {
        String sql = "UPDATE module SET moduleNo=?, moduleName=?, semNo=?, courseID=? WHERE moduleID=?";
        boolean isUpdated = CrudUtil.execute(
                sql,
                moduleDTO.getModuleNo(),
                moduleDTO.getModuleName(),
                moduleDTO.getSemNo(),
                moduleDTO.getCourseID(),
                moduleDTO.getModuleID()
        );
        return isUpdated ? "updated" : "failed";
    }
    public static ModuleDTO searchModule(String moduleID) throws SQLException, ClassNotFoundException {
        String sql = "SELECT * FROM module WHERE moduleID = ?";
        ResultSet rst = CrudUtil.execute(sql, moduleID);
        if (rst.next()) {
            return new ModuleDTO(
                    rst.getString("moduleID"),
                    rst.getString("moduleNo"),
                    rst.getString("moduleName"),
                    rst.getString("semNo"),
                    rst.getString("courseID")
            );
        }
        return null;
    }


    public static List<ModuleDTO> modulesByCourseID(String courseID) throws SQLException, ClassNotFoundException {
        String sql = "SELECT * FROM module WHERE courseID = ?";
        ResultSet rst = CrudUtil.execute(sql, courseID);

        List<ModuleDTO> moduleList = new ArrayList<>();

        while (rst.next()) {
            moduleList.add(new ModuleDTO(
                    rst.getString("moduleID"),
                    rst.getString("moduleNo"),
                    rst.getString("moduleName"),
                    rst.getString("semNo"),
                    rst.getString("courseID")
            ));
        }
        return moduleList;
    }

    public static String getBadgeIDByModule(String moduleID) throws SQLException, ClassNotFoundException {
        String sql = """
                SELECT b.badgeID
                FROM module m
                JOIN course c ON m.courseID = c.courseID
                JOIN badge b ON c.courseID = b.courseID
                WHERE m.moduleID = ?
            """;
        ResultSet rst = CrudUtil.execute(sql, moduleID);

        if (rst.next()) {
            return rst.getString("badgeID");
        }
        return null;
    }
    public static String getBadgeIDByModuleID(String moduleID) throws SQLException, ClassNotFoundException {
        String sql = "SELECT badgeID FROM badge_module WHERE moduleID = ?";
        ResultSet rst = CrudUtil.execute(sql, moduleID);
        if (rst.next()) {
            return rst.getString("badgeID");
        }
        return null;
    }
    public static String getLecturerIDByModuleID(String moduleID) throws SQLException, ClassNotFoundException {
        String sql = "SELECT bml.lecturerID FROM badge_module_lecturer bml " +
                "JOIN badge_module bm ON bml.badge_moduleID = bm.badge_moduleID " +
                "WHERE bm.moduleID = ?";
        ResultSet rst = CrudUtil.execute(sql, moduleID);
        if (rst.next()) {
            return rst.getString("lecturerID");
        }
        return null;
    }
    public static String getCourseNameByModuleID(String moduleID) throws SQLException, ClassNotFoundException {
        String sql = "SELECT c.courseName " +
                "FROM module m " +
                "JOIN course c ON m.courseID = c.courseID " +
                "WHERE m.moduleID = ?";

        ResultSet rst = CrudUtil.execute(sql, moduleID);
        if (rst.next()) {
            return rst.getString("courseName");
        }
        return null;
    }

}
