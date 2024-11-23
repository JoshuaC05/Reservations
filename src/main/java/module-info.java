module com.mycompany.hotel_reservation.joshua_currie {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.sql; 


    opens com.mycompany.hotel_reservation.joshua_currie to javafx.fxml;
    exports com.mycompany.hotel_reservation.joshua_currie;
}
    