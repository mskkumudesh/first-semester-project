package lk.ijse.education.dto;

public class ModuleDTO {
    private String moduleID;
    private String moduleNo;
    private String moduleName;
    private String semNo;
    private String courseID;

    public ModuleDTO() {}

    public ModuleDTO(String moduleID, String moduleNo, String moduleName, String semNo, String courseID) {
        this.moduleID = moduleID;
        this.moduleNo = moduleNo;
        this.moduleName = moduleName;
        this.semNo = semNo;
        this.courseID = courseID;
    }

    public String getModuleID() {return moduleID;}
    public void setModuleID(String moduleID) {this.moduleID = moduleID;}
    public String getModuleNo() {return moduleNo;}
    public void setModuleNo(String moduleNo) {this.moduleNo = moduleNo;}
    public String getModuleName() {return moduleName;}
    public void setModuleName(String moduleName) {this.moduleName = moduleName;}
    public String getSemNo() {return semNo;}
    public void setSemNo(String semNo) {this.semNo = semNo;}
    public String getCourseID() {return courseID;}
    public void setCourseID(String courseID) {this.courseID = courseID;}

    @Override
    public String toString() {
        return "ModuleModel{" +
                "moduleID='" + moduleID + '\'' +
                ", moduleNo='" + moduleNo + '\'' +
                ", moduleName='" + moduleName + '\'' +
                ", semNo='" + semNo + '\'' +
                ", courseID='" + courseID + '\'' +
                '}';
    }
}
