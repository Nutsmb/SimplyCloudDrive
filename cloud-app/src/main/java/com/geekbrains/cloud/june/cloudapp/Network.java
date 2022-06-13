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
    DataInputStream inputStream;
    DataOutputStream outputStream;

    public Network() throws IOException {
        Socket socket = new Socket(IP_ADRESS, PORT);
        inputStream = new DataInputStream(socket.getInputStream());
        outputStream = new DataOutputStream(socket.getOutputStream());
    }

    public String readMsg() throws IOException {
        return inputStream.readUTF();
    }

    public void writeMsg(String msg) throws IOException {
        outputStream.writeUTF(msg);
        outputStream.flush();
    }
}
