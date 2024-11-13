package com.sl2425.da.sellersapp.Controllers;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Slider;
import javafx.scene.control.TextField;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;

public class MainView2Controller {

    @FXML
    private ComboBox<String> categoryComboBox;

    @FXML
    private ComboBox<String> productComboBox;

    @FXML
    private Slider stockSlider;

    @FXML
    private TextField priceTextField;

    @FXML
    private Button addButton;

    @FXML
    private void initialize() {
        // Initialize ComboBoxes with sample data
        categoryComboBox.getItems().addAll("Electronics", "Books", "Clothing", "Groceries");
        productComboBox.getItems().addAll("TV", "Laptop", "Smartphone", "Tablet");

        // Set default values or behavior if needed
        stockSlider.setValue(0);

        // Set event handler for add button
        addButton.setOnAction(event -> handleAddAction());
    }

    private void handleAddAction() {
        String category = categoryComboBox.getValue();
        String product = productComboBox.getValue();
        int stock = (int) stockSlider.getValue();
        String priceText = priceTextField.getText();

        if (category == null || product == null || priceText.isEmpty()) {
            showError("Please fill in all fields before adding the product.");
            return;
        }

        try {
            double price = Double.parseDouble(priceText);
            // Perform action to add product to the store (e.g., save to database or list)
            showConfirmation("Product added successfully: " + product + " in category " + category + " with stock " + stock + " and price " + price);
        } catch (NumberFormatException e) {
            showError("Invalid price. Please enter a valid number.");
        }
    }

    private void showError(String message) {
        Alert alert = new Alert(AlertType.ERROR);
        alert.setTitle("Error");
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }

    private void showConfirmation(String message) {
        Alert alert = new Alert(AlertType.INFORMATION);
        alert.setTitle("Success");
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }
}
