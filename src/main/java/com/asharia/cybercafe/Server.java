package com.asharia.cybercafe;


import com.asharia.cybercafe.entity.Computer;

import javax.swing.table.DefaultTableModel;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.UnknownHostException;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class Server {

    public static List<Computer> computers = new ArrayList<Computer>();
    private int port = 4444;
    private int clientPort = 4646;

    public void startServer() {
        final ExecutorService executor = Executors.newFixedThreadPool(10);

        Runnable serverTask = new Runnable() {
            @Override
            public void run() {
                try {
                    ServerSocket serverSocket = new ServerSocket(port);
                    System.out.println("Listening for Client");
                    while (true) {
                        Socket clientSocket = serverSocket.accept();
                        System.out.println("Computer found;");
                        executor.submit(new ComputerFunctions(clientSocket));
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        };

        Thread serverThread = new Thread(serverTask);
        serverThread.start();
    }

    public void sendPowerSignal(String ipv4, String signalName) {
        try {
            InetAddress targetComputer = InetAddress.getByName(ipv4);
            Socket socket = new Socket(targetComputer, clientPort);
            DataOutputStream dos = new DataOutputStream(socket.getOutputStream());

            // Power Options.
            switch (signalName) {
                case "shutdown":
                    dos.write("power shutdown".getBytes(StandardCharsets.UTF_8));
                    dos.flush();
                    break;
                case "reboot":
                    dos.write("power reboot".getBytes(StandardCharsets.UTF_8));
                    dos.flush();
                    break;
                case "sleep":
                    dos.write("power sleep".getBytes(StandardCharsets.UTF_8));
                    dos.flush();
                    break;
            }
            socket.close();
            dos.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
