module com.sl2425.da.demo {
    requires javafx.controls;
    requires javafx.fxml;
    requires jdk.jfr;


    opens com.sl2425.da.demo to javafx.fxml;
    exports com.sl2425.da.demo;
}