package lk.ijse.education.model;

import lk.ijse.education.dto.EmployeeDTO;
import lk.ijse.education.util.CrudUtil;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class EmployeeModel {
    public boolean saveEmployee(EmployeeDTO employeeDTO) throws SQLException, ClassNotFoundException {
        return CrudUtil.execute("insert into  Employees values(?,?,?,?,?,?,?,?)",
        employeeDTO.getEmployeeID(), employeeDTO.getEmployeeTitle(), employeeDTO.getEmployeeName(),employeeDTO.getDepartmentID(),employeeDTO.getJobRoll(),employeeDTO.getDOB(),employeeDTO.getTelephoneNo(),employeeDTO.getEmployeeAddress());
    }

    public boolean updateEmployee(EmployeeDTO employeeDTO) throws SQLException, ClassNotFoundException {
        return CrudUtil.execute("update employees set employeeTitle=?,employeeName=?,departmentID=?,jobRoll=?,DOB=?,telephoneNo=?,employeeAddress=? where employeeID=?",
                 employeeDTO.getEmployeeTitle(), employeeDTO.getEmployeeName(),employeeDTO.getDepartmentID(),employeeDTO.getJobRoll(),employeeDTO.getDOB(),employeeDTO.getTelephoneNo(),employeeDTO.getEmployeeAddress(),employeeDTO.getEmployeeID());
    }
    public boolean deleteEmployee(String employeeID) throws SQLException, ClassNotFoundException {
        return CrudUtil.execute("delete from Employees where employeeID=?", employeeID);
    }
    public EmployeeDTO searchEmployee(String employeeID) throws SQLException, ClassNotFoundException {
        ResultSet rst = CrudUtil.execute("select * from employees where employeeID=?",employeeID);
        if (rst.next()) {
            return new EmployeeDTO(
                    rst.getString("employeeID"),
                    rst.getString("employeeTitle"),
                    rst.getString("employeeName"),
                    rst.getString("departmentID"),
                    rst.getString("jobRoll"),
                    rst.getDate("DOB"),
                    rst.getString("telephoneNo"),
                    rst.getString("employeeAddress")

            );
        }
        return null;
        }
    public static ArrayList<EmployeeDTO> getAll() throws SQLException, ClassNotFoundException {
        ResultSet rst = CrudUtil.execute("SELECT * FROM Employees");
        ArrayList<EmployeeDTO> list = new ArrayList<>();

        while (rst.next()) {
            list.add(new EmployeeDTO(
                    rst.getString("employeeID"),
                    rst.getString("employeeTitle"),
                    rst.getString("employeeName"),
                    rst.getString("departmentID"),
                    rst.getString("jobRoll"),
                    rst.getDate("DOB"),
                    rst.getString("telephoneNo"),
                    rst.getString("employeeAddress")
            ));
        }
        return list;
    }
    public static String loadNextID() throws SQLException, ClassNotFoundException {
        ResultSet rs = CrudUtil.execute("SELECT employeeID FROM employees ORDER BY employeeID DESC LIMIT 1");
        return rs.next() ? rs.getString(1) : null;
    }
    public static String getLecturerName(String lecturerID) throws SQLException, ClassNotFoundException {
            String sql = "SELECT employeeName FROM employees WHERE employeeID = ? AND jobRoll = 'Lecturer'";
            ResultSet rst = CrudUtil.execute(sql, lecturerID);
            if (rst.next()) {
                return rst.getString("employeeName");
            }
            return null;
        }
    }

