package utils;

import java.io.FileWriter;
import java.io.IOException;

public class LoggerUtil {

    public static synchronized void logBooking(String message) {
        try {
            FileWriter writer = new FileWriter("bookings.txt", true);
            writer.write(message + "\n");
            writer.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}