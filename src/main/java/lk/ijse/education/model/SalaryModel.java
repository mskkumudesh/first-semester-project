package lk.ijse.education.model;

import lk.ijse.education.db.DBConnection;
import lk.ijse.education.dto.SalaryDTO;
import lk.ijse.education.util.CrudUtil;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class SalaryModel {
    public boolean saveSalary(SalaryDTO salaryDTO) throws SQLException, ClassNotFoundException {
        return CrudUtil.execute("insert into salary values (?,?,?,?,?,?)",
                salaryDTO.getSalaryID(),
                salaryDTO.getEmployeeID(),
                salaryDTO.getAmount(),
                salaryDTO.getDate(),
                salaryDTO.getMonth(),
                salaryDTO.getPayMethod()
        );
    }

    public boolean updateSalary(SalaryDTO salaryDTO) throws SQLException, ClassNotFoundException {
        return CrudUtil.execute("update salary where salaryID=?,employeeID=?,amount=?,date=?,month=?,payMethod=?",
                salaryDTO.getSalaryID(),
                salaryDTO.getEmployeeID(),
                salaryDTO.getAmount(),
                salaryDTO.getDate(),
                salaryDTO.getMonth(),
                salaryDTO.getPayMethod()
        );
    }

    public SalaryDTO searchSalary(String employeeID, String month) throws SQLException, ClassNotFoundException {
        ResultSet rst = CrudUtil.execute("select * from salary where employeeID=? and moth=?", employeeID, month);
        if (rst.next()) {
            return new SalaryDTO(
                    rst.getString("salaryID"),
                    rst.getString("employeeID"),
                    rst.getDouble("amount"),
                    rst.getString("date"),
                    rst.getString("month"),
                    rst.getString("payMethod")
            );
        }
        return null;
    }

    public boolean deleteSalary(String employeeID, String month) throws SQLException, ClassNotFoundException {
        return CrudUtil.execute("delete from salary where employeeID=? and month=?", employeeID, month);
    }

    public static List<SalaryDTO> getAll(String employeeID) throws SQLException, ClassNotFoundException {
        ResultSet rst = CrudUtil.execute("select * from salary", employeeID);
        List<SalaryDTO> salaryDTOList = new ArrayList<>();
        while (rst.next()) {
            salaryDTOList.add(new SalaryDTO(
                    rst.getString("salaryID"),
                    rst.getString("employeeID"),
                    rst.getDouble("amount"),
                    rst.getString("date"),
                    rst.getString("month"),
                    rst.getString("payMethod")
            ));
        }
        return salaryDTOList;
    }

    public static String loadNextID() throws SQLException, ClassNotFoundException {
        String sql = "SELECT salaryID FROM salary ORDER BY salaryID DESC LIMIT 1";
        ResultSet rs = CrudUtil.execute(sql);
        if (rs.next()) {
            return rs.getString("salaryID");
        }
        return null;
    }

    public static boolean isAlreadyPayed(String employeeID, String month) throws SQLException, ClassNotFoundException {
        String sql = "SELECT * FROM salary WHERE employeeID = ? AND month = ?";
        ResultSet rs = CrudUtil.execute(sql, employeeID, month);
        return rs.next();
    }

    public static SalaryDTO getLastPaidFromEmployeeID(String employeeID) throws SQLException, ClassNotFoundException {
        String sql = "SELECT * FROM salary WHERE employeeID = ? ORDER BY salaryID DESC LIMIT 1";
        ResultSet rst = CrudUtil.execute(sql, employeeID);
        if (rst.next()) {
            return new SalaryDTO(
                    rst.getString("salaryID"),
                    rst.getString("employeeID"),
                    rst.getDouble("amount"),
                    rst.getString("date"),
                    rst.getString("month"),
                    rst.getString("payMethod")
            );
        }
        return null;
    }
}
