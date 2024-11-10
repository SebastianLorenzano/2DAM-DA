package com.sl2425.da.sellersapp.Controllers;

import com.sl2425.da.sellersapp.Model.DatabaseOps;
import com.sl2425.da.sellersapp.Model.Entities.SellerEntity;
import com.sl2425.da.sellersapp.Model.Utils;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;

public class MainView1Controller {

    @FXML
    private TextField cifField;

    @FXML
    private TextField nameField;

    @FXML
    private TextField businessNameField;

    @FXML
    private TextField phoneField;

    @FXML
    private TextField emailField;

    @FXML
    private PasswordField passwordField;

    @FXML
    private PasswordField confirmPassField;

    @FXML
    private Button confirmButton;

    private SellerEntity seller = null;

    @FXML
    private void initialize() {
        // Initialization logic, if needed
        confirmButton.setOnAction(event -> handleConfirmAction());
        seller = Utils.currentSeller;
        if (seller == null)
            System.out.println("Seller is null");
        else
        {
            cifField.setText(seller.getCif());
            nameField.setText(seller.getName());
            businessNameField.setText(seller.getBusinessName());
            phoneField.setText(seller.getPhone());
            emailField.setText(seller.getEmail());
            passwordField.setText("********");
        }
    }

    private void handleConfirmAction() {
        SellerEntity updatedSeller = new SellerEntity()
        { {
                setId(seller.getId());
                setCif(cifField.getText());
                setName(nameField.getText());
                setBusinessName(businessNameField.getText());
                setPhone(phoneField.getText());
                setEmail(emailField.getText());
                setPassword(seller.getPassword());
                setPlainPassword(seller.getPlainPassword());
                ; }
        };
        if (isSellerValid(updatedSeller))
        {
            boolean result = DatabaseOps.updateSeller(updatedSeller);
            if (result)
            {
                Utils.currentSeller = updatedSeller;
                System.out.println("Seller updated successfully.");
            }
            else
                System.out.println("Error updating seller.");
        }



    }

    public boolean isSellerValid(SellerEntity seller)
    {
        return seller != null && seller.getCif() != null && !seller.getCif().isEmpty() &&
                seller.getName() != null && !seller.getName().isEmpty() &&
                seller.getBusinessName() != null && !seller.getBusinessName().isEmpty() &&
                seller.getPhone() != null && !seller.getPhone().isEmpty() &&
                seller.getPassword() != null && !seller.getPassword().isEmpty();
    }
}




