package lk.ijse.education.model;

import lk.ijse.education.dto.DepartmentDTO;
import lk.ijse.education.util.CrudUtil;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class DepartmentModel {

    public boolean saveDepartment(DepartmentDTO dto) throws SQLException, ClassNotFoundException {
        return CrudUtil.execute(
                "INSERT INTO Department VALUES (?, ?, ?, ?, ?)",
                dto.getDepartID(),
                dto.getDepartName(),
                dto.getHeadTitle(),
                dto.getDepartHead(),
                dto.getTelephoneNo()
        );
    }

    public boolean updateDepartment(DepartmentDTO dto) throws SQLException, ClassNotFoundException {
        return CrudUtil.execute(
                "UPDATE Department SET departName = ?, headTitle = ?, departHead = ?, telephoneNo = ? WHERE departID = ?",
                dto.getDepartName(),
                dto.getHeadTitle(),
                dto.getDepartHead(),
                dto.getTelephoneNo(),
                dto.getDepartID()
        );
    }

    public boolean deleteDepartment(String departID) throws SQLException, ClassNotFoundException {
        return CrudUtil.execute("DELETE FROM Department WHERE departID = ?", departID);
    }

    public DepartmentDTO searchDepartment(String departID) throws SQLException, ClassNotFoundException {
        ResultSet rs = CrudUtil.execute("SELECT * FROM Department WHERE departID = ?", departID);
        if (rs.next()) {
            return new DepartmentDTO(
                    rs.getString("departID"),
                    rs.getString("departName"),
                    rs.getString("headTitle"),
                    rs.getString("departHead"),
                    rs.getString("telephoneNo")
            );
        }
        return null;
    }

    public static ArrayList<DepartmentDTO> getAll() throws SQLException, ClassNotFoundException {
        ResultSet rs = CrudUtil.execute("SELECT * FROM Department");
        ArrayList<DepartmentDTO> list = new ArrayList<>();

        while (rs.next()) {
            list.add(new DepartmentDTO(
                    rs.getString("departID"),
                    rs.getString("departName"),
                    rs.getString("headTitle"),
                    rs.getString("departHead"),
                    rs.getString("telephoneNo")
            ));
        }
        return list;
    }

    public String loadNextID() throws SQLException, ClassNotFoundException {
        ResultSet rs = CrudUtil.execute("SELECT departID FROM Department ORDER BY departID DESC LIMIT 1");
        return rs.next() ? rs.getString(1) : null;
    }
    public static String getDepartName(String departmentId) throws SQLException, ClassNotFoundException {
        ResultSet rst = CrudUtil.execute("SELECT departName FROM Department WHERE departID = ?", departmentId);

        if (rst.next()) {
            return rst.getString("departName");
        }
        return null;
    }

}
