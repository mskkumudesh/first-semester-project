package lk.ijse.education.controller;

import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import lk.ijse.education.dto.CourseDTO;
import lk.ijse.education.dto.tm.CourseTM;
import lk.ijse.education.model.CourseModel;
import lk.ijse.education.model.DepartmentModel;

import java.net.URL;
import java.sql.SQLException;
import java.util.Optional;
import java.util.ResourceBundle;

public class CoursePageController implements Initializable {
    private final CourseModel courseModel = new CourseModel();

    @FXML
    private TableView<CourseTM> tblCourses;
    @FXML
    private TableColumn<CourseTM, String> colID;
    @FXML
    private TableColumn<CourseTM, String> colName;
    @FXML
    private TableColumn<CourseTM, String> colDuration;
    @FXML
    private TableColumn<CourseTM, String> colFee;
    @FXML
    private TableColumn<CourseTM, String> colSemesters;
    @FXML
    private TableColumn<CourseTM, String> colDepartID;

    @FXML
    private  TextField txtCourseID;
    @FXML
    private  TextField txtName;
    @FXML
    private  TextField txtDuration;
    @FXML
    private  TextField txtFee;
    @FXML
    private  TextField txtSemesters;
    @FXML
    private  ChoiceBox<String> cbDepartID;

    @FXML
    private  Button btnDelete;
    @FXML
    private  Button btnSave;
    @FXML
    private  Button btnSearch;
    @FXML
    private  Button btnUpdate;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        loadDepartmentIDs();
        colID.setCellValueFactory(new PropertyValueFactory<>("courseID"));
        colName.setCellValueFactory(new PropertyValueFactory<>("courseName"));
        colDuration.setCellValueFactory(new PropertyValueFactory<>("Duration"));
        colFee.setCellValueFactory(new PropertyValueFactory<>("Fee"));
        colSemesters.setCellValueFactory(new PropertyValueFactory<>("numberOfSemesters"));
        colDepartID.setCellValueFactory(new PropertyValueFactory<>("departID"));

        try {
            resetPage();
        } catch (Exception e) {
            new Alert(Alert.AlertType.ERROR, e.getMessage(), ButtonType.OK).show();
        }
    }

    @FXML
    void handleSave(ActionEvent event) {
        try {
            String id = txtCourseID.getText();
            String name = txtName.getText();
            String duration = txtDuration.getText();
            String fee = txtFee.getText();
            String semesters = txtSemesters.getText();
            String deptID = cbDepartID.getValue();

            if (!validateInputs(name, duration, fee, semesters)) return;

            CourseDTO course = new CourseDTO(id, name, duration, semesters, deptID, Double.valueOf(fee));
            boolean saved = courseModel.saveCourse(course);

            if (saved) {
                resetPage();
                new Alert(Alert.AlertType.INFORMATION, "Course saved successfully").show();
            } else {
                new Alert(Alert.AlertType.ERROR, "Failed to save course").show();
            }
        } catch (Exception e) {
            e.printStackTrace();
            new Alert(Alert.AlertType.ERROR, "Error saving course").show();
        }
    }

    @FXML
    void handleUpdate(ActionEvent event) {
        try {
            String id = txtCourseID.getText();
            String name = txtName.getText().trim();
            String duration = txtDuration.getText().trim();
            String fee = txtFee.getText().trim();
            String semesters = txtSemesters.getText().trim();
            String deptID = cbDepartID.getValue().trim();

            if (!validateInputs(name, duration, fee, semesters)) return;

            CourseDTO course = new CourseDTO(id, name, duration, semesters, deptID, Double.valueOf(fee));
            boolean updated = courseModel.updateCourse(course);

            if (updated) {
                resetPage();
                new Alert(Alert.AlertType.INFORMATION, "Course updated successfully").show();
            } else {
                new Alert(Alert.AlertType.ERROR, "Failed to update course").show();
            }
        } catch (Exception e) {
            e.printStackTrace();
            new Alert(Alert.AlertType.ERROR, "Error updating course").show();
        }
    }

    @FXML
    void handleDelete(ActionEvent event) {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION, "Are you sure?", ButtonType.YES, ButtonType.NO);
        Optional<ButtonType> result = alert.showAndWait();

        if (result.isPresent() && result.get() == ButtonType.YES) {
            try {
                String id = txtCourseID.getText();
                boolean deleted = courseModel.deleteCourse(id);
                if (deleted) {
                    resetPage();
                    new Alert(Alert.AlertType.INFORMATION, "Course deleted successfully").show();
                } else {
                    new Alert(Alert.AlertType.ERROR, "Failed to delete course").show();
                }
            } catch (Exception e) {
                e.printStackTrace();
                new Alert(Alert.AlertType.ERROR, "Error deleting course").show();
            }
        }
    }

    @FXML
    void handleSearch(ActionEvent event) {
        try {
            String id = txtCourseID.getText();
            CourseDTO course = courseModel.searchCourse(id);
            if (course != null) {
                txtName.setText(course.getCourseName());
                txtDuration.setText(course.getDuration());
                txtFee.setText(String.valueOf(course.getFee()));
                txtSemesters.setText(String.valueOf(course.getNumberOfSemesters()));
                cbDepartID.setValue(course.getDepartID());

                btnSave.setDisable(true);
                btnUpdate.setDisable(false);
                btnDelete.setDisable(false);
            } else {
                new Alert(Alert.AlertType.INFORMATION, "Course not found").show();
            }
        } catch (Exception e) {
            e.printStackTrace();
            new Alert(Alert.AlertType.ERROR, "Error searching course").show();
        }
    }

    private void resetPage() throws SQLException, ClassNotFoundException {
        loadNextID();
        loadTableData();

        btnSave.setDisable(false);
        btnUpdate.setDisable(true);
        btnDelete.setDisable(true);

        txtCourseID.clear();
        txtName.clear();
        txtDuration.clear();
        txtFee.clear();
        txtSemesters.clear();
        cbDepartID.setValue(null);
    }

    private void loadTableData() throws SQLException, ClassNotFoundException {
        tblCourses.setItems(FXCollections.observableList(
                CourseModel.getAll().stream().map(c ->
                        new CourseTM(
                                c.getCourseID(),
                                c.getCourseName(),
                                c.getDuration(),
                                c.getNumberOfSemesters(),
                                c.getDepartID(),
                                c.getFee()
                        )).toList()));
    }

    private void loadNextID() throws SQLException, ClassNotFoundException {
        String lastID = courseModel.loadNextID();
        if (lastID != null) {
            int num = Integer.parseInt(lastID.substring(1)) + 1;
            txtCourseID.setText(String.format("C%03d", num));
        } else {
            txtCourseID.setText("C001");
        }
    }

    private boolean validateInputs(String name, String duration, String fee, String semesters) {
        String NAME_PATTERN = "^[A-Za-z0-9 ]{3,50}$";
        boolean isValidName = name.matches(NAME_PATTERN);
        boolean isValidFee = fee.matches("^\\d+(\\.\\d{1,2})?$");
        boolean isValidSemesters = semesters.matches("^\\d+$");

        txtName.setStyle("-fx-text-fill: black;");
        txtFee.setStyle("-fx-text-fill: black;");
        txtSemesters.setStyle("-fx-text-fill: black;");

        if (!isValidName) txtName.setStyle("-fx-text-fill: red;");
        if (!isValidFee) txtFee.setStyle("-fx-text-fill: red;");
        if (!isValidSemesters) txtSemesters.setStyle("-fx-text-fill: red;");

        if (!isValidName || !isValidFee || !isValidSemesters) {
            new Alert(Alert.AlertType.ERROR, "Please correct the highlighted fields.").show();
            return false;
        }

        return true;
    }

    @FXML
    public void onClickTable(MouseEvent mouseEvent) {
        CourseTM selected = tblCourses.getSelectionModel().getSelectedItem();

        if (selected != null) {
            txtCourseID.setText(selected.getCourseID());
            txtName.setText(selected.getCourseName());
            txtDuration.setText(selected.getDuration());
            txtFee.setText(String.valueOf(selected.getFee()));
            txtSemesters.setText(String.valueOf(selected.getNumberOfSemesters()));
            cbDepartID.setValue(selected.getDepartID());

            btnSave.setDisable(true);
            btnUpdate.setDisable(false);
            btnDelete.setDisable(false);
        }
    }
    private void loadDepartmentIDs() {
        try {
            DepartmentModel model = new DepartmentModel();
            cbDepartID.getItems().clear();

            for (var dto : model.getAll()) {
                cbDepartID.getItems().add(dto.getDepartID());
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}

