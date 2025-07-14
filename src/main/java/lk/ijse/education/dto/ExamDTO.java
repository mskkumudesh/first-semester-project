package lk.ijse.education.dto;

import java.util.Date;

public class ExamDTO {
    private String examID;
    private String badgeID;
    private String description;
    private Date date;
    private String time;

    public ExamDTO(){}

    public ExamDTO(String examID, String badgeID, String description, Date date, String time){
        this.examID = examID;
        this.badgeID = badgeID;
        this.description = description;
        this.date = date;
        this.time = time;
    }

    public String getExamID() {
        return examID;
    }

    public void setExamID(String examID) {
        this.examID = examID;
    }

    public String getBadgeID() {
        return badgeID;
    }

    public void setBadgeID(String badgeID) {
        this.badgeID = badgeID;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    @Override
    public String toString() {
        return "ExamModel{" +
                "examID='" + examID + '\'' +
                ", badgeID='" + badgeID + '\'' +
                ", description='" + description + '\'' +
                ", date=" + date +
                ", time='" + time + '\'' +
                '}';
    }
}