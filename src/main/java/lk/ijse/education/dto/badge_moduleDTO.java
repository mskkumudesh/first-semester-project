package lk.ijse.education.dto;

public class badge_moduleDTO {
    private String badge_moduleID;
    private String badgeID;
    private String moduleID;

    public badge_moduleDTO() {}

    public badge_moduleDTO(String badge_moduleID, String badgeID, String moduleID) {
        this.badge_moduleID = badge_moduleID;
        this.badgeID = badgeID;
        this.moduleID = moduleID;
    }

    public void setBadge_moduleID(String badge_moduleID) {this.badge_moduleID = badge_moduleID;}
    public String getBadge_moduleID() {return badge_moduleID;}
    public void setBadgeID(String badgeID) {this.badgeID = badgeID;}
    public String getBadgeID() {return badgeID;}
    public void setModuleID(String moduleID) {this.moduleID = moduleID;}
    public String getModuleID() {return moduleID;}

    @Override
    public String toString() {
        return "badge_moduleModel{" +
                "badge_moduleID='" + badge_moduleID + '\'' +
                ", badgeID='" + badgeID + '\'' +
                ", moduleID='" + moduleID + '\'' +
                '}';
    }
}