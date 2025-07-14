package lk.ijse.education.dto;

import lombok.*;

import java.sql.Date;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString

public class EnrollmentDTO {
    private String enrollmentID;
    private String courseID;
    private String courseName;
    private String badgeID;
    private String studID;
    private Date regiDate;
    }

