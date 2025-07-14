package lk.ijse.education.dto;

import lombok.*;

import java.sql.Date;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class StudentDTO {
    private String studID;
    private String studName;
    private Date DOB;
    private String studAddress;
    private String NIC;
    private String telephone;

}