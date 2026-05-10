package server;

public class Room {
    private int roomNumber;
    private String type;
    private double price;
    private boolean available;

    public Room(int roomNumber, String type, double price) {
        this.roomNumber = roomNumber;
        this.type = type;
        this.price = price;
        this.available = true;
    }

    public int getRoomNumber() {
        return roomNumber;
    }

    public String getType() {
        return type;
    }

    public double getPrice() {
        return price;
    }

    public boolean isAvailable() {
        return available;
    }

    public void bookRoom() {
        available = false;
    }

    public void cancelBooking() {
        available = true;
    }

    @Override
    public String toString() {
        return "Room " + roomNumber +
                " | Type: " + type +
                " | Price: €" + price +
                " | " + (available ? "Available" : "Booked");
    }
}