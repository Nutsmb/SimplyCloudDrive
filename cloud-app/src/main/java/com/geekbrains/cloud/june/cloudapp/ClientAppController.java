package com.geekbrains.cloud.june.cloudapp;

import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

public class ClientAppController implements Initializable {
    private Network network;
    private final String homeDir = "/home/ayrat/Coding/GeekBrains/SimplyCloudDrive/cloud-app/src/main/resources/client/";
    @FXML
    ListView<String> clientView;
    @FXML
    ListView<String> serverView;
    @FXML
    TextField msgField;

    public void sendFile() {
        String fileName  = msgField.getText();
        uploadToServer(fileName);
        msgField.clear();
    }

    public void uploadToServer(String filename){
        File file = new File(homeDir + filename);
        long length = file.length();
        try{
            network.outputStream.writeUTF("#upload");
            network.outputStream.writeUTF(filename);
            network.outputStream.writeLong(length);
            Files.copy(Paths.get(homeDir + filename), network.outputStream);
            network.outputStream.flush();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        try {
            network = new Network();
            Thread readThread = new Thread(this::readLoop);
            readThread.setDaemon(true);
            readThread.start();
        }catch (Exception e){
            System.err.println(e.getMessage());
        }

        String[] ClientFilesList = getFilesList(homeDir);
        showListOfFiles(ClientFilesList, clientView);
//        Platform.runLater(new Runnable() {
//            @Override
//            public void run() {
//                clientView.getItems().clear();
//                for (int i = 0; i < ClientFilesList.length; i++) {
//                    clientView.getItems().add(ClientFilesList[i]);
//                }
//            }
//        });
        try {
            network.writeMsg("#getServerFilesList");
        } catch (IOException e) {
            System.err.println("Can't download server files list");
        }
    }
    public String[] getFilesList(String catalogName) {
        File catalog = new File(catalogName);
        String[] list = new String[0];
        if (catalog.isDirectory()) {
            list = catalog.list();
            /*if (list != null) {
                for (String fileName : list) {
                    File file = new File(catalogName + "/" + fileName);
                    if (file.isDirectory()) {
                        System.out.printf("\t%s каталог%n", fileName);
                    } else {
                        System.out.printf("\t%s файл%n", fileName);
                    }
                }*/
        }
        return list;
    }

    private void readLoop(){
        try{
            while(true){
                String msg = network.readMsg();
                if(msg.equals("#ServerFilesList")){
                    int serverfiles = network.inputStream.readInt();
                    String[] ServerFilesList = new String[serverfiles];
                    for(int i = 0; i < serverfiles; i++) {
                        ServerFilesList[i] = network.readMsg();
                    }
                    showListOfFiles(ServerFilesList, serverView);
//                    Platform.runLater(new Runnable() {
//                        @Override
//                        public void run() {
//                            serverView.getItems().clear();
//                            for (int i = 0; i < ServerFilesList.length; i++) {
//                                clientView.getItems().add(ServerFilesList[i]);
//                            }
//                        }
//                    });
                }
            }
        }catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void showListOfFiles(String[] filesNamesList, ListView<String> View){
        Platform.runLater(() -> {
            View.getItems().clear();
            for (String s : filesNamesList) {
                View.getItems().add(s);
            }
        });
    }
}