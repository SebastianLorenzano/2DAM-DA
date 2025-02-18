package com.sl2425.da.sellersapp.restapi.model.dto;

import com.sl2425.da.sellersapp.Model.Entities.SellerProductEntity;
import com.sun.istack.NotNull;
import jakarta.validation.constraints.*;

import java.math.BigDecimal;
import java.time.LocalDate;

public class SellerProductDTO
{
    private Integer id;
    @NotBlank(message = "CIF cannot be blank")

    @Size(max = 9, message = "CIF must be 9 characters long")
    private String cif;

    @Positive(message = "Product ID must be a positive number")
    @NotNull
    private int productId;

    @NotNull
    @DecimalMin(value = "0.0", inclusive = true, message = "Price must be greater or equal than 0")
    @Max(value = 999999999, message = "Price must be less than 1.000.000.000")
    private BigDecimal price;

    @Min(value = 1, message = "Discount percentage must be greater than 0")
    @Max(value = 50, message = "Price must be less than 1.000.000.000") // 50% discount
    private int discount;

    @FutureOrPresent(message = "Offer start date must be today or in the future")
    private LocalDate offerStartDate;

    @FutureOrPresent(message = "Offer end date must be today or in the future")
    private LocalDate offerEndDate;

    @PositiveOrZero(message = "Stock must be equal or greater than 0")
    @Max(value = 1000000000, message = "Stock must be less than 1.000.000.000")
    private Integer stock;

    public SellerProductDTO()
    {
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getCif() {
        return cif;
    }

    public void setCif(String cif) {
        this.cif = cif;
    }

    public int getProductId() {
        return productId;
    }

    public void setProductId(int productId) {
        this.productId = productId;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    public int getDiscount()
    {
        return discount;
    }

    public void setDiscount(int discount) {
        this.discount = discount;
    }

    public LocalDate getOfferStartDate() {
        return offerStartDate;
    }

    public void setOfferStartDate(LocalDate offerStartDate) {
        this.offerStartDate = offerStartDate;
    }

    public LocalDate getOfferEndDate() {
        return offerEndDate;
    }

    public void setOfferEndDate(LocalDate offerEndDate) {
        this.offerEndDate = offerEndDate;
    }

    public Integer getStock() {
        return stock;
    }

    public void setStock(Integer stock) {
        this.stock = stock;
    }
}
