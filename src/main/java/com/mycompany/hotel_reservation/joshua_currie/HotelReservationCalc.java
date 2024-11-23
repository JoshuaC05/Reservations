package com.mycompany.hotel_reservation.joshua_currie;

import java.io.IOException;
import java.net.URL;
import java.text.NumberFormat;
import java.text.ParseException;
import java.time.LocalDate;
import java.time.format.DateTimeParseException;
import java.time.temporal.ChronoUnit;
import java.util.Locale;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.TextField;
import javafx.stage.Modality;
import javafx.stage.Stage;

public class HotelReservationCalc implements Initializable {

    //All TextFields
    @FXML
    private TextField ArriveDate;
    
    @FXML
    private TextField DepartDate;
    
    @FXML
    private TextField NumberNights;
    
    @FXML
    private TextField TotalPrice;
    
    // Method to initialize default values for date fields
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // Get the current date and set the default dates
        LocalDate currentDate = LocalDate.now();
        ArriveDate.setText(currentDate.toString()); // Sets today's date
        DepartDate.setText(currentDate.plusDays(3).toString()); // Sets the date 3 days later
    }
    
     // Method to calculate the total price based on input dates
    @FXML
    private void calculateTotalPrice(ActionEvent event) {
        // Retrieve and validate the input dates from the text fields
        String arrivalDateText = ArriveDate.getText().trim();
        String departureDateText = DepartDate.getText().trim();

        try {
            // Parse the input dates
            LocalDate arrivalDate = LocalDate.parse(arrivalDateText);
            LocalDate departureDate = LocalDate.parse(departureDateText);
            LocalDate currentDate = LocalDate.now();

            if (arrivalDate.isBefore(currentDate)) {
                showAlert("Error", "Arrival date must be after the current date.");
                return;
            }

            // Ensure the departure date is after the arrival date
            if (departureDate.isBefore(arrivalDate) || departureDate.equals(arrivalDate)) {
                showAlert("Error", "Departure date must be after arrival date.");
                return;
            }

            // Calculate the number of nights and total price
            long numNights = ChronoUnit.DAYS.between(arrivalDate, departureDate);
            double totalPrice = numNights * 120;

            // Format the total price as Canadian currency
            NumberFormat currencyFormat = NumberFormat.getCurrencyInstance(Locale.CANADA);
            String formattedPrice = currencyFormat.format(totalPrice);

            // Update the text fields with the results
            NumberNights.setText(String.valueOf(numNights));
            TotalPrice.setText(formattedPrice); // Use the formatted price here
        } catch (DateTimeParseException e) {
            // Handle invalid date format error
            showAlert("Error", "Invalid date format. Please use YYYY-MM-DD.");
        }
    }
    
    // Method to handle the hotel reservations list
    @FXML
    private void HotelReservationsList() throws IOException {
        try {
            // Retrieve input data from text fields
            LocalDate arrivalDate = LocalDate.parse(ArriveDate.getText().trim());
            LocalDate departureDate = LocalDate.parse(DepartDate.getText().trim());
            int numNights = Integer.parseInt(NumberNights.getText().trim());

            // Extract numeric value from formatted price (remove currency symbol)
            String totalPriceText = TotalPrice.getText().trim();
            double totalPrice = parseCurrencyToDouble(totalPriceText);

            // Create a Hotel_Reservations object
            Hotel_Reservations reservation = new Hotel_Reservations(arrivalDate, departureDate, numNights, totalPrice);

            // Add the reservation to the database
            boolean success = HotelDB.addReservation(reservation);
            if (success) {
                showAlert("Success", "Reservation added successfully.");
                ArriveDate.setText("");
                DepartDate.setText("");
                NumberNights.setText("");
                TotalPrice.setText("");
            } else {
                showAlert("Error", "Failed to add reservation.");
            }

            // Load the reservations list
            FXMLLoader loader = new FXMLLoader(getClass().getResource("ReservationsApp.fxml"));
            Parent root = loader.load();
            Stage stage = new Stage();
            stage.setTitle("Reservations");
            stage.setScene(new Scene(root));
            stage.initModality(Modality.APPLICATION_MODAL);
            stage.showAndWait();

        } catch (DateTimeParseException e) {
            showAlert("Error", "Invalid date format. Please use YYYY-MM-DD.");
        } catch (NumberFormatException e) {
            showAlert("Error", "Please ensure the number of nights and total price are valid numbers.");
        } catch (IOException e) {
            showAlert("Error", "Unable to open the Reservations form.");
        }
    }

    // Method to parse the currency formatted string to double
    private double parseCurrencyToDouble(String currencyString) {
        try {
            return NumberFormat.getCurrencyInstance(Locale.CANADA).parse(currencyString).doubleValue();
        } catch (ParseException e) {
            throw new NumberFormatException("Invalid currency format: " + currencyString);
        }
    }
    
    @FXML
    private void Exit(ActionEvent event) {
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        stage.close();
    }
    
    // Method to display alert messages
    private void showAlert(String title, String message) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }
}