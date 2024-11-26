package com.sl2425.da.sellersapp.Controllers;

import com.sl2425.da.sellersapp.Model.DatabaseOps;
import com.sl2425.da.sellersapp.Model.Entities.SellerEntity;
import com.sl2425.da.sellersapp.Model.LogProperties;
import com.sl2425.da.sellersapp.Model.Utils;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;

import java.util.Objects;

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
    private TextField urlField;

    @FXML
    private PasswordField passwordField;

    @FXML
    private PasswordField confirmPassField;

    @FXML
    private Button confirmButton;

    private static SellerEntity seller = null;

    @FXML
    private void initialize() {
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
            confirmPassField.setText("********");
            urlField.setText(seller.getUrl());
            if (seller.getPro())
                urlField.setEditable(true);

        }
    }

    private void handleConfirmAction()
    {
        SellerEntity updatedSeller = getSellerFromFields();
        if (!passwordField.getText().equals("********"))
        {
            String newPassword = passwordField.getText();
            if (!newPassword.equals(confirmPassField.getText()))
            {
                Utils.showError("Error updating seller: Passwords do not match.");
                return;
            }
            updatedSeller.setPassword(Utils.encryptToMD5(newPassword).toUpperCase());
        }
        if (isSellerValid(updatedSeller))
        {
            boolean result = DatabaseOps.updateSeller(updatedSeller);
            if (result)
            {
                Utils.currentSeller = updatedSeller;
                seller = updatedSeller;
                System.out.println("Seller updated successfully.");
                Utils.showConfirmation("Changes were made successfully!");

            }
            else
            {
                Utils.showError("Error updating seller.");
                System.out.println("Error updating seller.");
            }
        }
    }

    private static boolean isSellerEqualToCurrent(SellerEntity newSeller)
    {
        System.out.println(newSeller.getPro());
        System.out.println(newSeller.getUrl());
        return newSeller != null &&
                Objects.equals(newSeller.getCif(), seller.getCif()) &&
                Objects.equals(newSeller.getName(), seller.getName()) &&
                Objects.equals(newSeller.getBusinessName(), seller.getBusinessName()) &&
                Objects.equals(newSeller.getPhone(), seller.getPhone()) &&
                Objects.equals(newSeller.getEmail(), seller.getEmail()) &&
                Objects.equals(newSeller.getPassword(), seller.getPassword()) &&
                (!newSeller.getPro() || Objects.equals(newSeller.getUrl(), seller.getUrl()));
    }

    private static boolean isSellerValid(SellerEntity newSeller)
    {
        if (newSeller == null)
        {
            LogProperties.logger.severe("Error updating seller: Seller is null.");
            Utils.showError("Error updating seller: Seller is null.");
            return false;
        }

        if (isSellerEqualToCurrent(newSeller))
        {
            LogProperties.logger.severe("Error updating seller: There were no changes to update.");
            Utils.showError("There were no changes to update.");
            return false;
        }

        if (newSeller.getName() != null && newSeller.getName().trim().isEmpty() ||
                !newSeller.getName().matches("^[A-Za-zÀ-ÖØ-öø-ÿ0-9]+(?: [A-Za-zÀ-ÖØ-öø-ÿ0-9]+){0,149}$"))
        {
            LogProperties.logger.warning("Error updating seller: Name is not valid.");
            Utils.showError("Error updating seller: Name is not valid.");
            return false;
        }

        if (newSeller.getBusinessName() != null && !newSeller.getBusinessName().trim().isEmpty() &&
                (!newSeller.getBusinessName().matches("^[A-Za-zÀ-ÖØ-öø-ÿ0-9 .,*&@#$%^()!?'-]{1,150}$") ||
                        newSeller.getBusinessName().length() > 150))
        {
            LogProperties.logger.warning("Error updating seller: Business name is not valid.");
            Utils.showError("Error updating seller: Business name is not valid.");
            return false;
        }

        if (newSeller.getPhone() != null && !newSeller.getPhone().trim().isEmpty() &&
                !newSeller.getPhone().matches("^\\d{3,9}(-\\d{1,9})*$"))
        {
            LogProperties.logger.warning("Error updating seller: Phone is not valid.");
            Utils.showError("Error updating seller: Phone is not valid.");
            return false;
        }
        if (newSeller.getEmail() != null && !newSeller.getEmail().trim().isEmpty() &&
                !newSeller.getEmail().matches("^[A-Za-z0-9._%+-]{1,64}@[A-Za-z0-9.-]+\\.[A-Za-z]{2,}$") &&
                newSeller.getEmail().length() <= 254)
        {
            LogProperties.logger.warning("Error updating seller: Email is not valid.");
            Utils.showError("Error updating seller: Email is not valid.");
            return false;
        }
        if (newSeller.getPassword() == null || !newSeller.getPassword().trim().isEmpty() &&
                !newSeller.getPassword().matches("^[A-Fa-f0-9]{32}$"))
        {
            LogProperties.logger.warning("Error updating seller: Password is not valid.");
            Utils.showError("Error updating seller: Password is not valid.");
            return false;
        }

        if (newSeller.getPro() && !newSeller.getUrl().trim().isEmpty() &&
                !newSeller.getUrl().matches("^https?:\\/\\/(www\\.)?[\\w-]+(\\.[\\w-]+)+(\\.[a-z]{2,6})(\\/[\\w-]*)*\\/?$"))
        {
            LogProperties.logger.warning("Error updating seller: URL is not valid.");
            Utils.showError("Error updating seller: URL is not valid.");
            return false;
        }

        return true;
    }

    private SellerEntity getSellerFromFields()
    {
        return new SellerEntity()
        { {
            setId(seller.getId());
            setCif(cifField.getText());
            setName(nameField.getText());
            setBusinessName(businessNameField.getText());
            setPhone(phoneField.getText());
            setEmail(emailField.getText());
            setPassword(seller.getPassword());
            setPlainPassword(seller.getPlainPassword());
            setPro(seller.getPro());
            setUrl(urlField.getText());
        }};
    };
    }





