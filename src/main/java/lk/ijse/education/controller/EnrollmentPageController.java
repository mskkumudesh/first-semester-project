package lk.ijse.education.controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import lk.ijse.education.dto.EnrollmentDTO;
import lk.ijse.education.model.*;


import java.net.URL;
import java.sql.Date;
import java.sql.SQLException;
import java.util.ResourceBundle;

public class EnrollmentPageController implements Initializable {
    private final EnrollmentModel enrollmentModel = new EnrollmentModel();

    @FXML
    private ChoiceBox<String> cbCourseID;

    @FXML
    private DatePicker date;

    @FXML
    private Label lblRegisterNo;

    @FXML
    private ChoiceBox<String> cbBadgeID;

    @FXML
    private Label txtCourseName;

    @FXML
    private TextField txtStudID;

    @FXML
    void btnRegisterOnAction(ActionEvent event) {
         boolean studIDIsValid = false;
        try {
            StudentModel model = new StudentModel();
            for (var dto : model.getAll()) {
                if (dto.getStudID().equals(txtStudID.getText())) {
                    studIDIsValid = true;
                }
            }

        } catch (SQLException | ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
        if (date.getValue() == null) {
            new Alert(Alert.AlertType.ERROR, "Please fill all required fields", ButtonType.OK).show();
            return;
        }
        try {
            String regiID = lblRegisterNo.getText();
            String studID = txtStudID.getText();
            String courseID = cbCourseID.getValue();
            String courseName = txtCourseName.getText();
            String badgeID = cbBadgeID.getValue();
            Date regiDate =Date.valueOf(date.getValue());


            // Reset styles first (clear old red borders)
            txtStudID.setStyle("");
            cbBadgeID.setStyle("");
            date.setStyle("");

            boolean hasError = false;

            if (regiID == null || regiID.isBlank()) {
                hasError = true;
            }

            if (studID == null || studID.isBlank() || !studIDIsValid) {
                txtStudID.setStyle("-fx-border-color: red;");
                hasError = true;
            }

            if (badgeID == null || badgeID.isBlank()) {
                cbBadgeID.setStyle("-fx-border-color: red;");
                hasError = true;
            }

            if (date.getValue() == null) {
                date.setStyle("-fx-border-color: red;");
                hasError = true;
            }

            if (hasError) {
                new Alert(Alert.AlertType.ERROR, "Please fill all required fields.").show();
                return;
            }

            if (EnrollmentModel.isAlreadyEnrolled(studID, badgeID)) {
                new Alert(Alert.AlertType.WARNING, "Student is already enrolled for this badge.").show();
                return;
            }


            EnrollmentDTO dto = new EnrollmentDTO(regiID,courseID,courseName,badgeID,studID,regiDate);
            boolean isRegistered =enrollmentModel.saveEnrollment(dto);
            if (isRegistered) {
                new Alert(Alert.AlertType.INFORMATION, "Student registered successfully").show();
            }else {
                new Alert(Alert.AlertType.ERROR, "Student registration failed").show();
            }

        } catch (Exception e) {
            e.printStackTrace();
            new Alert(Alert.AlertType.ERROR, "Something went wrong: " + e.getMessage()).show();
        }




//            txtStudID.setStyle("-fx-text-fill: black;");
//             txtStudID.setStyle("-fx-text-fill: red;");

    }



        @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        loadCourseIDs();

        try {
            loadNextID();
        } catch (SQLException | ClassNotFoundException e) {
            throw new RuntimeException(e);
        }

        cbCourseID.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue != null) {
                setCourseNameFromCourseID(newValue);
                try {
                    loadBadgeIDs(newValue);
                } catch (SQLException | ClassNotFoundException e) {
                    throw new RuntimeException(e);
                }
            }
        });
    }

    private void loadCourseIDs() {
        try {
            CourseModel model = new CourseModel();
            for (var dto : model.getAll()) {
                cbCourseID.getItems().add(dto.getCourseID());
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void setCourseNameFromCourseID(String courseID) {
        try {
            CourseModel model = new CourseModel();
            var dto = model.searchCourse(courseID);
            if (dto != null) {
                txtCourseName.setText(dto.getCourseName());
            } else {
                txtCourseName.setText("N/A");
            }
        } catch (Exception e) {
            e.printStackTrace();
            txtCourseName.setText("Error");
        }
    }
    private void loadNextID() throws SQLException, ClassNotFoundException {
        String lastID = EnrollmentModel.loadNextID();

        if (lastID != null && lastID.matches("EN\\d{3}")) {
            int num = Integer.parseInt(lastID.substring(2)) + 1;
            lblRegisterNo.setText(String.format("EN%03d", num));
        } else {
            lblRegisterNo.setText("EN001");
        }
    }
    private void loadBadgeIDs(String courseID) throws SQLException, ClassNotFoundException {
        cbBadgeID.getItems().clear();
        BadgeModel model = new BadgeModel();
        var badgeList = BadgeModel.getBadgesByCourseID(courseID);
        for (var dto : badgeList) {
            cbBadgeID.getItems().add(dto.getBadgeID());
        }
    }
}
