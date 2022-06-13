package com.geekbrains.cloud.june.cloudapp;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.File;
import java.io.IOException;
import java.net.Socket;
import java.nio.file.Files;
import java.nio.file.Paths;

public class Network {
    final String IP_ADRESS = "localhost";
    final int PORT = 8188;
    private DataInputStream inputStream;
    private DataOutputStream outputStream;

    public Network() throws IOException {
        Socket socket = new Socket(IP_ADRESS, PORT);
        inputStream = new DataInputStream(socket.getInputStream());
        outputStream = new DataOutputStream(socket.getOutputStream());
    }
    public void uploadToServer(String filename){
        String homeDir = "/home/ayrat/Coding/GeekBrains/SimplyCloudDrive/cloud-app/src/main/resources/client/";
        File file = new File(homeDir + filename);
        long length = file.length();
        try{
            outputStream.writeUTF("#upload");
            outputStream.writeUTF(filename);
            outputStream.writeLong(length);
            Files.copy(Paths.get(homeDir + filename), outputStream);
            outputStream.flush();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
