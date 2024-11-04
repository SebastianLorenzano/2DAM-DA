package com.sl2425.da.sellersapp;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.control.Button;
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
        loadView("main-view1.fxml");
    }

    @FXML
    private void showView2() {
        loadView("view2.fxml");
    }

    @FXML
    private void showView3() {
        loadView("view3.fxml");
    }

    @FXML
    private void showView4() {
        loadView("view4.fxml");
    }
}
