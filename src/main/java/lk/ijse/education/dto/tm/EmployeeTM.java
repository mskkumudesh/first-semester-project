package lk.ijse.education.dto.tm;
import lombok.*;

import java.sql.Date;
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString

public class EmployeeTM {
    private String employeeID;
    private String employeeTitle;
    private String employeeName;
    private String departmentID;
    private String jobRoll;
    private Date DOB;
    private String telephoneNo;
    private String employeeAddress;
}
