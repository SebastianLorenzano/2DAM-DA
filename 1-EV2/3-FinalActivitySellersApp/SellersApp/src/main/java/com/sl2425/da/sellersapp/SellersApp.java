package com.sl2425.da.sellersapp;

import com.sl2425.da.sellersapp.Model.Database.HibernateDBManager;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;
import java.io.IOException;
import com.sl2425.da.sellersapp.Model.*;

public class SellersApp extends Application {

    @Override
    public void start(Stage stage) throws IOException
    {
        LogProperties.logger.info("Application started");
        GenericAppController.initialize(new HibernateDBManager());

        FXMLLoader fxmlLoader = new FXMLLoader(SellersApp.class.getResource("login-view.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), 320, 400);
        stage.setTitle("SellersApp — Login");
        stage.setResizable(false);  // Makes sure the user cannot resize the window
        stage.setScene(scene);
        stage.show();
    }

    public static void main(String[] args)
    {
        launch();
    }
}