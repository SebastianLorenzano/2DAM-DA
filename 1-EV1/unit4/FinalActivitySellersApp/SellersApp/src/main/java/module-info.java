module com.sl2425.da.sellersapp {
    requires javafx.controls;
    requires javafx.fxml;


    opens com.sl2425.da.sellersapp to javafx.fxml;
    exports com.sl2425.da.sellersapp;
}