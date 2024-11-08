package com.sl2425.da.sellersapp;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.logging.FileHandler;
import java.util.logging.Logger;
import com.sl2425.da.sellersapp.Model.*;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;

import static com.sl2425.da.sellersapp.Model.LogProperties.logger;

public class SellersApp extends Application {

    @Override
    public void start(Stage stage) throws IOException
    {
        FXMLLoader fxmlLoader = new FXMLLoader(SellersApp.class.getResource("login-view.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), 320, 400);
        stage.setTitle("SellersApp — Login");
        stage.setResizable(false);  // Desactiva la posibilidad de redimensionar la ventana
        stage.setScene(scene);
        stage.show();
    }

    public static void main(String[] args)
    {
        launch();
    }
}