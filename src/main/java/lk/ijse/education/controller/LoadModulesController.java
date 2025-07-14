package lk.ijse.education.controller;

import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.VBox;
import lk.ijse.education.dto.ModuleDTO;
import lk.ijse.education.dto.StudentDTO;
import lk.ijse.education.dto.tm.StudentTM;
import lk.ijse.education.model.CourseModel;
import lk.ijse.education.model.StudentModel;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.List;
import java.util.ResourceBundle;

public class LoadModulesController implements Initializable {

    @FXML
    private TableColumn<StudentTM, String> colAddress;
    @FXML
    private TableColumn<StudentTM, String> colDOB;
    @FXML
    private TableColumn<StudentTM, String> colID;
    @FXML
    private TableColumn<StudentTM, String> colNIC;
    @FXML
    private TableColumn<StudentTM, String> colName;
    @FXML
    private TableColumn<StudentTM, String> colTelephone;
    @FXML
    private Label lbl;
    @FXML
    private AnchorPane moduleAnchorPane;
    @FXML
    private VBox moduleContainer;
    @FXML
    private TableView<StudentTM> tblStudents;
    @FXML
    private AnchorPane mainAnchorPane;


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        colID.setCellValueFactory(new PropertyValueFactory<>("studID"));
        colName.setCellValueFactory(new PropertyValueFactory<>("studName"));
        colDOB.setCellValueFactory(new PropertyValueFactory<>("DOB"));
        colAddress.setCellValueFactory(new PropertyValueFactory<>("studAddress"));
        colNIC.setCellValueFactory(new PropertyValueFactory<>("NIC"));
        colTelephone.setCellValueFactory(new PropertyValueFactory<>("telephone"));
    }

    public void loadStudentsByBadgeID(String badgeID) throws SQLException, ClassNotFoundException {
        List<StudentDTO> students = StudentModel.getStudentsByBadgeID(badgeID);
        List<StudentTM> tableData = students.stream().map(dto ->
                new StudentTM(
                        dto.getStudID(),
                        dto.getStudName(),
                        dto.getDOB(),
                        dto.getStudAddress(),
                        dto.getNIC(),
                        dto.getTelephone()
                )).toList();
        tblStudents.setItems(FXCollections.observableList(tableData));
    }


    public void setModules(List<ModuleDTO> modules) throws SQLException, ClassNotFoundException {
        moduleContainer.getChildren().clear();

        for (ModuleDTO module : modules) {
            lbl.setText(CourseModel.getCourseName(module.getCourseID()));
            final String name = module.getModuleName();
            Button button = new Button(name);
            button.setPrefWidth(200);
            button.setPrefHeight(60);
            button.setAlignment(Pos.CENTER);
            button.setStyle("""
                -fx-background-color: #2196F3;
                -fx-text-fill: white;
                -fx-font-weight: bold;
                -fx-cursor: hand;
                                """);

            button.setOnAction(e -> {
                try {
                    loadController(module.getModuleID());
                } catch (SQLException | ClassNotFoundException ex) {
                    throw new RuntimeException(ex);
                }
            });
            moduleContainer.getChildren().add(button);
    }

    }
    public void setBadgeId(String badgeID) throws SQLException, ClassNotFoundException {
        lbl.setText(CourseModel.getCourseID(badgeID)+"   - "+badgeID);
    }
    public void loadController(String moduleID) throws SQLException, ClassNotFoundException {
        loadModuleDetailsPage(moduleID);
    }

    public void loadModuleDetailsPage(String moduleID) throws SQLException, ClassNotFoundException {
        mainAnchorPane.getChildren().clear();
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/view/ModuleDetailsPage.fxml"));
            AnchorPane anchorPane = loader.load();
            ModuleDetailsPageController controller = loader.getController();
            controller.setModuleIDs(moduleID);

            anchorPane.prefWidthProperty().bind(mainAnchorPane.widthProperty());
            anchorPane.prefHeightProperty().bind(mainAnchorPane.heightProperty());
            mainAnchorPane.getChildren().add(anchorPane);
        } catch (IOException ex) {
            throw new RuntimeException(ex);
        }
    }
}
