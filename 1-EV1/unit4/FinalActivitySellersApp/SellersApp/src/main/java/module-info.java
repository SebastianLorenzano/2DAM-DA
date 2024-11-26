module com.sl2425.da.sellersapp {
    requires javafx.controls;
    requires javafx.fxml;
    requires org.hibernate.orm.core;
    requires java.naming;
    requires jakarta.persistence;
    requires jdk.jshell;
    requires jakarta.xml.bind;
    requires java.desktop;

    // Open the entities package to Hibernate for reflection access
    opens com.sl2425.da.sellersapp.Model.Entities to org.hibernate.orm.core;

    // Export the main package for use by other modules
    exports com.sl2425.da.sellersapp;

    exports com.sl2425.da.sellersapp.Model.Entities;

    opens com.sl2425.da.sellersapp.Controllers to javafx.fxml;
}
