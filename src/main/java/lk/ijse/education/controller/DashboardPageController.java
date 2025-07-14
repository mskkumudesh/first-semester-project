package lk.ijse.education.controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.layout.AnchorPane;

import java.net.URL;
import java.util.ResourceBundle;

public class DashboardPageController implements Initializable {

    @FXML
    private AnchorPane ancMainContainer;
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        System.out.println("Initializing DashboardPageController");
        navigateTo("/view/DepartmentPage.fxml");
    }

    @FXML
    void btnCourseOnAction(ActionEvent event) {
        navigateTo("/view/CoursePage.fxml");

    }

    @FXML
    void btnEmployeeOnAction(ActionEvent event) {
        navigateTo("/view/EmployeePage.fxml");
    }

    @FXML
    void btnStudentOnAction(ActionEvent event) {
        navigateTo("/view/StudentPage.fxml");
    }




    private void navigateTo(String path){
        try{
            ancMainContainer.getChildren().clear();
            AnchorPane anchorpane = FXMLLoader.load(getClass().getResource(path));
            anchorpane.prefWidthProperty().bind(ancMainContainer.widthProperty());
            anchorpane.prefHeightProperty().bind(ancMainContainer.heightProperty());
            ancMainContainer.getChildren().add(anchorpane);
        }catch (Exception e){
            new Alert(Alert.AlertType.ERROR, "Something went wrong", ButtonType.OK).show();
            e.printStackTrace();
        }
    }

    public void btnDepartmentOnAction(ActionEvent actionEvent) {
        navigateTo("/view/DepartmentPage.fxml");
    }

    public void btnEnrollOnAction(ActionEvent actionEvent) {
        navigateTo("/view/EnrollmentPage.fxml");
    }

    public void btnBadgeOnAction(ActionEvent actionEvent) {
        navigateTo("/view/BadgePage.fxml");
    }

    public void btnSalaryOnAction(ActionEvent actionEvent) {
        navigateTo("/view/SalaryPage.fxml");
    }

    public void btnPaymentOnAction(ActionEvent actionEvent) {
        navigateTo("/view/PaymentPage.fxml");
    }

    public void btnEventOnAction(ActionEvent actionEvent) {
        navigateTo("/view/EventPage.fxml");
    }
}
