package lk.ijse.education.dto.tm;

import lombok.*;

@ToString
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor

public class CourseTM {
    private String courseID;
    private String courseName;
    private String duration;
    private String numberOfSemesters;
    private String departID;
    private Double fee;
}
