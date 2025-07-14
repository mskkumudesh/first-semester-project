package lk.ijse.education.dto;

import java.sql.Date;

public class PaymentDTO {
    private  String paymentID;
    private  String studID;
    private  String courseID;
    private  String semesterNo;
    private Date date;
    private Double amount;

    public PaymentDTO() {}

    public PaymentDTO(String paymentID, String studID, String courseID, String semesterNo,Date date, Double amount) {
        this.paymentID = paymentID;
        this.studID = studID;
        this.courseID = courseID;
        this.semesterNo = semesterNo;
        this.date = date;
        this.amount = amount;
    }

    public String getPaymentID() {return paymentID;}
    public void setPaymentID(String paymentID) {this.paymentID = paymentID;}
    public String getStudID() {return studID;}
    public void setStudID(String studID) {this.studID = studID;}
    public String getCourseID() {return courseID;}
    public void setCourseID(String courseID) {this.courseID = courseID;}
    public String getSemesterNo() {return semesterNo;}
    public void setSemesterNo(String semesterNo) {this.semesterNo = semesterNo;}
    public Date getDate() {return date;}
    public void setDate(Date date) {this.date = date;}
    public Double getAmount() {return amount;}
    public void setAmount(Double amount) {this.amount = amount;}

    @Override
    public String toString() {
        return "PaymentModel{" +
                "paymentID='" + paymentID + '\'' +
                ", studID='" + studID + '\'' +
                ", courseID='" + courseID + '\'' +
                ", semesterNo='" + semesterNo + '\'' +
                ", date=" + date +
                ", amount=" + amount +
                '}';
    }
}
