package lk.ijse.education.dto;

import lombok.*;

@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class CourseDTO {
    private String courseID;
    private String courseName;
    private String duration;
    private String numberOfSemesters;
    private String departID;
    private Double fee;

}
