package com.sl2425.da.sellersapp.Controllers;

import com.sl2425.da.sellersapp.Model.Entities.SellerProductEntity;
import com.sl2425.da.sellersapp.Model.Utils;
import com.sl2425.da.sellersapp.Model.DatabaseOps;
import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.input.KeyEvent;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
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
    private Spinner<Integer> discountSpinner;

    @FXML
    private Button addButton;

    @FXML
    private void initialize() {
        initializeSellerProductsBox();
        initializeDatePickers();
        initializeDiscountSpinner();

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
        initializeFromDateDayCellFactory();
        initializeToDatePickers();
        fromDatePicker.setPromptText("From");
        toDatePicker.setPromptText("To");
        fromDatePicker.setValue(LocalDate.now());
        toDatePicker.setValue(LocalDate.now().plusDays(1));
        fromDatePicker.getEditor().addEventFilter(KeyEvent.ANY, Event::consume);
        toDatePicker.getEditor().addEventFilter(KeyEvent.ANY, Event::consume);
            // Destroys listeners to make sure user cannot write in the DatePicker

    }

    private void initializeFromDateDayCellFactory()
    {
        fromDatePicker.setDayCellFactory(datePicker -> new DateCell() {
            @Override
            public void updateItem(LocalDate item, boolean empty) {
                super.updateItem(item, empty);

                if (empty || item == null) {
                    setDisable(false);
                    setStyle("");
                    return;
                }

                List<SellerProductEntity> sellerProducts = sellerProductBox.getItems();
                if (sellerProducts == null || sellerProducts.isEmpty()) {
                    // If no seller products, there's no need to disable anything
                    setDisable(false);
                    setStyle("");
                    return;
                }

                boolean isCollision = sellerProducts.stream().anyMatch(sellerProduct -> {
                    LocalDate startDate = sellerProduct.getOfferStartDate();
                    LocalDate endDate = sellerProduct.getOfferEndDate();

                    if (startDate == null || endDate == null)
                        return false;
                    return Utils.doesDatePeriodCollide(item, item, startDate, endDate);
                });

                if (isCollision)
                {
                    setDisable(true);   // Disables and pints red to tell user that the date is not available
                    setStyle("-fx-background-color: #ffcccc;");
                }
                else
                {
                    setDisable(false);
                    setStyle("");
                }
            }
        });
    }

    private void initializeToDatePickers()
    {
        fromDatePicker.valueProperty().addListener((observable, oldValue, newValue) ->
        {
            if (newValue != null) {
                LocalDate maxDate = newValue.plusDays(30);
                toDatePicker.setValue(newValue.plusDays(1));

                toDatePicker.setDayCellFactory(picker -> new DateCell() {
                    @Override
                    public void updateItem(LocalDate item, boolean empty) {
                        super.updateItem(item, empty);
                        if (empty || item == null) {
                            setDisable(false);
                            setStyle("");
                            return;
                        }

                        // Disables and colours those dates that are either before or 30 days after fromToDate
                        if (item.isBefore(newValue) || item.isAfter(maxDate)) {
                            setDisable(true);
                            setStyle("-fx-background-color: #ffcccc;");
                        }

                        // Enables the rest
                        else {
                            setDisable(false);
                            setStyle("");
                        }
                    }
                });
            }
        });

        // Add a listener to `toDatePicker` to adjust discount limits based on date range
        toDatePicker.valueProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue != null && fromDatePicker.getValue() != null) {
                long daysBetween = ChronoUnit.DAYS.between(fromDatePicker.getValue(), newValue);
                refreshNewMaxDiscount(daysBetween);
            }
        });
    }

    private void initializeDiscountSpinner()
    {
        discountSpinner.setEditable(true);
        SpinnerValueFactory.IntegerSpinnerValueFactory valueFactory = new SpinnerValueFactory.IntegerSpinnerValueFactory(0, 50, 0);
        discountSpinner.setValueFactory(valueFactory);

        // Initializers
        initializeSpinnerIsFocusedListener(valueFactory);
        TextFormatter<Integer> formatter = initializeSpinnerChangeListener(valueFactory);
        discountSpinner.getEditor().setTextFormatter(formatter);
    }

    private TextFormatter<Integer> initializeSpinnerChangeListener(SpinnerValueFactory.IntegerSpinnerValueFactory valueFactory)
    {
        // TextFormatter to enforce numeric input within bounds
        return new TextFormatter<>(
                change ->
                {
                    String newText = change.getControlNewText();
                    if (newText.isEmpty())
                        return change; // Accept change to allow deletion

                    if (newText.matches("\\d*"))
                    {
                        try
                        {
                            int value = Integer.parseInt(newText);
                            if (value >= valueFactory.getMin() &&
                                    value <= valueFactory.getMax())
                                return change; // Valid change

                        }
                        catch (NumberFormatException ignored)
                        {
                        }
                    }
                    return null;
                }
        );
    }

    private void initializeSpinnerIsFocusedListener(SpinnerValueFactory.IntegerSpinnerValueFactory valueFactory)
    {
        discountSpinner.focusedProperty().addListener((obs, wasFocused, isFocused) -> {
            if (!isFocused)
            {
                String text = discountSpinner.getEditor().getText();
                if (text.isEmpty())
                    discountSpinner.getValueFactory().setValue(valueFactory.getMin());
                else
                    try
                    {
                        discountSpinner.getValueFactory().setValue(Integer.parseInt(text));
                    }
                    catch (NumberFormatException e)
                    {
                        discountSpinner.getValueFactory().setValue(valueFactory.getMin());
                    }

            }
        });
    }

    private void refreshNewMaxDiscount(long daysBetween)
    {
        int maxDiscount;

        if (daysBetween > 15)
            maxDiscount = 10;
        else if (daysBetween > 7)
            maxDiscount = 15;
        else if (daysBetween > 3)
            maxDiscount = 20;
        else if (daysBetween > 1)
            maxDiscount = 30;
        else if (daysBetween == 1)
            maxDiscount = 50;
        else
            maxDiscount = 0; // Default case, which should not happen, but ensures safety


        // Update the spinner value factory with the new max discount
        SpinnerValueFactory.IntegerSpinnerValueFactory valueFactory =
                (SpinnerValueFactory.IntegerSpinnerValueFactory) discountSpinner.getValueFactory();
        valueFactory.setMax(maxDiscount);


        if (discountSpinner.getValue() > maxDiscount)
            discountSpinner.getValueFactory().setValue(maxDiscount);
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
                sellerProductBox.getItems().add(sellerProduct);
        }
    }

    private void handleAddAction() {
        SellerProductEntity sellerProduct = sellerProductBox.getValue();
        if (sellerProduct == null)
        {
            Utils.showError("Invalid product. Please select a product.");
            return;
        }
        sellerProduct.setOfferStartDate(fromDatePicker.getValue());
        sellerProduct.setOfferEndDate(toDatePicker.getValue());
        int discount = discountSpinner.getValue();
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
