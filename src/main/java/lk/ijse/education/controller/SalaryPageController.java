package lk.ijse.education.controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import lk.ijse.education.dto.EmployeeDTO;
import lk.ijse.education.dto.SalaryDTO;
import lk.ijse.education.model.EmployeeModel;
import lk.ijse.education.model.EnrollmentModel;
import lk.ijse.education.model.SalaryModel;

import java.net.URL;
import java.sql.Date;
import java.sql.SQLException;
import java.util.ResourceBundle;

public class SalaryPageController implements Initializable {
    private  final SalaryModel salaryModel=new SalaryModel();

    @FXML
    private ChoiceBox<String> cbEmployeeID;

    @FXML
    private ChoiceBox<String> cbMonth;

    @FXML
    private ChoiceBox<String> cbPaymentMethod;

    @FXML
    private ChoiceBox<String> cbYear;

    @FXML
    private DatePicker datePayDate;

    @FXML
    private Label lblName;

    @FXML
    private Label lblPaymentID;

    @FXML
    private ChoiceBox<String> pbEmployeeID;

    @FXML
    private ChoiceBox<String> pbMonth;

    @FXML
    private ChoiceBox<String> pbYear;

    @FXML
    private TextField txtAmount;

    @FXML
    void btnGenerateReportOnAction(ActionEvent event) {

    }

    @FXML
    void btnPayNowOnAction(ActionEvent event) throws SQLException, ClassNotFoundException {
        String payID = lblPaymentID.getText();
        String empID = cbEmployeeID.getValue();
        String date = String.valueOf(datePayDate.getValue());
        String payMonth = cbYear.getValue()+" "+cbMonth.getValue();
        String method =cbPaymentMethod.getValue();
        String amountText = txtAmount.getText();

        String year = cbYear.getValue();
        String month = cbMonth.getValue();

        if (amountText == null || amountText.isBlank()||empID == null || empID.isBlank()||year==null||date==null||date.isBlank()||month==null||month.isBlank()||method==null||method.isBlank()) {
            new Alert(Alert.AlertType.ERROR, "fill required fields!").show();
            return;
        }

        double amount;
        try {
            amount = Double.parseDouble(amountText);
        } catch (NumberFormatException e) {
            new Alert(Alert.AlertType.ERROR, "Please enter a valid number for amount.").show();
            return;
        }


        lblPaymentID.setText("");
        lblName.setText("");
        cbEmployeeID.setValue("");
        txtAmount.setText("");
        datePayDate.setValue(null);
        txtAmount.setText("");
        cbMonth.setValue("");
        cbPaymentMethod.setValue("");
        cbYear.setValue("");

        SalaryDTO dto = new SalaryDTO(payID,empID,amount,date,payMonth,method);
        boolean isPayed =salaryModel.saveSalary(dto);
        if(isPayed) {
            loadNextID();
            new Alert(Alert.AlertType.INFORMATION, "Salary payed successfully").show();
        }else {
            new Alert(Alert.AlertType.ERROR, "Salary payment unsuccessful").show();
        }
    }
    private void loadNextID() throws SQLException, ClassNotFoundException {
        String lastID = SalaryModel.loadNextID();

        if (lastID != null && lastID.matches("SAL\\d{2}")) {
            int num = Integer.parseInt(lastID.substring(3)) + 1;
            lblPaymentID.setText(String.format("SAL%02d", num));
        } else {
            lblPaymentID.setText("SAL01");
        }
    }
    private  void loadEmployeeIDs() throws SQLException, ClassNotFoundException {
        EmployeeModel model = new EmployeeModel();
        cbEmployeeID.getItems().clear();
        pbEmployeeID.getItems().clear();
        for (var dto:model.getAll()){
            cbEmployeeID.getItems().add(dto.getEmployeeID());
            pbEmployeeID.getItems().add(dto.getEmployeeID());
        }
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        try {
            loadNextID();
            loadEmployeeIDs();
            cbYear.getItems().addAll("2025","2026","2027");
            pbYear.getItems().addAll("2023","2024","2025");
            cbMonth.getItems().addAll("January","February","March","April","May","June","July","August","September","October","December");
            pbMonth.getItems().addAll("January","February","March","April","May","June","July","August","September","October","December");
            cbPaymentMethod.getItems().addAll("Cash","Cheque");
        } catch (SQLException | ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
        cbEmployeeID.getSelectionModel().selectedItemProperty().addListener(
                (observable, oldValue, newValue) -> {
                    if (newValue != null) {
                        try {
                            setLblName(newValue);
                        } catch (SQLException | ClassNotFoundException e) {
                            throw new RuntimeException(e);
                        }
                    }
                }
        );
    }
    private  void setLblName(String employeeID) throws SQLException, ClassNotFoundException {
        try {
            EmployeeModel model = new EmployeeModel();
            var dto = model.searchEmployee(employeeID);
            if (dto != null) {
                lblName.setText(dto.getEmployeeTitle() + " " + dto.getEmployeeName());
            } else {
                lblName.setText("");
            }
        }catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
