module com.sl2425.da.sellersapp {
    requires javafx.controls;
    requires javafx.fxml;
    requires jakarta.persistence;
    requires java.naming;



    opens com.sl2425.da.sellersapp to javafx.fxml;
    exports com.sl2425.da.sellersapp;
    exports com.sl2425.da.sellersapp.Controllers;
    opens com.sl2425.da.sellersapp.Controllers to javafx.fxml;
}