module com.example.secondtry {
    requires javafx.controls;
    requires javafx.fxml;


    opens com.example.secondtry to javafx.fxml;
    exports com.example.secondtry;
}