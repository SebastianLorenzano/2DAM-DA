package com.sl2425.da.sellersapp.Controllers;

import com.sl2425.da.sellersapp.Model.*;
import com.sl2425.da.sellersapp.Model.Entities.SellerEntity;
import com.sl2425.da.sellersapp.Model.XML.RememberXML;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.CheckBox;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import java.io.IOException;

public class LoginController extends GenericAppController
{
    @FXML
    private TextField cifField;

    @FXML
    private PasswordField passwordField;

    @FXML
    private CheckBox rememberCheckbox;

    private RememberObj rememberObj = new RememberObj();

    @FXML
    private void initialize()
    {
        rememberObj = RememberXML.DeserializeXML(Utils.REMEMBER_CHECKBOX_PATH);
        if (rememberObj != null && rememberObj.getRemember().equals("true") && rememberObj.getCif() != null)
        {
            cifField.setText(rememberObj.getCif());
            rememberCheckbox.setSelected(true);
            rememberObj.setRemember("true");
        }
        else
        {
            rememberCheckbox.setSelected(false);
            rememberObj.setRemember("false");
        }



    }

    @FXML
    private synchronized void onLoginButtonClick()
    {
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
        SellerEntity seller = database.SelectSellerWithCifAndPassword(cif, password);
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
            mainStage.setScene(new Scene(root, 800, 700));
            mainStage.setResizable(false);  // Makes sure the user cannot resize the window
            mainStage.show();
            close();                // closes the login window
        }
        catch (IOException e)
        {
            e.printStackTrace();
        }
    }

    private void close()
    {   // Gets the current window, casts it and closes it
        System.out.println(rememberObj.getCif() + rememberObj.getRemember());
        RememberXML.SerializeXML(rememberObj, Utils.REMEMBER_CHECKBOX_PATH);
        ((Stage) cifField.getScene().getWindow()).close();
    }



}
