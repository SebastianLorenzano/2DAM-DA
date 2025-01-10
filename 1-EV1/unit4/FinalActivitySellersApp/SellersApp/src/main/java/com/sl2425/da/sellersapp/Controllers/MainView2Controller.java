package com.sl2425.da.sellersapp.Controllers;

import com.sl2425.da.sellersapp.Model.Entities.CategoryEntity;
import com.sl2425.da.sellersapp.Model.Entities.SellerEntity;
import com.sl2425.da.sellersapp.Model.Entities.ProductEntity;
import com.sl2425.da.sellersapp.Model.GenericAppController;
import com.sl2425.da.sellersapp.Model.Utils;
import com.sl2425.da.sellersapp.Model.DatabaseOps;
import com.sl2425.da.sellersapp.Model.LogProperties;
import javafx.fxml.FXML;
import javafx.scene.control.*;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

public class MainView2Controller extends GenericAppController
{

    @FXML
    private ComboBox<CategoryEntity> categoryBox;

    @FXML
    private ComboBox<ProductEntity> productBox;

    @FXML
    private Spinner<Integer> stockSpinner;

    @FXML
    private TextField priceTextField;

    @FXML
    private Button addButton;

    private static SellerEntity seller = null;

    @FXML
    private void initialize()
    {
        seller = Utils.currentSeller;
        if (seller == null)
        {
            LogProperties.logger.severe("Current Seller is null.");
            Utils.showError("Error: Current Seller is Null.");
        }
        initializeCategoriesBox();
        initializeProductsBox();
        initializeStockSpinner();

        // Set event handler for add button
        categoryBox.setOnAction(event -> handleCategoriesBoxAction());
        addButton.setOnAction(event -> handleAddAction());
    }

    private void initializeCategoriesBox()
    {
        List<CategoryEntity> categories = database.SelectCategories();
        if (categories == null)
        {
            LogProperties.logger.severe("Categories are null");
            categories = new ArrayList<>();
        }
        categoryBox.getItems().addAll(categories);

        categoryBox.setPromptText("Select Category");
        // Set the converter to display category names instead of the object reference
        categoryBox.setCellFactory(lv -> new ListCell<>() {
            @Override
            protected void updateItem(CategoryEntity category, boolean empty) {
                super.updateItem(category, empty);
                setText(empty ? null : category.getCategoryName());         // Displays category name
            }                                                               // instead of object reference
        });
        categoryBox.setButtonCell(new ListCell<>() {
            @Override
            protected void updateItem(CategoryEntity category, boolean empty) {
                super.updateItem(category, empty);
                setText(empty ? null : category.getCategoryName());     // Displays the selected category name
            }                                                           // instead of object reference
        });
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

    private void initializeStockSpinner()
    {
        SpinnerValueFactory.IntegerSpinnerValueFactory valueFactory =
                new SpinnerValueFactory.IntegerSpinnerValueFactory(0, 1000, 0);
        stockSpinner.setValueFactory(valueFactory);

        TextField spinnerEditor = stockSpinner.getEditor();
        spinnerEditor.textProperty().addListener((observable, oldValue, newValue) -> {
            try
            {
                int value = Integer.parseInt(newValue);
                if (value < valueFactory.getMin())

                    spinnerEditor.setText(String.valueOf(valueFactory.getMin()));
                else if (value > valueFactory.getMax())
                    spinnerEditor.setText(String.valueOf(valueFactory.getMax()));
            }
            catch (NumberFormatException e)
            {
                spinnerEditor.setText(oldValue);
            }
        });

        stockSpinner.setEditable(true);
    }

    private void handleCategoriesBoxAction()
    {
        CategoryEntity selectedCategory = categoryBox.getSelectionModel().getSelectedItem();
        List<ProductEntity> products = database.SelectAvailableProducts(seller, selectedCategory);
        productBox.getItems().clear();
        if (products != null)
            productBox.getItems().addAll(products);
    }

    private void handleAddAction()
    {
        CategoryEntity category = categoryBox.getValue();
        ProductEntity product = productBox.getValue();
        int stock = stockSpinner.getValue();
        String priceString = priceTextField.getText();

        if (priceString.length() > 10)
        {
            Utils.showError("Price is too big. Please enter a valid price.");
            return;
        }

        if (category == null || product == null || priceString.isEmpty())
        {
            Utils.showError("Please fill in all fields before adding the product.");
            return;
        }

        try
        {
            BigDecimal price = new BigDecimal(priceString);
            if (database.InsertSellerProduct(seller, product, price, stock))
            {
                Utils.showConfirmation("Your product added successfully: " + product.getProductName() + " in category "
                        + category.getCategoryName() + " with stock " + stock + " and price " + price);
                handleCategoriesBoxAction();
            }
            else
                Utils.showError("Error adding product. Please try again.");

        } catch (NumberFormatException e) {
            Utils.showError("Invalid price. Please enter a valid number.");
        }
    }
}
