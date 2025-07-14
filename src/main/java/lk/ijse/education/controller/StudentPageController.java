package lk.ijse.education.controller;

import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import lk.ijse.education.dto.StudentDTO;
import lk.ijse.education.dto.tm.StudentTM;
import lk.ijse.education.model.StudentModel;

import javafx.scene.input.MouseEvent;
import java.net.URL;
import java.sql.Date;
import java.sql.SQLException;
import java.util.Optional;
import java.util.ResourceBundle;

public class StudentPageController implements Initializable {
    private final StudentModel studentModel = new StudentModel();

    String NAME_PATTERN = "^[A-Za-z ]{3,50}$";
    String NIC_PATTERN = "^[0-9]{9}[vVxX]$|^[0-9]{12}$";
    String PHONE_PATTERN = "^(0\\d{9})$";


    @FXML
    private TableColumn<StudentTM,String> colAddress;

    @FXML
    private TableColumn<StudentTM,String> colDOB;

    @FXML
    private TableColumn<StudentTM,String> colID;

    @FXML
    private TableColumn<StudentTM,String> colNIC;

    @FXML
    private TableColumn<StudentTM,String> colName;

    @FXML
    private TableColumn<StudentTM,String> colTelephone;

    @FXML
    private DatePicker dateDOB;

    @FXML
    private TableView<StudentTM> tblStudents;

    @FXML
    private TextField txtAddress;

    @FXML
    private TextField txtStudID;

    @FXML
    private TextField txtNIC;

    @FXML
    private TextField txtName;

    @FXML
    private TextField txtTelephone;

    @FXML
    public void handleDelete(ActionEvent event) {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION, "Are you sure?", ButtonType.YES, ButtonType.NO);
        Optional<ButtonType> result = alert.showAndWait();

        if (result.isPresent()&&result.get() == ButtonType.YES) {
            try{
                String studID = txtStudID.getText();
                boolean isDeleted = studentModel.deleteStudent(studID);
                if(isDeleted){
                    resetPage();
                    new Alert(Alert.AlertType.INFORMATION, "Student deleted successfully").show();
                }else{
                    new Alert(Alert.AlertType.ERROR, "Fail to delete student").show();
                }
            } catch (Exception e) {
                e.printStackTrace();
                new Alert(Alert.AlertType.ERROR, "Fail to delete student").show();
            }
        }
    }

    @FXML
    void handleSave(ActionEvent event) throws SQLException, ClassNotFoundException {
        try {
            String ID = txtStudID.getText();
            String name = txtName.getText().trim();
            Date DOB = Date.valueOf(dateDOB.getValue());
            String Address = txtAddress.getText().trim();
            String NIC = txtNIC.getText().trim();
            String Telephone = txtTelephone.getText().trim();

//            String NAME_PATTERN = "^[A-Za-z ]{3,50}$";
//            String NIC_PATTERN = "^[0-9]{9}[vVxX]$|^[0-9]{12}$";
//            String PHONE_PATTERN = "^(0\\d{9})$";

            boolean isValidName = name.matches(NAME_PATTERN);
            boolean isValidNIC = NIC.matches(NIC_PATTERN);
            boolean isValidTelephone = Telephone.matches(PHONE_PATTERN);

            // Reset styles
            txtName.setStyle("-fx-text-fill: black;");
            txtNIC.setStyle("-fx-text-fill: black;");
            txtTelephone.setStyle("-fx-text-fill: black;");

            // Apply red if invalid
            if (!isValidName) txtName.setStyle("-fx-text-fill: red;");
            if (!isValidNIC) txtNIC.setStyle("-fx-text-fill: red;");
            if (!isValidTelephone) txtTelephone.setStyle("-fx-text-fill: red;");

            if (!isValidName || !isValidNIC || !isValidTelephone) {
                btnSearch.setDisable(true);
                new Alert(Alert.AlertType.ERROR, "Please correct the highlighted fields.").show();
                return;
            }

            StudentDTO studentDTO = new StudentDTO(ID, name, DOB, Address, NIC, Telephone);
            boolean isSaved = studentModel.saveStudent(studentDTO);

            if (isSaved) {
                resetPage();
                new Alert(Alert.AlertType.INFORMATION, "Student saved successfully").show();
            } else {
                new Alert(Alert.AlertType.ERROR, "Failed to save student").show();
            }

        } catch (Exception e) {
            e.printStackTrace();
            new Alert(Alert.AlertType.ERROR, "An error occurred while saving").show();
        }
    }



    @FXML
    void handleSearch(ActionEvent event) {
            String ID = txtStudID.getText();

            try {
                StudentDTO student = studentModel.searchStudent(ID);
                if (student != null) {
                    txtName.setText(student.getStudName());
                    dateDOB.setValue(student.getDOB().toLocalDate());
                    txtAddress.setText(student.getStudAddress());
                    txtNIC.setText(student.getNIC());
                    txtTelephone.setText(student.getTelephone());

                    btnSave.setDisable(true);
                    btnUpdate.setDisable(false);
                    btnDelete.setDisable(false);
                } else {
                    new Alert(Alert.AlertType.WARNING, "Student not found!").show();
                }
            } catch (Exception e) {
                e.printStackTrace();
                new Alert(Alert.AlertType.ERROR, "Error occurred while searching!").show();
            }
        }

    @FXML
    void handleUpdate(ActionEvent event) {
        try {
            String ID = txtStudID.getText();
            String name = txtName.getText().trim();
            Date DOB = Date.valueOf(dateDOB.getValue());
            String Address = txtAddress.getText().trim();
            String NIC = txtNIC.getText().trim();
            String Telephone = txtTelephone.getText().trim();

            // Validation patterns
//            String NAME_PATTERN = "^[A-Za-z ]{3,50}$";
//            String NIC_PATTERN = "^[0-9]{9}[vVxX]$|^[0-9]{12}$";
//            String PHONE_PATTERN = "^(0\\d{9})$";

            boolean isValidName = name.matches(NAME_PATTERN);
            boolean isValidNIC = NIC.matches(NIC_PATTERN);
            boolean isValidTelephone = Telephone.matches(PHONE_PATTERN);

            // Reset styles to default
            txtName.setStyle("-fx-text-fill: black;");
            txtNIC.setStyle("-fx-text-fill: black;");
            txtTelephone.setStyle("-fx-text-fill: black;");

            // Highlight invalid fields
            if (!isValidName) txtName.setStyle("-fx-text-fill: red;");
            if (!isValidNIC) txtNIC.setStyle("-fx-text-fill: red;");
            if (!isValidTelephone) txtTelephone.setStyle("-fx-text-fill: red;");

            // If any invalid input, show alert and stop update
            if (!isValidName || !isValidNIC || !isValidTelephone) {
                new Alert(Alert.AlertType.ERROR, "Please correct the highlighted fields before updating.").show();
                return;
            }

            // Create DTO and update
            StudentDTO studentDTO = new StudentDTO(ID, name, DOB, Address, NIC, Telephone);
            boolean isUpdated = studentModel.updateStudent(studentDTO);

            if (isUpdated) {
                resetPage();
                new Alert(Alert.AlertType.INFORMATION, "Student updated successfully").show();
            } else {
                new Alert(Alert.AlertType.ERROR, "Failed to update student").show();
            }

        } catch (Exception e) {
            e.printStackTrace();
            new Alert(Alert.AlertType.ERROR, "An error occurred while updating").show();
        }
    }

    @FXML public Button btnDelete;
    @FXML public Button btnSave;
    @FXML public Button btnSearch;
    @FXML public Button btnUpdate;


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        colID.setCellValueFactory(new PropertyValueFactory<>("studID"));
        colName.setCellValueFactory(new PropertyValueFactory<>("studName"));
        colDOB.setCellValueFactory(new PropertyValueFactory<>("DOB"));
        colAddress.setCellValueFactory(new PropertyValueFactory<>("studAddress"));
        colNIC.setCellValueFactory(new PropertyValueFactory<>("NIC"));
        colTelephone.setCellValueFactory(new PropertyValueFactory<>("telephone"));
        try {
            resetPage();
        } catch (Exception e) {
            new Alert(Alert.AlertType.ERROR, e.getMessage(), ButtonType.OK).show();
        }
    }

    private void resetPage() throws SQLException, ClassNotFoundException {
        loadNextID();
        loadTableData();

        btnDelete.setDisable(true);
        btnUpdate.setDisable(true);

        btnSave.setDisable(false);

        txtStudID.setText("");
        txtName.setText("");
        dateDOB.setValue(null);
        txtAddress.setText("");
        txtNIC.setText("");
        txtTelephone.setText("");
    }

    private void loadTableData() throws SQLException, ClassNotFoundException {
        tblStudents.setItems(FXCollections.observableList(
                StudentModel.getAll().stream().map(studentDTO ->
                        new StudentTM(
                                studentDTO.getStudID(),
                                studentDTO.getStudName(),
                                studentDTO.getDOB(),
                                studentDTO.getStudAddress(),
                                studentDTO.getNIC(),
                                studentDTO.getTelephone()
                        )).toList()));
    }

    private void loadNextID() throws SQLException, ClassNotFoundException {
        String lastID = studentModel.loadNextID();
        if (lastID != null) {
            int num = Integer.parseInt(lastID.substring(1)) + 1;
            txtStudID.setText(String.format("S%03d", num));
        } else {
            txtStudID.setText("S001");
        }
    }

    public void onClickTable(MouseEvent mouseEvent) {
        StudentTM selectedItem = tblStudents.getSelectionModel().getSelectedItem();

        if (selectedItem != null) {
            txtStudID.setText(selectedItem.getStudID());
            txtName.setText(selectedItem.getStudName());
            dateDOB.setValue(selectedItem.getDOB().toLocalDate());
            txtAddress.setText(selectedItem.getStudAddress());
            txtNIC.setText(selectedItem.getNIC());
            txtTelephone.setText(selectedItem.getTelephone());

            btnSave.setDisable(true);

            btnUpdate.setDisable(false);
            btnDelete.setDisable(false);
        }
    }
}

