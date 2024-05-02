module com.example.bets_day6 {
    requires javafx.controls;
    requires javafx.fxml;


    opens com.example.bets_day6 to javafx.fxml;
    exports com.example.bets_day6;
}