package com.sl2425.da.sellersapp.Controllers;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.layout.StackPane;
import java.io.IOException;

public class MainController {

    @FXML
    private StackPane contentPane;

    // Method to load a specific view into the content pane
    private void loadView(String fxmlFile) {
        try {
            // Load the FXML for the selected view
            Parent view = FXMLLoader.load(getClass().getResource(fxmlFile));

            // Clear existing content and add the new view
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
