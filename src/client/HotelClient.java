package client;

import java.io.*;
import java.net.Socket;
import java.util.Scanner;

public class HotelClient {

    public static void main(String[] args) {

        try {
            Socket socket = new Socket("localhost", 5000);

            BufferedReader input = new BufferedReader(
                    new InputStreamReader(socket.getInputStream()));

            PrintWriter output = new PrintWriter(
                    socket.getOutputStream(), true);

            Scanner scanner = new Scanner(System.in);

            while (true) {

                String serverMessage;

                // Lexon mesazhet derisa serveri pret input
                while ((serverMessage = input.readLine()) != null) {

                    System.out.println(serverMessage);

                    if (serverMessage.contains("Enter your name:")
                            || serverMessage.contains("Enter choice:")
                            || serverMessage.contains("Enter room number:")
                            || serverMessage.contains("How many nights will you stay?")
                            || serverMessage.contains("How many guests?")
                            || serverMessage.contains("Enter room number to cancel:")
                            || serverMessage.contains("Thank you")) {
                        break;
                    }
                }

                if (serverMessage == null ||
                        serverMessage.contains("Thank you")) {
                    break;
                }

                String userInput = scanner.nextLine();
                output.println(userInput);
            }

            socket.close();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}