package server;

import java.io.*;
import java.net.Socket;

public class ClientHandler implements Runnable {

    private Socket socket;
    private BookingManager bookingManager;

    public ClientHandler(Socket socket,
                         BookingManager bookingManager) {
        this.socket = socket;
        this.bookingManager = bookingManager;
    }

    @Override
    public void run() {

        try {
            BufferedReader input =
                    new BufferedReader(
                            new InputStreamReader(
                                    socket.getInputStream()));

            PrintWriter output =
                    new PrintWriter(
                            socket.getOutputStream(), true);

            output.println("Welcome to Valbona River Hotel Reservation System");
            output.println("Enter your name:");

            String customerName = input.readLine();

            while (true) {

                output.println("--------------------------");
                output.println("1. Type of Rooms");
                output.println("2. Book Room");
                output.println("3. Cancel Booking");
                output.println("4. Exit");
                output.println("--------------------------");
                output.println("Enter choice:");

                String choice = input.readLine();

                if (choice == null) {
                    break;
                }

                switch (choice) {

                    case "1":
                        output.println(
                                bookingManager.viewRooms()
                        );
                        break;

                    case "2":

                        output.println("Enter room number:");
                        int roomNumber =
                                Integer.parseInt(input.readLine());

                        output.println("How many nights will you stay?");
                        int nights =
                                Integer.parseInt(input.readLine());

                        output.println("How many guests?");
                        int guests =
                                Integer.parseInt(input.readLine());

                        output.println(
                                bookingManager.bookRoom(
                                        customerName,
                                        roomNumber,
                                        nights,
                                        guests
                                )
                        );
                        break;

                    case "3":

                        output.println("Enter room number to cancel:");
                        int cancelRoom =
                                Integer.parseInt(input.readLine());

                        output.println(
                                bookingManager.cancelBooking(cancelRoom)
                        );
                        break;

                    case "4":
                        output.println(
                                "Thank you for choosing Valbona River Hotel!"
                        );
                        socket.close();
                        return;

                    default:
                        output.println("Invalid option.");
                }
            }

        } catch (Exception e) {
            System.out.println("Client disconnected.");
        }
    }
}