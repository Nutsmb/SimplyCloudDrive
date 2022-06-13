package com.geekbrains.cloud;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

public class server {
    private static final int PORT = 8188;

    public static void main(String[] args) throws IOException {

        ServerSocket serverSocket = new ServerSocket(PORT);
        System.out.println("Server started...");
        while (true){
            Socket socket = serverSocket.accept();
            new Thread(new Handler(socket)).start();
        }
    }
}