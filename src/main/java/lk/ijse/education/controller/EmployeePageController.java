package lk.ijse.education.controller;

import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;

import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import lk.ijse.education.dto.EmployeeDTO;
import lk.ijse.education.dto.tm.EmployeeTM;
import lk.ijse.education.model.CourseModel;
import lk.ijse.education.model.DepartmentModel;
import lk.ijse.education.model.EmployeeModel;
import lk.ijse.education.model.SalaryModel;

import java.net.URL;
import java.sql.Date;
import java.sql.SQLException;
import java.util.Optional;
import java.util.ResourceBundle;

public class EmployeePageController implements Initializable {
    private final EmployeeModel employeeModel = new EmployeeModel();
    @FXML
    private Button btnDelete;

    @FXML
    private Button btnSave;

    @FXML
    private Button btnSearch;

    @FXML
    private Button btnUpdate;

    @FXML
    private ChoiceBox<String> choiceBox2;

    @FXML
    private ChoiceBox<String> choiceBoxDepartID;

    @FXML
    private ChoiceBox<String> choiceBoxRole;

    @FXML
    private TableColumn<EmployeeTM, String> col2;

    @FXML
    private TableColumn<EmployeeTM, String> colAddress;

    @FXML
    private TableColumn<EmployeeTM, String> colDOB;

    @FXML
    private TableColumn<EmployeeTM, String> colDepartID;

    @FXML
    private TableColumn<EmployeeTM, String> colID;

    @FXML
    private TableColumn<EmployeeTM, String> colName;

    @FXML
    private TableColumn<EmployeeTM, String> colRole;

    @FXML
    private TableColumn<EmployeeTM, String> colTelephone;

    @FXML
    private DatePicker dateDOB;

    @FXML
    private TableView<EmployeeTM> tblEmployee;

    @FXML
    private TextField txtAddress;

    @FXML
    private TextField txtID;

    @FXML
    private TextField txtName;

    @FXML
    private TextField txtTelephone;
    @FXML
    private Label lblLastPaid;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        choiceBox2.getItems().addAll("Mr","Mrs");
        loadDepartmentIDs();
        choiceBoxRole.getItems().addAll("Lecturer","Manager","Driver","Cleaner");

        colID.setCellValueFactory(new PropertyValueFactory<>("employeeID"));
        col2.setCellValueFactory(new PropertyValueFactory<>("employeeTitle"));
        colName.setCellValueFactory(new PropertyValueFactory<>("employeeName"));
        colDepartID.setCellValueFactory(new PropertyValueFactory<>("departmentID"));
        colRole.setCellValueFactory(new PropertyValueFactory<>("jobRoll"));
        colDOB.setCellValueFactory(new PropertyValueFactory<>("DOB"));
        colTelephone.setCellValueFactory(new PropertyValueFactory<>("telephoneNo"));
        colAddress.setCellValueFactory(new PropertyValueFactory<>("employeeAddress"));
        try {
            resetPage();
        } catch (Exception e) {
            new Alert(Alert.AlertType.ERROR, e.getMessage(), ButtonType.OK).show();
        }

    }
    public void resetPage() throws Exception {
       // loadNextID();
        loadTableData();
    }
    private void loadNextID() throws SQLException, ClassNotFoundException {
        String lastID = EmployeeModel.loadNextID();
        if (lastID != null) {
            int num = Integer.parseInt(lastID.substring(1)) + 1;
            txtID.setText(String.format("E%03d", num));
        } else {
            txtID.setText("E001");
        }
    }
    private void loadTableData() throws SQLException, ClassNotFoundException {
        tblEmployee.setItems(FXCollections.observableList(
                EmployeeModel.getAll().stream().map(employeeDTO ->new EmployeeTM(
                        employeeDTO.getEmployeeID(),
                        employeeDTO.getEmployeeTitle(),
                        employeeDTO.getEmployeeName(),
                        employeeDTO.getDepartmentID(),
                        employeeDTO.getJobRoll(),
                        employeeDTO.getDOB(),
                        employeeDTO.getTelephoneNo(),
                        employeeDTO.getEmployeeAddress()
                )).toList()));
    }
    private void loadDepartmentIDs() {
        try {
            DepartmentModel model = new DepartmentModel();
            for (var dto : model.getAll()) {
                choiceBoxDepartID.getItems().add(dto.getDepartID());
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    @FXML
    void handleDelete(ActionEvent event) {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION,"Are You Sure?", ButtonType.YES, ButtonType.NO);
        Optional<ButtonType> result = alert.showAndWait();
        if (result.isPresent()&&result.get() == ButtonType.YES) {
            try {
                String employeeID = txtID.getText();
                boolean isDeleted = employeeModel.deleteEmployee(employeeID);
                if (isDeleted) {
                    resetPage();
                    new Alert(Alert.AlertType.INFORMATION,"Employee Deleted").show();
                }else {
                    new Alert(Alert.AlertType.ERROR,"Employee Not Deleted").show();
                }  } catch (Exception e) {
                e.printStackTrace();
                new Alert(Alert.AlertType.ERROR,"Employee Not Deleted").show();
            }
        }

    }

    @FXML
    void handleSave(ActionEvent event) throws Exception {
        try {
            String id = txtID.getText();
            String title = choiceBox2.getValue();
            String name = txtName.getText();
            String departID = choiceBoxDepartID.getValue();
            String role = choiceBoxRole.getValue();
            Date DOB = Date.valueOf(dateDOB.getValue());
            String telephone = txtTelephone.getText();
            String address = txtAddress.getText();

            EmployeeDTO employeeDTO = new EmployeeDTO(id,title,name,departID,role,DOB,telephone,address);
            boolean isSaved = employeeModel.saveEmployee(employeeDTO);

            if (isSaved) {
                resetPage();
                new Alert(Alert.AlertType.INFORMATION, "Employee has been saved successfully").show();
            } else {
                new Alert(Alert.AlertType.ERROR, "Employee could not be saved").show();
            }
        } catch (Exception e) {
            e.printStackTrace();
            new Alert(Alert.AlertType.ERROR, "Employee could not be saved").show();
        }
    }

    @FXML
    void handleSearch(ActionEvent event) {
        String employeeID = txtID.getText();
        try {
            EmployeeDTO employee = employeeModel.searchEmployee(employeeID);
            if (employee != null) {
                btnSave.setDisable(true);
                btnDelete.setDisable(false);
                btnUpdate.setDisable(false);

                txtID.setText(employee.getEmployeeID());
                choiceBox2.setValue(employee.getEmployeeTitle());
                txtName.setText(employee.getEmployeeName());
                choiceBoxDepartID.setValue(employee.getDepartmentID());
                choiceBoxRole.setValue(employee.getJobRoll());
                dateDOB.setValue(employee.getDOB().toLocalDate());
                txtAddress.setText(employee.getEmployeeAddress());
                txtTelephone.setText(employee.getTelephoneNo());
                setLastPaidFromEmployeeID(employee.getEmployeeID());
            }else{
                new Alert(Alert.AlertType.WARNING, "Employee not found").show();
            }
        } catch (Exception e) {
            e.printStackTrace();
            new Alert(Alert.AlertType.ERROR, "Employee could not be found").show();
        }
    }

    @FXML
    void handleUpdate(ActionEvent event) {
        try {
            String id = txtID.getText();
            String title = choiceBox2.getValue();
            String name = txtName.getText();
            String departID = choiceBoxDepartID.getValue();
            String role = choiceBoxRole.getValue();
            Date DOB = Date.valueOf(dateDOB.getValue());
            String telephone = txtTelephone.getText();
            String address = txtAddress.getText();

            EmployeeDTO employeeDTO = new EmployeeDTO(id,title,name,departID,role,DOB,telephone,address);
            boolean isUpdated = employeeModel.updateEmployee(employeeDTO);
            if (isUpdated) {
                resetPage();
                new Alert(Alert.AlertType.INFORMATION, "Employee has been updated successfully").show();
            }else {
                new Alert(Alert.AlertType.ERROR, "Employee could not be updated").show();
            }
        }  catch (Exception e) {
            e.printStackTrace();
            new Alert(Alert.AlertType.ERROR, "Employee could not be updated").show();
        }
    }
    public void onClickTable(MouseEvent mouseEvent) {
        EmployeeTM selectedItem = tblEmployee.getSelectionModel().getSelectedItem();
        if (selectedItem != null) {
            txtID.setText(selectedItem.getEmployeeID());
            choiceBox2.setValue(selectedItem.getEmployeeTitle());
            txtName.setText(selectedItem.getEmployeeName());
            choiceBoxDepartID.setValue(selectedItem.getDepartmentID());
            choiceBoxRole.setValue(selectedItem.getJobRoll());
            dateDOB.setValue(selectedItem.getDOB().toLocalDate());
            txtTelephone.setText(selectedItem.getTelephoneNo());
            txtAddress.setText(selectedItem.getEmployeeAddress());
            setLastPaidFromEmployeeID(selectedItem.getEmployeeID());


            btnSave.setDisable(true);
            btnDelete.setDisable(false);
            btnUpdate.setDisable(false);
        }
    }
    private void setLastPaidFromEmployeeID(String employeeID) {
        try {
            SalaryModel model = new SalaryModel();
            var dto = model.getLastPaidFromEmployeeID(employeeID);
            if (dto != null) {
                lblLastPaid.setText(dto.getMonth());
            } else {
                lblLastPaid.setText("N/A");
            }
        } catch (Exception e) {
            e.printStackTrace();
            lblLastPaid.setText("Error");
        }
    }
}
