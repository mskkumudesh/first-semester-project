package lk.ijse.education.model;

import lk.ijse.education.db.DBConnection;
import lk.ijse.education.dto.PaymentDTO;

import java.sql.ResultSet;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ArrayList;


public class PaymentModel {
    public String savePayment(PaymentDTO paymentDTO) throws SQLException, ClassNotFoundException {
        Connection connection =DBConnection.getInstance().getConnection();
        String sql = "INSERT INTO payment VALUES(?,?,?,?,?,?)";
        PreparedStatement statement = connection.prepareStatement(sql);
        statement.setString(1, paymentDTO.getPaymentID());
        statement.setString(2,paymentDTO.getStudID());
        statement.setString(3,paymentDTO.getCourseID());
        statement.setString(4,paymentDTO.getSemesterNo());
        statement.setDate(5,paymentDTO.getDate());
        statement.setDouble(6,paymentDTO.getAmount());
        return statement.executeUpdate() >0 ? "saved":"failed";
    }
    public String updatePayment(PaymentDTO paymentDTO) throws SQLException, ClassNotFoundException {
        Connection connection =DBConnection.getInstance().getConnection();
        String sql = "UPDATE payment SET paymentID=?,studID=?,courseID=?,semesterNo=?,date=?,amount=? WHERE studentID=?";
        PreparedStatement statement = connection.prepareStatement(sql);
        statement.setString(1, paymentDTO.getPaymentID());
        statement.setString(2,paymentDTO.getStudID());
        statement.setString(3,paymentDTO.getCourseID());
        statement.setString(4,paymentDTO.getSemesterNo());
        statement.setDate(5,paymentDTO.getDate());
        statement.setDouble(6,paymentDTO.getAmount());
        return statement.executeUpdate() >0 ? "updated":"failed";
    }

    public String deletePayment(String paymentID) throws SQLException, ClassNotFoundException {
        Connection connection =DBConnection.getInstance().getConnection();
        String sql = "DELETE FROM payment WHERE paymentID=?";
        PreparedStatement statement = connection.prepareStatement(sql);
        statement.setString(1, paymentID);
        return statement.executeUpdate() >0 ? "deleted":"failed";
    }

    public PaymentDTO searchPayment(String paymentID) throws SQLException, ClassNotFoundException {
       Connection connection =DBConnection.getInstance().getConnection();
       String sql = "SELECT * FROM payment WHERE paymentID=?";
       PreparedStatement statement = connection.prepareStatement(sql);
       statement.setString(1, paymentID);
       ResultSet rst = statement.executeQuery();
       if (rst.next()) {
           PaymentDTO paymentDTO = new PaymentDTO(rst.getString(1),rst.getString(2),rst.getString(3),rst.getString(4),rst.getDate(5),rst.getDouble(6));
           return paymentDTO;
       }
       return null;
    }

    public ArrayList<PaymentDTO> getAll() throws SQLException, ClassNotFoundException {
        Connection connection =DBConnection.getInstance().getConnection();
        String sql = "SELECT * FROM payment";
        PreparedStatement statement = connection.prepareStatement(sql);
        ResultSet rst = statement.executeQuery();
        ArrayList<PaymentDTO> list = new ArrayList<>();
        while (rst.next()) {
            PaymentDTO paymentDTO = new PaymentDTO(rst.getString(1),rst.getString(2),rst.getString(3),rst.getString(4),rst.getDate(5),rst.getDouble(6));
            list.add(paymentDTO);
        }
        return list;
    }

}
