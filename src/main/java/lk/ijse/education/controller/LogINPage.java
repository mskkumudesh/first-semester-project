package lk.ijse.education.controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.control.Alert;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;

import java.io.IOException;
import java.util.Objects;

public class LogINPage {

    @FXML
    private AnchorPane ancpane;

    @FXML
    private TextField txd1;

    @FXML
    private TextField txd2;

    @FXML
    void btnSignINOnAction(ActionEvent event) throws IOException {
        String  username = txd1.getText();
        String  password = txd2.getText();

        boolean isUsernameCorrect =username.equals("admin");
        boolean isPasswordCorrect =password.equals("123");
        if (isUsernameCorrect && isPasswordCorrect) {
            try {
                ancpane.getChildren().clear();
                Parent parent = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("/view/WelcomePage.fxml")));
                ancpane.getChildren().add(parent);
            } catch (IOException | NullPointerException e) {
                e.printStackTrace();  // Print the actual error in console
            }

        }else {
            new Alert(Alert.AlertType.ERROR, "Wrong credentials").show();
            System.out.println("wrong");
        }
    }
}
