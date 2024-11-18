package com.sl2425.da.sellersapp.Controllers;

import com.sl2425.da.sellersapp.Model.Entities.ProductEntity;
import com.sl2425.da.sellersapp.Model.Utils;
import com.sl2425.da.sellersapp.Model.DatabaseOps;
import com.sl2425.da.sellersapp.Model.LogProperties;
import javafx.fxml.FXML;
import javafx.scene.control.*;

import java.time.LocalDate;
import java.util.List;

public class MainView3Controller
{

    @FXML
    private ComboBox<ProductEntity> productBox;

    @FXML
    private DatePicker fromDatePicker;

    @FXML
    private DatePicker toDatePicker;

    @FXML
    private TextField discountTextField;

    @FXML
    private Button addButton;

    private static

    @FXML
    private void initialize() {
        initializeProductsBox();

        addButton.setOnAction(event -> handleAddAction());
    }




    private void initializeProductsBox()
    {
        productBox.setPromptText("Select Product");
        productBox.setCellFactory(lv -> new ListCell<>() {
            @Override
            protected void updateItem(ProductEntity product, boolean empty) {
                super.updateItem(product, empty);                       // Same as it does in categories right ontop
                setText(empty ? null : product.getProductName());
            }
        });
        productBox.setButtonCell(new ListCell<>() {
            @Override
            protected void updateItem(ProductEntity product, boolean empty) {
                super.updateItem(product, empty);
                setText(empty ? null : product.getProductName());
            }
        });
    }

    private void handleAddAction() {
        ProductEntity product = productBox.getValue();
        LocalDate fromDate = fromDatePicker.getValue();
        LocalDate toDate = toDatePicker.getValue();
        String discountString = discountTextField.getText();

        if (product == null || fromDate == null || toDate == null || discountString.isEmpty()) {
            Utils.showError("Please fill in all fields before adding the offer.");
            return;
        }


        /*
        try {
            int discount = Integer.parseInt(discountString);
            if (discount < 0 || discount > 100) {
                Utils.showError("Invalid discount. Please enter a value between 0 and 100.");
                return;
            }

            if (fromDate.isAfter(toDate)) {
                Utils.showError("Invalid date range. 'From' date cannot be after 'To' date.");
                return;
            }

            if (DatabaseOps.InsertOffer(product, fromDate, toDate, discount)) {
                Utils.showConfirmation("Offer added successfully for product: " + product.getProductName());
            } else {
                Utils.showError("Error adding offer. Please try again.");
            }
        } catch (NumberFormatException e) {
            Utils.showError("Invalid discount. Please enter a valid number.");
        }
        */
    }
}
