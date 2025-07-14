package lk.ijse.education.controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import lk.ijse.education.dto.CourseDTO;
import lk.ijse.education.model.CourseModel;

import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

public class DepartmentPageController {

    @FXML
    private HBox mainHBox;

    @FXML
    private Label lbl;

    @FXML
    private AnchorPane departmentAnchorPane;

    @FXML
    void handleIT(ActionEvent event) {
        loadCoursesByDepartment("D001");
    }

    @FXML
    void handleManagement(ActionEvent event) {
        loadCoursesByDepartment("D002");
    }

    @FXML
    void handleMedicine(ActionEvent event) {
        loadCoursesByDepartment("D004");
    }

    @FXML
    void handleTechnology(ActionEvent event) {
        loadCoursesByDepartment("D003");
    }

    private void loadCoursesByDepartment(String departmentId) {
        try {
            List<CourseDTO> courses = CourseModel.coursesByDepartID(departmentId);
            loadDataAsButtons(courses);
        } catch (SQLException | ClassNotFoundException | IOException e) {
            e.printStackTrace();
        }
    }

    private void loadDataAsButtons(List<CourseDTO> courses) throws IOException, SQLException, ClassNotFoundException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/view/LoadCourses.fxml"));
        AnchorPane coursePane = loader.load();

        LoadCoursesController controller = loader.getController();
        controller.setCourses(courses);

        departmentAnchorPane.getChildren().clear();
        coursePane.prefWidthProperty().bind(departmentAnchorPane.widthProperty());
        coursePane.prefHeightProperty().bind(departmentAnchorPane.heightProperty());
        departmentAnchorPane.getChildren().add(coursePane);
    }
}

