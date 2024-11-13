package com.sl2425.da.sellersapp.Controllers;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.StackPane;
import java.io.IOException;

public class MainController {

    @FXML
    private StackPane contentPane;

    @FXML
    private ImageView imageButton1;

    @FXML
    private void initialize() {
        Image image = new Image(getClass().getResource("/com/sl2425/da/sellersapp/images/profile.png").toExternalForm());
        imageButton1.setImage(image);
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
    private void showView4() {
        loadView("/com/sl2425/da/sellersapp/main-view4.fxml");
    }
}
