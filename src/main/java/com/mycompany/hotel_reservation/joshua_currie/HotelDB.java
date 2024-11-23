package com.mycompany.hotel_reservation.joshua_currie;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

public class HotelDB {
    private static final String DB_URL = "jdbc:mysql://localhost:3306/hoteldb";
    private static final String USER = "root"; 
    private static final String PASS = ""; 
    private static final Logger logger = Logger.getLogger(HotelDB.class.getName());

    public static Connection getConnection() {
        try {
            return DriverManager.getConnection(DB_URL, USER, PASS);
        } catch (SQLException ex) {
            logger.log(Level.SEVERE, "Error connecting to database", ex);
        }
        return null;
    }

    public static boolean addReservation(Hotel_Reservations hr) {
    String sql = "INSERT INTO reservations (arrival_date, departure_date, num_nights, price) VALUES (?, ?, ?, ?)";
    
    try (Connection conn = getConnection(); PreparedStatement pstmt = conn.prepareStatement(sql)) {
        pstmt.setDate(1, java.sql.Date.valueOf(hr.getArrivalDate()));
        pstmt.setDate(2, java.sql.Date.valueOf(hr.getDepartureDate()));
        pstmt.setInt(3, hr.getNumNights());
        pstmt.setDouble(4, hr.getPrice());

        int affectedRows = pstmt.executeUpdate();
        return affectedRows > 0; // Return true if at least one row was inserted
    } 
    catch (SQLException ex) {
        Logger.getLogger(HotelDB.class.getName()).log(Level.SEVERE, "Error adding reservation", ex);
        return false; // Return false on failure
    }
}

    public static List<Hotel_Reservations> getReservations() {
        List<Hotel_Reservations> reservations = new ArrayList<>();
        String sql = "SELECT * FROM reservations"; // Using all columns

        try (Connection conn = getConnection(); PreparedStatement pstmt = conn.prepareStatement(sql);
             ResultSet rs = pstmt.executeQuery()) {
            while (rs.next()) {
                LocalDate arrivalDate = rs.getDate("arrival_date").toLocalDate();
                LocalDate departureDate = rs.getDate("departure_date").toLocalDate();
                int numNights = rs.getInt("num_nights"); // Ensure correct column name
                double price = rs.getDouble("price");

                reservations.add(new Hotel_Reservations(arrivalDate, departureDate, numNights, price));
            }
        } catch (SQLException ex) {
            logger.log(Level.SEVERE, "Error retrieving reservations", ex);
        }
        System.out.println("Retrieved Reservations: " + reservations); // Check what is retrieved
        return reservations;
    }
}
