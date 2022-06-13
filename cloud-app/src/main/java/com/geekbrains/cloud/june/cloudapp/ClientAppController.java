package com.geekbrains.cloud.june.cloudapp;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import java.net.URL;
import java.util.ResourceBundle;

public class ClientAppController implements Initializable {
    private Network network;
    @FXML
    ListView clientView;
    @FXML
    ListView serverView;
    @FXML
    TextField msgField;

    public void sendFile() {
        String fileName  = msgField.getText();
        network.uploadToServer(fileName);
        msgField.clear();
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        try {
            network = new Network();
        }catch (Exception e){
            System.err.println(e.getMessage());
        }
    }
}