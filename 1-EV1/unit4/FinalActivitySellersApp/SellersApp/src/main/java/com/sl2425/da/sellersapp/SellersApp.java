package com.sl2425.da.sellersapp;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class SellersApp extends Application {
    @Override
    public void start(Stage stage) throws IOException
    {
        FXMLLoader fxmlLoader = new FXMLLoader(SellersApp.class.getResource("login-view.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), 320, 400);
        stage.setTitle("SellersApp — Login");
        stage.setScene(scene);
        stage.show();
    }

    public static void main(String[] args)
    {
        launch();
    }
}