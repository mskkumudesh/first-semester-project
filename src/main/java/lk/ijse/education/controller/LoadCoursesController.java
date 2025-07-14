package lk.ijse.education.controller;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.VBox;
import lk.ijse.education.dto.BadgeDTO;
import lk.ijse.education.dto.CourseDTO;
import lk.ijse.education.model.BadgeModel;
import lk.ijse.education.model.CourseModel;
import lk.ijse.education.model.DepartmentModel;

import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

public class LoadCoursesController {

    @FXML
    private AnchorPane courseAnchorPane;
    @FXML
    private VBox courseContainer;
    @FXML
    private Label lbl;

    public void setCourses(List<CourseDTO> courses) throws SQLException, ClassNotFoundException {
        courseContainer.getChildren().clear();


        for (CourseDTO course : courses) {
            lbl.setText(DepartmentModel.getDepartName(course.getDepartID())+" Department");
            Button button = new Button(course.getCourseName());
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
                    loadBadgesByCourse(course.getCourseID());
                } catch (IOException ex) {
                    throw new RuntimeException(ex);
                }
            });
            courseContainer.getChildren().add(button);
        }
    }
    private void loadBadgesByCourse(String courseID) throws IOException {
        try {
            List<BadgeDTO> badges = BadgeModel.getBadgesByCourseID(courseID);
            loadDataAsButtons(badges);
            lbl.setText(CourseModel.getCourseName(courseID)+" Course");

        } catch (SQLException | ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    private void loadDataAsButtons(List<BadgeDTO> badges) throws IOException, SQLException, ClassNotFoundException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/view/LoadBadges.fxml"));
        AnchorPane pane = loader.load();
        LoadBadgesController controller = loader.getController();
        controller.setBadge(badges);
        courseAnchorPane.getChildren().clear();


//        AnchorPane.setTopAnchor(pane, 0.0);
//        AnchorPane.setBottomAnchor(pane, 0.0);
//        AnchorPane.setLeftAnchor(pane, 0.0);
//        AnchorPane.setRightAnchor(pane, 0.0);
        pane.prefWidthProperty().bind(courseAnchorPane.widthProperty());
        pane.prefHeightProperty().bind(courseAnchorPane.heightProperty());

        courseAnchorPane.getChildren().add(pane);
    }
}
