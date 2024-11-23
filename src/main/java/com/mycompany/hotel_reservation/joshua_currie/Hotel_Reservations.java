package com.mycompany.hotel_reservation.joshua_currie;

import java.text.NumberFormat;
import java.time.LocalDate;
import java.util.Locale;

public class Hotel_Reservations {
    private LocalDate arrivalDate;
    private LocalDate departureDate;
    private int numNights;
    private double price;

    // Constructor
    public Hotel_Reservations(LocalDate arrivalDate, LocalDate departureDate, int numNights, double price) {
        this.arrivalDate = arrivalDate;
        this.departureDate = departureDate;
        this.numNights = numNights;
        this.price = price;
    }
    //
    // Getters
    public LocalDate getArrivalDate() {
        return arrivalDate;
    }

    public LocalDate getDepartureDate() {
        return departureDate;
    }

    public int getNumNights() {
        return numNights;
    }

    public double getPrice() {
        return price;
    }
    
    // Override the toString method
    @Override
    public String toString() {
        NumberFormat currencyFormat = NumberFormat.getCurrencyInstance(Locale.CANADA);
        String formattedPrice = currencyFormat.format(price);
        
        return String.format("Arrival: %s, Departure: %s, Nights: %d, Price: %s",
                arrivalDate.toString(),
                departureDate.toString(),
                numNights,
                formattedPrice);
    }
}