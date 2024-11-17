package com.sl2425.da.sellersapp.Controllers;

import com.sl2425.da.sellersapp.Model.Utils;
import com.sl2425.da.sellersapp.SellersApp;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;

import java.io.IOException;

public class MainController {

    @FXML
    private StackPane contentPane;

    @FXML
    private ImageView imageButton1;

    @FXML
    private ImageView imageButton2;

    @FXML
    private ImageView imageButton3;

    @FXML
    private ImageView imageButton4;

    @FXML
    private void initialize() {
        Image profile = new Image(getClass().getResource("/com/sl2425/da/sellersapp/images/userSettingsBig.png").toExternalForm());
        imageButton1.setImage(profile);
        Image addProduct = new Image(getClass().getResource("/com/sl2425/da/sellersapp/images/addProductBig.png").toExternalForm());
        imageButton2.setImage(addProduct);
        Image addOffer = new Image(getClass().getResource("/com/sl2425/da/sellersapp/images/addOfferBig.png").toExternalForm());
        imageButton3.setImage(addOffer);
        Image logout = new Image(getClass().getResource("/com/sl2425/da/sellersapp/images/logoutBig.png").toExternalForm());
        imageButton4.setImage(logout);
    }


    private void loadView(String fxmlFile) {
        try {
            Parent view = FXMLLoader.load(getClass().getResource(fxmlFile));
            contentPane.getChildren().clear();
            contentPane.getChildren().add(view);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    // Button action methods
    @FXML
    private void showView1() {
        loadView("/com/sl2425/da/sellersapp/main-view1.fxml");
    }

    @FXML
    private void showView2() {
        loadView("/com/sl2425/da/sellersapp/main-view2.fxml");
    }

    @FXML
    private void showView3() {
        loadView("/com/sl2425/da/sellersapp/main-view3.fxml");
    }

    @FXML
    private void logout() {
        Utils.currentSeller = null;
        try {
            // Load the new FXML file
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/com/sl2425/da/sellersapp/login-view.fxml"));
            Parent root = fxmlLoader.load();

            // Create a new stage for the logout window
            Stage mainStage = new Stage();
            mainStage.setTitle("SellersApp — Main");
            mainStage.setScene(new Scene(root, 320, 400));
            mainStage.show();
            close();                // closes the main window
        }
        catch (IOException e)
        {
            e.printStackTrace();
        }
    }

    private void close()
    {   // Gets the current window, casts it and closes it
        ((Stage) contentPane.getScene().getWindow()).close();
    }
}

