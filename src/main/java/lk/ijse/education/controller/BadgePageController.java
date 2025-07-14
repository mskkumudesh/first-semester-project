package lk.ijse.education.controller;

import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import lk.ijse.education.dto.BadgeDTO;
import lk.ijse.education.dto.tm.BadgeTM;
import lk.ijse.education.model.BadgeModel;
import lk.ijse.education.model.CourseModel;

import java.net.URL;
import java.sql.SQLException;
import java.util.Optional;
import java.util.ResourceBundle;

public class BadgePageController implements Initializable {
    private final BadgeModel badgeModel = new BadgeModel();

    @FXML
    private Button btnDelete;

    @FXML
    private Button btnSave;

    @FXML
    private Button btnSearch;

    @FXML
    private Button btnUpdate;

    @FXML
    private ChoiceBox<String> cbCourseID;

    @FXML
    private TableColumn<BadgeTM, String> colBadgeID;

    @FXML
    private TableColumn<BadgeTM, String> colBadgeNo;

    @FXML
    private TableColumn<BadgeTM, String> colCourseID;

    @FXML
    private TableColumn<BadgeTM, String> colName;

    @FXML
    private Label lblCourseName;

    @FXML
    private TableView<BadgeTM> tblBadges;

    @FXML
    private TextField txtBadgeID;

    @FXML
    private TextField txtBadgeNo;

    @FXML
    void handleDelete(ActionEvent event) {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION, "Are you sure?", ButtonType.YES, ButtonType.NO);
        Optional<ButtonType> result = alert.showAndWait();

        if (result.isPresent()&&result.get() == ButtonType.YES) {
            try{
                String badgeID = txtBadgeID.getText();
                boolean isDeleted = BadgeModel.delete(badgeID);
                if(isDeleted){
                    resetPage();
                    new Alert(Alert.AlertType.INFORMATION, "badge deleted successfully").show();
                }else{
                    new Alert(Alert.AlertType.ERROR, "Fail to delete badge").show();
                }
            } catch (Exception e) {
                e.printStackTrace();
                new Alert(Alert.AlertType.ERROR, "Fail to delete badge").show();
            }
        }
    }

    @FXML
    void handleSave(ActionEvent event) throws SQLException, ClassNotFoundException {
        String courseID = cbCourseID.getValue();
        String courseName = lblCourseName.getText();
        String badgeID = txtBadgeID.getText();
        String badgeNo = txtBadgeNo.getText();

        BadgeDTO badgeDTO = new BadgeDTO(courseID, courseName, badgeID, badgeNo);
        boolean isSaved = badgeModel.save(badgeDTO);
        if(isSaved){
            resetPage();
            new Alert(Alert.AlertType.INFORMATION, "badge saved successfully").show();
        }else {
            new Alert(Alert.AlertType.ERROR, "Fail to save badge").show();
        }
    }

    @FXML
    void handleSearch(ActionEvent event) throws SQLException, ClassNotFoundException {
        String badgeID = txtBadgeID.getText();
        BadgeDTO badge =BadgeModel.search(badgeID);
        if(badge != null){
            cbCourseID.setValue(badge.getCourseID());
            lblCourseName.setText(badge.getCourseName());
            txtBadgeID.setText(badge.getBadgeID());
            txtBadgeNo.setText(badge.getBadgeNo());
        }

    }

    @FXML
    void handleUpdate(ActionEvent event) throws SQLException, ClassNotFoundException {
        String courseID = cbCourseID.getValue();
        String courseName = lblCourseName.getText();
        String badgeID = txtBadgeID.getText();
        String badgeNo = txtBadgeNo.getText();

        BadgeDTO badgeDTO = new BadgeDTO(courseID, courseName, badgeID, badgeNo);
        boolean isUpdated =BadgeModel.update(badgeDTO);
        if(isUpdated){
            resetPage();
            new Alert(Alert.AlertType.INFORMATION, "badge updated successfully").show();
        }else{
            new Alert(Alert.AlertType.ERROR, "Fail to update badge").show();
        }

    }

    @FXML
    void onClickTable(MouseEvent event) {
        BadgeTM selectedItem = tblBadges.getSelectionModel().getSelectedItem();
        if (selectedItem != null) {
            cbCourseID.setValue(selectedItem.getCourseID());
            lblCourseName.setText(selectedItem.getCourseName());
            txtBadgeID.setText(selectedItem.getBadgeID());
            txtBadgeNo.setText(selectedItem.getBadgeNo());

            btnSave.setDisable(true);
            btnUpdate.setDisable(false);
            btnDelete.setDisable(false);
        }
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        try {
            loadTableData();
            loadCourseIDs();
        } catch (SQLException | ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
        colCourseID.setCellValueFactory(new PropertyValueFactory<>("courseID"));
        colName.setCellValueFactory(new PropertyValueFactory<>("courseName"));
        colBadgeID.setCellValueFactory(new PropertyValueFactory<>("badgeID"));
        colBadgeNo.setCellValueFactory(new PropertyValueFactory<>("badgeNo"));
        try {
            resetPage();
        } catch (Exception e) {
            e.printStackTrace();
            new Alert(Alert.AlertType.ERROR, e.getMessage(), ButtonType.OK).show();
        }
        cbCourseID.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
            setCourseNameFromCourseID(newValue);
        });
    }

    private void resetPage() throws SQLException, ClassNotFoundException {
        btnDelete.setDisable(true);
        btnUpdate.setDisable(true);
        btnSave.setDisable(false);
        cbCourseID.setValue(null);
        lblCourseName.setText("");
        txtBadgeID.setText("");
        txtBadgeNo.setText("");
        loadCourseIDs();
        loadTableData();
    }

     private void loadNextID() { }
    private void loadTableData() throws SQLException, ClassNotFoundException {
          tblBadges.setItems(FXCollections.observableList(
                  BadgeModel.getAll().stream().map(badgeDTO ->
                          new BadgeTM(
                                  badgeDTO.getCourseID(),
                                  badgeDTO.getCourseName(),
                                  badgeDTO.getBadgeID(),
                                  badgeDTO.getBadgeNo()

                          )).toList()));
      }
      private void loadCourseIDs() throws SQLException, ClassNotFoundException {
          CourseModel courseModel = new CourseModel();
          cbCourseID.getItems().clear();
          for (var dto : courseModel.getAll()) {
              cbCourseID.getItems().add(dto.getCourseID());
          }
      }
    private void setCourseNameFromCourseID(String courseID) {
        try {
            CourseModel model = new CourseModel();
            var dto = model.searchCourse(courseID);
            if (dto != null) {
                lblCourseName.setText(dto.getCourseName());
            } else {
                lblCourseName.setText("N/A");
            }
        } catch (Exception e) {
            e.printStackTrace();
            lblCourseName.setText("Error");
        }
    }
}
