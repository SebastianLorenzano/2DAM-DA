package com.sl2425.da.sellersapp.Controllers;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;

public class MainView1Controller {

    @FXML
    private TextField inputField1; // CIF

    @FXML
    private TextField inputField2; // Name

    @FXML
    private TextField inputField3; // Business Name

    @FXML
    private TextField inputField4; // Phone

    @FXML
    private TextField inputField5; // Email

    @FXML
    private PasswordField inputField6; // Password

    @FXML
    private Button confirmButton; // Confirm Button

    @FXML
    private void initialize() {
        // Initialization logic, if needed
        confirmButton.setOnAction(event -> handleConfirmAction());
    }

    private void handleConfirmAction() {
        // Example logic for handling confirm button click
        String cif = inputField1.getText();
        String name = inputField2.getText();
        String businessName = inputField3.getText();
        String phone = inputField4.getText();
        String email = inputField5.getText();
        String password = inputField6.getText();

        // For demonstration purposes, printing the values to the console
        System.out.println("CIF: " + cif);
        System.out.println("Name: " + name);
        System.out.println("Business Name: " + businessName);
        System.out.println("Phone: " + phone);
        System.out.println("Email: " + email);
        System.out.println("Password: " + password);

        // Add additional logic here for handling the input (e.g., validation, storing data, etc.)
    }
}

