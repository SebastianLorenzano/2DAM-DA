package com.sl2425.da.demo;

import javafx.fxml.FXML;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;

public class LoginController
{
    @FXML
    private TextField usernameField;

    @FXML
    private PasswordField passwordField;

    @FXML
    private void onLoginButtonClick() {
        // Get the text from the username and password fields
        String username = usernameField.getText();
        String password = passwordField.getText();

        // Print the username and password to the console (or handle them as needed)
        System.out.println("Username: " + username);
        System.out.println("Password: " + password);

        // Add further logic to handle login validation, etc.
    }
}
