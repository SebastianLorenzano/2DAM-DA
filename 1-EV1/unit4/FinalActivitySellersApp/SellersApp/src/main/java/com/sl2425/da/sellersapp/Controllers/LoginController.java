package com.sl2425.da.sellersapp.Controllers;

import com.sl2425.da.sellersapp.Model.Entities.SellerEntity;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import java.io.IOException;
import com.sl2425.da.sellersapp.Model.Utils;
import com.sl2425.da.sellersapp.Model.DatabaseOps;
import com.sl2425.da.sellersapp.Model.LogProperties;

public class LoginController
{
    @FXML
    private TextField cifField;

    @FXML
    private PasswordField passwordField;

    @FXML
    private synchronized void onLoginButtonClick() {
        String username = cifField.getText();
        String password = Utils.encryptToMD5(passwordField.getText()).toUpperCase();
        if (checkLogin(username, password))
        openMainWindow();
    }

    private boolean checkLogin(String cif, String password)
    {
        if (cif.isEmpty() || password.isEmpty())
        {
            LogProperties.logger.warning("Fields are empty");
            Utils.showError("Fields are empty");
            return false;
        }
        SellerEntity seller = DatabaseOps.SelectSellerWithCifAndPassword(cif, password);
        if (seller == null)
        {
            LogProperties.logger.warning("Login failed: Invalid credentials");
            Utils.showError("Login failed: Invalid credentials");
            return false;
        }
        Utils.currentSeller = seller;
        return true;
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
