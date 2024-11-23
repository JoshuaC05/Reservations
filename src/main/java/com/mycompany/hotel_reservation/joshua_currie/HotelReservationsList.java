package com.mycompany.hotel_reservation.joshua_currie;

import java.util.List;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.stage.Stage;

public class HotelReservationsList {

    @FXML
    private Button closeButton;

    @FXML
    private ListView<String> Reservations;

    @FXML
    private void initialize() {
        loadReservations();
    }

   private void loadReservations() {
        // Get reservations from the database
        List<Hotel_Reservations> reservations = HotelDB.getReservations(); 
         // Clear any existing items in the ListView
        Reservations.getItems().clear();
        for (Hotel_Reservations reservation : reservations) {
            // Use the toString method
            Reservations.getItems().add(reservation.toString()); 
        }
        if (reservations.isEmpty()) {
             // Display a message if no reservations
            Reservations.getItems().add("No reservations found.");
        }
    }

   //close event
    @FXML
    private void closeWindow() {
        Stage stage = (Stage) closeButton.getScene().getWindow();
        stage.close();
    }
}