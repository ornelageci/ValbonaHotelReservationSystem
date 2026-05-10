package server;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class HotelServer {

    public static void main(String[] args) {

        int port = 5000;

        BookingManager bookingManager = new BookingManager();

        ExecutorService threadPool =
                Executors.newFixedThreadPool(10);

        try {
            ServerSocket serverSocket =
                    new ServerSocket(port);

            System.out.println("Valbona Hotel Server is running on port " + port);

            while (true) {
                Socket clientSocket = serverSocket.accept();

                System.out.println("New client connected: "
                        + clientSocket.getInetAddress());

                ClientHandler handler =
                        new ClientHandler(clientSocket, bookingManager);

                threadPool.execute(handler);
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}