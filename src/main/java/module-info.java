module lk.ijse.education.education {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.sql;
    requires static lombok;
    requires java.desktop;


    opens lk.ijse.education.controller to javafx.fxml;
    opens lk.ijse.education.dto.tm to javafx.base;
    exports lk.ijse.education;
}