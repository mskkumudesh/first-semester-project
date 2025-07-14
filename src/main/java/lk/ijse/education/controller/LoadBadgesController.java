package lk.ijse.education.controller;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.VBox;
import lk.ijse.education.dto.BadgeDTO;
import lk.ijse.education.dto.ModuleDTO;
import lk.ijse.education.model.CourseModel;
import lk.ijse.education.model.ModuleModel;

import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

public class LoadBadgesController {

    @FXML
    private AnchorPane badgeAnchorPane;
    @FXML
    private VBox badgeContainer;
    @FXML
    private Label lbl;

    public void setBadge(List<BadgeDTO> badges) throws SQLException, ClassNotFoundException {
        badgeContainer.getChildren().clear();

        for (BadgeDTO badge : badges) {
            lbl.setText(CourseModel.getCourseName(badge.getCourseID()));
            final String badgeId = badge.getBadgeID();
            Button button = new Button(badgeId);
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
                    loadModulesByCourseID(badge.getCourseID(),badge.getBadgeID());


                } catch (SQLException | ClassNotFoundException | IOException ex) {
                    throw new RuntimeException(ex);
                }
            });

            badgeContainer.getChildren().add(button);
        }
    }
    public void loadDataAsButtons(List<ModuleDTO> modules,String badgeID) throws SQLException, ClassNotFoundException, IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/view/LoadModules.fxml"));
        AnchorPane modulePane =loader.load();
        LoadModulesController controller = loader.getController();
        controller.setModules(modules);
        controller.loadStudentsByBadgeID(badgeID);
        controller.setBadgeId(badgeID);
        badgeAnchorPane.getChildren().clear();
        modulePane.prefWidthProperty().bind(badgeAnchorPane.widthProperty());
        modulePane.prefHeightProperty().bind(badgeAnchorPane.heightProperty());
        badgeAnchorPane.getChildren().add(modulePane);
    }
    public void loadModulesByCourseID(String courseID,String badgeID) throws SQLException, ClassNotFoundException, IOException {
        try {
            List<ModuleDTO> modules = ModuleModel.modulesByCourseID(courseID);
            loadDataAsButtons(modules,badgeID);
        }catch (SQLException e) {
            e.printStackTrace();
        }

    }

}
