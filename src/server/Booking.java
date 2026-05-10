package server;

import java.time.LocalDate;
import java.time.LocalDateTime;

public class Booking {

    private String customerName;
    private int roomNumber;
    private String roomType;
    private double price;
    private LocalDate checkInDate;
    private LocalDate checkOutDate;
    private LocalDateTime bookingTime;

    public Booking(String customerName,
                   int roomNumber,
                   String roomType,
                   double price,
                   LocalDate checkInDate,
                   LocalDate checkOutDate) {

        this.customerName = customerName;
        this.roomNumber = roomNumber;
        this.roomType = roomType;
        this.price = price;
        this.checkInDate = checkInDate;
        this.checkOutDate = checkOutDate;
        this.bookingTime = LocalDateTime.now();
    }

    public String getCustomerName() {
        return customerName;
    }

    public int getRoomNumber() {
        return roomNumber;
    }

    public String getRoomType() {
        return roomType;
    }

    public double getPrice() {
        return price;
    }

    public LocalDate getCheckInDate() {
        return checkInDate;
    }

    public LocalDate getCheckOutDate() {
        return checkOutDate;
    }

    @Override
    public String toString() {
        return customerName +
                " booked Room " + roomNumber +
                " (" + roomType + ")" +
                " | Price: €" + price +
                " | Check-in: " + checkInDate +
                " | Check-out: " + checkOutDate +
                " | Booking Time: " + bookingTime;
    }
}