package com.geekbrains.cloud;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.Socket;

public class Handler implements Runnable{

    private byte[] buffer;
    private final int BUFFER_SIZE = 1024;
    private DataInputStream inputStream;
    private DataOutputStream outputStream;
    String directory = "/home/ayrat/Coding/GeekBrains/SimplyCloudDrive/cloud-server/src/main/resources/server/";

    public Handler(Socket socket) throws IOException {
        buffer = new byte[BUFFER_SIZE];
        inputStream = new DataInputStream(socket.getInputStream());
        outputStream = new DataOutputStream(socket.getOutputStream());
        System.out.println("Client accepted...");

    }
    @Override
    public void run() {
        try {
            while(true){
                String command = inputStream.readUTF();
                if(command.equals("#upload")){
                    String filename = inputStream.readUTF();
                    long size = inputStream.readLong();
                    try(FileOutputStream fos = new FileOutputStream(directory + filename)){
                        for(int i = 0; i < (size + (BUFFER_SIZE - 1))/BUFFER_SIZE; i++){
                            int read = inputStream.read(buffer);
                            fos.write(buffer, 0, read);
                        }
                    }
                    outputStream.writeUTF("File uploaded successfully!");
                }
            }
        }
        catch (IOException e) {
            System.err.println("Client connection exception");
        }
        finally {
            try {
                inputStream.close();
                outputStream.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
