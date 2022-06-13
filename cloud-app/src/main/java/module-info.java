module com.geekbrains.cloud.june.cloudapp {
    requires javafx.controls;
    requires javafx.fxml;


    opens com.geekbrains.cloud.june.cloudapp to javafx.fxml;
    exports com.geekbrains.cloud.june.cloudapp;
}