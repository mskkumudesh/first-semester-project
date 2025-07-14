package lk.ijse.education.dto;

public class badge_module_lecturerDTO {
    private String badge_moduleID;
    private String lecturerID;

    public badge_module_lecturerDTO() {}

    public badge_module_lecturerDTO(String badge_moduleID, String lecturerID) {
        this.badge_moduleID = badge_moduleID;
        this.lecturerID = lecturerID;
    }

    public void setBadge_moduleID(String badge_moduleID) {this.badge_moduleID = badge_moduleID;}
    public String getBadge_moduleID() {return badge_moduleID;}
    public void setLecturerID(String lecturerID) {this.lecturerID = lecturerID;}
    public String getLecturerID() {return lecturerID;}

    @Override
    public String toString() {
        return "badge_moduleModel{" +
                "badge_moduleID='" + badge_moduleID + '\'' +
                ", lecturerID='" + lecturerID + '\'' +
                '}';
    }
}
