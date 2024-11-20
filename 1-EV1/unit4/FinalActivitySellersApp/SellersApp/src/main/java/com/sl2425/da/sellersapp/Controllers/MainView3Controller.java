package com.sl2425.da.sellersapp.Controllers;

import com.sl2425.da.sellersapp.Model.Entities.CategoryEntity;
import com.sl2425.da.sellersapp.Model.Entities.SellerProductEntity;
import com.sl2425.da.sellersapp.Model.Utils;
import com.sl2425.da.sellersapp.Model.DatabaseOps;
import javafx.fxml.FXML;
import javafx.scene.control.*;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class MainView3Controller
{

    @FXML
    private ComboBox<SellerProductEntity> sellerProductBox;

    @FXML
    private DatePicker fromDatePicker;

    @FXML
    private DatePicker toDatePicker;

    @FXML
    private TextField discountTextField;

    @FXML
    private Button addButton;

    private List<SellerProductEntity> sellerProducts = new ArrayList<SellerProductEntity>();

    @FXML
    private void initialize() {
        initializeSellerProductsBox();
        initializeDatePickers();

        addButton.setOnAction(event -> handleAddAction());
    }




    private void initializeSellerProductsBox()
    {
        refreshSellerProductsBox();
        sellerProductBox.setPromptText("Select your Product");
        sellerProductBox.setCellFactory(lv -> new ListCell<>() {
            @Override
            protected void updateItem(SellerProductEntity sellerProduct, boolean empty) {
                super.updateItem(sellerProduct, empty);                       // Same as it does in categories right ontop
                setText(empty ? null : sellerProduct.getProduct().getProductName());
            }
        });
        sellerProductBox.setButtonCell(new ListCell<>() {
            @Override
            protected void updateItem(SellerProductEntity sellerProduct, boolean empty) {
                super.updateItem(sellerProduct, empty);
                setText(empty ? null : sellerProduct.getProduct().getProductName());
            }
        });
    }

    private void initializeDatePickers()
    {
        fromDatePicker.setPromptText("From");
        toDatePicker.setPromptText("To");
        fromDatePicker.setValue(LocalDate.now());
        toDatePicker.setValue(LocalDate.now().plusDays(1));
    }

    private void refreshSellerProductsBox()
    {
        sellerProductBox.getItems().clear();
        List<SellerProductEntity> sellerProducts = DatabaseOps.SelectSellerProducts(Utils.currentSeller);
        if (sellerProducts == null)
        {
            Utils.showError("Failed to load products. Please try again.");
            return;
        }
        if (sellerProducts.isEmpty())
        {
            Utils.showConfirmation("You have no products to add offers to.");
            return;
        }
        for (SellerProductEntity sellerProduct : sellerProducts)
        {
            if (!Utils.doesDatePeriodCollide(LocalDate.now(), LocalDate.now(),
                    sellerProduct.getOfferStartDate(), sellerProduct.getOfferEndDate()))
            {
                sellerProductBox.getItems().add(sellerProduct);
                this.sellerProducts.add(sellerProduct);
            }

        }
    }

    private void handleAddAction() {
        SellerProductEntity sellerProduct = sellerProductBox.getValue();
        sellerProduct.setOfferStartDate(fromDatePicker.getValue());
        sellerProduct.setOfferEndDate(toDatePicker.getValue());
        String discountString = discountTextField.getText();
        int discount = 0;
        try
        {
            discount = Integer.parseInt(discountString);
        }
        catch (NumberFormatException e)
        {
            Utils.showError("Invalid discount. Please enter a valid number.");
            return;
        }

        if (isOfferValid(sellerProduct, fromDatePicker.getValue(), toDatePicker.getValue(), discount))
        {
            sellerProduct.setOfferStartDate(fromDatePicker.getValue());
            sellerProduct.setOfferEndDate(toDatePicker.getValue());
            double discountedPrice = sellerProduct.getPrice().doubleValue() * (1 - discount / 100.0);
            sellerProduct.setOfferPrice(new BigDecimal(discountedPrice));
            AddOffer(sellerProduct);
        }
    }


    private void AddOffer(SellerProductEntity sellerProduct)
    {
        if (DatabaseOps.AddOffer(sellerProduct))
            Utils.showConfirmation("Offer added successfully.");
        else
            Utils.showError("Failed to add offer. Please try again.");
    }

    private boolean isOfferValid(SellerProductEntity sellerProduct, LocalDate fromDate, LocalDate toDate, int discount)
    {
        if (sellerProduct == null || fromDate == null || toDate == null)
        {
            Utils.showError("Please fill in all fields before adding the offer.");
            return false;
        }

        if (ChronoUnit.DAYS.between(LocalDate.now(), fromDate) < 0)
        {
            Utils.showError("Invalid date. 'From' date must be today or later.");
            return false;
        }

        if (discount < 0 || discount > 100)
        {
            Utils.showError("Invalid discount. Please enter a value between 0 and 100.");
            return false;
        }

        long daysDistance = ChronoUnit.DAYS.between(fromDate, toDate);
        if (daysDistance < 0)
        {
            Utils.showError("Invalid date range. 'From' date must be before 'to' date.");
            return false;
        }

        if (daysDistance == 0)
        {
            Utils.showError("Invalid date range. 'From' date must be different 'to' date.");
            return false;
        }

        if (daysDistance > 30)
        {
            Utils.showError("Invalid date range. Maximum offer duration is 30 days.");
            return false;
        }

        if (daysDistance >  15 && discount > 10)
        {
            Utils.showError("Invalid discount. Maximum discount for offers longer than 15 days is 10%.");
            return false;
        }

        if (daysDistance > 7 && discount > 15)
        {
            Utils.showError("Invalid discount. Maximum discount for offers longer than 7 days is 15%.");
            return false;
        }

        if (daysDistance > 3 && discount > 20)
        {
            Utils.showError("Invalid discount. Maximum discount for offers longer than 3 days is 20%.");
            return false;
        }

        if (daysDistance > 1 && discount > 30)
        {
            Utils.showError("Invalid discount. Maximum discount for offers longer than 1 day is 30%.");
            return false;
        }

        if (daysDistance == 1 && discount > 50)
        {
            Utils.showError("Invalid discount. Maximum discount for offers lasting 1 day is 50%.");
            return false;
        }

        for (SellerProductEntity sProduct : sellerProductBox.getItems())
        {
            if (!Objects.equals(sProduct.getId(), sellerProduct.getId()) &&
                    Utils.doesDatePeriodCollide(fromDate, toDate, sProduct.getOfferStartDate(), sProduct.getOfferEndDate()))
            {
                Utils.showError("Invalid date range. The offer coincides with another offer.");
                return false;
            }
        }
        return true;
    }
}
