package com.asharia.cybercafe;

import com.asharia.cybercafe.entity.Computer;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.*;
import java.net.InetAddress;
import java.net.Socket;
import java.net.SocketException;
import java.nio.charset.StandardCharsets;

public class ComputerFunctions extends Server implements Runnable {

    private final Socket clientSocket;
    private boolean duplicateFound = false;

    public ComputerFunctions(Socket socket) {
        this.clientSocket = socket;
    }

    @Override
    public void run() {
        try {
            DataInputStream dis = new DataInputStream(new BufferedInputStream(clientSocket.getInputStream()));
            ObjectMapper objectMapper = new ObjectMapper();
            Computer computer = objectMapper.readValue((InputStream) dis, Computer.class);
            // In this way, there will be no duplicate in Server.computers
            Server.computers.forEach(pc -> {
                Server.computers.forEach(pc2 -> {
                    if (pc.getComputerName().equals(pc2.getComputerName())) {
                        duplicateFound = true;
                    }
                });
            });

            if (!duplicateFound) {
                // If nothing is found, add the computer to the list.
                Server.computers.add(computer);
            } else {
                // Do not add the computer since it already exist.
                duplicateFound = false;
            }

            // Send confirmation that the computer is correctly sent its data information to the server.
            sendConfirmation(computer.getComputerLocalIP());
        } catch (SocketException e) {
            System.out.println(e.getMessage());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void sendConfirmation(String computerLocalIP) {
        try {
            InetAddress address = InetAddress.getByName(computerLocalIP);
            Socket socket = new Socket(address, 1234);
            DataOutputStream dos = new DataOutputStream(socket.getOutputStream());

            dos.write("accept".getBytes(StandardCharsets.UTF_8));
            dos.flush();

            dos.close();
            socket.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

        Dashboard.refreshComputerTable();
    }
}
