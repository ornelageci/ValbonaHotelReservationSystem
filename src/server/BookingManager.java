package server;

import utils.LoggerUtil;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.locks.ReentrantLock;

public class BookingManager {

    private List<Room> rooms = new ArrayList<>();
    private ReentrantLock lock = new ReentrantLock();

    public BookingManager() {

        // Standard + Jacuzzi rooms (101-110)
        for (int i = 101; i <= 110; i++) {

            if (i == 103) {
                rooms.add(new Room(i, "Jacuzzi Room", 120));
            } else {
                rooms.add(new Room(i, "Standard Room", 55));
            }
        }

        // Second floor rooms (201-210)
        for (int i = 201; i <= 210; i++) {

            if (i == 203) {
                rooms.add(new Room(i, "Jacuzzi Room", 120));
            }
            else if (i == 207 || i == 208) {
                rooms.add(new Room(i, "Duplex Room", 150));
            }
            else if (i == 210) {
                rooms.add(new Room(i, "Room With View", 90));
            }
            else {
                rooms.add(new Room(i, "Standard Room", 55));
            }
        }
    }

    // NEW ROOM TYPE MENU
    public String viewRooms() {

        return """
                
                ========= ROOM TYPES =========
                
                1. Standard Room
                Rooms: 101-110, 201-206, 209
                Price: €55/night
                Breakfast Included
                Max Capacity: 2 Guests
                
                -----------------------------
                
                2. Jacuzzi Room
                Rooms: 103, 203
                Price: €120/night
                Breakfast Included
                Max Capacity: 2 Guests
                
                -----------------------------
                
                3. Duplex Room
                Rooms: 207, 208
                Price: €150/night
                Breakfast Included
                Capacity: 2-5 Guests
                
                -----------------------------
                
                4. Room With View
                Room: 210
                Price: €90/night
                Breakfast Included
                Max Capacity: 2 Guests
                
                =============================
                """;
    }

    public String bookRoom(String customerName,
                           int roomNumber,
                           int numberOfDays,
                           int numberOfGuests) {

        lock.lock();

        try {
            for (Room room : rooms) {

                if (room.getRoomNumber() == roomNumber) {

                    if (!room.isAvailable()) {
                        return suggestAlternativeRooms(room);
                    }

                    // Guest validation
                    if (room.getType().equals("Duplex Room")) {

                        if (numberOfGuests < 2 || numberOfGuests > 5) {
                            return """
                                    
                                    Duplex rooms allow only 2 to 5 guests.
                                    """;
                        }
                    }
                    else {
                        if (numberOfGuests > 2) {
                            return """
                                    
                                    This room allows maximum 2 guests only.
                                    Please choose another room.
                                    """;
                        }
                    }

                    room.bookRoom();

                    double totalPrice =
                            room.getPrice() * numberOfDays;

                    LoggerUtil.logBooking(
                            customerName + " booked Room " + roomNumber
                    );

                    return """
                            
                            ROOM BOOKED SUCCESSFULLY
                            
                            Guest Name: %s
                            Room Number: %d
                            Room Type: %s
                            Price Per Night: €%.2f
                            Breakfast Included
                            
                            Number of Nights: %d
                            Number of Guests: %d
                            
                            TOTAL PRICE: €%.2f
                            
                            Check-In Time: 14:00 PM
                            Check-Out Time: 11:00 AM
                            
                            Enjoy your stay at Valbona River Hotel!
                            """
                            .formatted(
                                    customerName,
                                    room.getRoomNumber(),
                                    room.getType(),
                                    room.getPrice(),
                                    numberOfDays,
                                    numberOfGuests,
                                    totalPrice
                            );
                }
            }

            return "Room not found.";

        } finally {
            lock.unlock();
        }
    }

    private String suggestAlternativeRooms(Room bookedRoom) {

        StringBuilder sb = new StringBuilder();

        sb.append("\nRoom ")
                .append(bookedRoom.getRoomNumber())
                .append(" is already booked.\n");

        sb.append("\nAvailable alternatives:\n");

        for (Room room : rooms) {

            if (room.isAvailable() &&
                    room.getType().equals(bookedRoom.getType())) {

                sb.append("Room ")
                        .append(room.getRoomNumber())
                        .append(" - ")
                        .append(room.getType())
                        .append(" - €")
                        .append(room.getPrice())
                        .append("\n");
            }
        }

        return sb.toString();
    }

    public String cancelBooking(int roomNumber) {

        lock.lock();

        try {
            for (Room room : rooms) {

                if (room.getRoomNumber() == roomNumber) {

                    if (!room.isAvailable()) {
                        room.cancelBooking();

                        return """
                                
                                Booking cancelled successfully.
                                Room %d is now available again.
                                """
                                .formatted(roomNumber);
                    }
                    else {
                        return "This room was not booked.";
                    }
                }
            }

            return "Room not found.";

        } finally {
            lock.unlock();
        }
    }
}