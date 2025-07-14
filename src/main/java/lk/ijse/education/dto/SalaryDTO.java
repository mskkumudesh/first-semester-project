package lk.ijse.education.dto;

import lombok.*;

import java.sql.Date;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class SalaryDTO {
    private  String salaryID;
    private  String employeeID;
    private  Double amount;
    private  String date;
    private String month;
    private String payMethod;
}
