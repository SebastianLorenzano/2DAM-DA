package com.sl2425.da.sellersapp.Controllers;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.io.IOException;

public class LoginController
{
    @FXML
    private TextField cifField;

    @FXML
    private PasswordField passwordField;

    @FXML
    private void onLoginButtonClick() {
        // Get the text from the username and password fields
        String username = cifField.getText();
        String password = passwordField.getText();

        // Print the username and password to the console (or handle them as needed)
        System.out.println("Username: " + username);
        System.out.println("Password: " + password);
        openMainWindow();

        // Add further logic to handle login validation, etc.
    }

    private void openMainWindow() {
        try {
            // Load the new FXML file
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/com/sl2425/da/sellersapp/main-view.fxml"));
            Parent root = fxmlLoader.load();

            // Create a new stage for the dashboard window
            Stage mainStage = new Stage();
            mainStage.setTitle("SellersApp — Main");
            mainStage.setScene(new Scene(root, 800, 600));
            mainStage.show();
            close();                // closes the login window

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void close()
    {   // Gets the current window, casts it and closes it
        ((Stage) cifField.getScene().getWindow()).close();
    }
}
