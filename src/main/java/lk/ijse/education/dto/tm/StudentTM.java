package lk.ijse.education.dto.tm;

import lombok.*;

import java.sql.Date;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString


public class StudentTM {

        private String studID;
        private String studName;
        private Date DOB;
        private String studAddress;
        private String NIC;
        private String telephone;
    }

