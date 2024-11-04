package com.sl2425.da.demo;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import jdk.jfr.Name;

public class HelloController
{
    @FXML
    private Label welcomeText;

    @FXML
    protected void onHelloButtonClick()
    {
        welcomeText.setText("Welcome to JavaFX Application!");
    }
}