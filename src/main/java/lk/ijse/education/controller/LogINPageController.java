package lk.ijse.education.controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import javafx.stage.Window;

import java.io.IOException;

public class LogINPageController {

    @FXML
    private AnchorPane ancPage;

    @FXML
    private TextField txtPassword;

    @FXML
    private TextField txtUsername;

    @FXML
    void btnSignInOnAction(ActionEvent event) throws IOException {
        String userName = "admin";
        String password = "1234";

        String inputUserName = txtUsername.getText();
        String inputPassword = txtPassword.getText();

        boolean isUsernameMatch = userName.equals(inputUserName);
        boolean isPasswordMatch = password.equals(inputPassword);

        if (isUsernameMatch && isPasswordMatch) {
            ancPage.getChildren().clear();
            Stage window = (Stage)ancPage.getScene().getWindow();
            window.close();

            Parent parent = FXMLLoader.load(getClass().getResource("/view/DashboardPage.fxml"));

            Stage stage = new Stage();
            stage.setTitle("Dashboard");
            stage.setScene(new Scene(parent));
        stage.show();
           // ancPage.getChildren().add(parent);
        } else {
            new Alert(Alert.AlertType.ERROR, "Wrong credentials").show();
            System.out.println("wrong");
        }
    }
}