package lk.ijse.education.model;

import lk.ijse.education.dto.EnrollmentDTO;
import lk.ijse.education.util.CrudUtil;

import java.sql.ResultSet;
import java.sql.SQLException;

public class EnrollmentModel {
    public static String loadNextID() throws SQLException, ClassNotFoundException {
        String sql = "SELECT enrollmentID FROM enrollment ORDER BY enrollmentID DESC LIMIT 1";
        ResultSet rs = CrudUtil.execute(sql);
        if (rs.next()) {
            return rs.getString("enrollmentID");
        }
        return null;
    }

    public boolean saveEnrollment(EnrollmentDTO dto) throws SQLException, ClassNotFoundException {
        return CrudUtil.execute("insert into enrollment values (?,?,?,?,?,?)" ,
                dto.getEnrollmentID(),
                dto.getCourseID(),
                dto.getCourseName(),
                dto.getBadgeID(),
                dto.getStudID(),
                dto.getRegiDate()
        );
    }
    public static boolean isAlreadyEnrolled(String studID, String badgeID) throws SQLException, ClassNotFoundException {
        String sql = "SELECT * FROM enrollment WHERE studID = ? AND badgeID = ?";
        ResultSet rs = CrudUtil.execute(sql, studID, badgeID);
        return rs.next();
    }

}
