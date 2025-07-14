package lk.ijse.education.controller;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import lk.ijse.education.dto.ModuleDTO;
import lk.ijse.education.model.EmployeeModel;
import lk.ijse.education.model.ModuleModel;

import java.net.URL;
import java.sql.SQLException;
import java.util.ResourceBundle;

public class ModuleDetailsPageController implements Initializable {

    @FXML
    private Label lbl;

    @FXML
    private Label lblLecturer;

    @FXML
    private Label lblModuleID;

    @FXML
    private Label lblModuleName;

    @FXML
    private Label lblModuleNo;

    @FXML
    private Label lblSemester;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

    }

    public void setModuleIDs(String moduleID) throws SQLException, ClassNotFoundException {
        ModuleDTO module = ModuleModel.searchModule(moduleID);
        if (module != null) {
            lblModuleID.setText(module.getModuleID());
            lblModuleName.setText(module.getModuleName());
            lblModuleNo.setText(String.valueOf(module.getModuleNo()));
            lblSemester.setText(module.getSemNo());
            lbl.setText(ModuleModel.getCourseNameByModuleID(module.getModuleID())+"-"+ModuleModel.getBadgeIDByModule(module.getModuleID()));
            String lectureID =ModuleModel.getLecturerIDByModuleID(module.getModuleID());
            lblLecturer.setText(EmployeeModel.getLecturerName(lectureID));
        } else {
            lblModuleID.setText("Not found");
        }
    }
}
