package com.mycompany.hotel_reservation.joshua_currie;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.List;

public class App extends Application {

    private static Scene scene;
    private static Stage primaryStage;

    @Override
    public void start(Stage stage) throws IOException {
        primaryStage = stage;
        scene = new Scene(loadFXML("HotelReservationApp"));
        stage.setScene(scene);
        stage.setTitle("Reservations");
        stage.show();
        
    }

    public static Stage getPrimaryStage() {
        return primaryStage;
    }

    static void setRoot(String fxml) throws IOException {
        scene.setRoot(loadFXML(fxml));
    }

    private static Parent loadFXML(String fxml) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(App.class.getResource(fxml + ".fxml"));
        return fxmlLoader.load();
    }

    public static void main(String[] args) {
        launch();
    }

    public static boolean addReservation(Hotel_Reservations hr) {
            return HotelDB.addReservation(hr);
    }
    
    public static List<Hotel_Reservations> getReservations() {
        return HotelDB.getReservations();
    }
}
