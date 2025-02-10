package com.sl2425.da.sellersapp.restapi.model.dto;

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

    @DecimalMin(value = "0.0", inclusive = true, message = "Offer price cannot be negative")
    @Max(value = 999999999, message = "Price must be less than 1.000.000.000")
    private BigDecimal offerPrice;

    @FutureOrPresent(message = "Offer start date must be today or in the future")
    private LocalDate offerStartDate;

    @FutureOrPresent(message = "Offer end date must be today or in the future")
    private LocalDate offerEndDate;

    @PositiveOrZero(message = "Stock must be equal or greater than 0")
    @Max(value = 1000000000, message = "Stock must be less than 1.000.000.000")
    private Integer stock;

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

    public BigDecimal getOfferPrice() {
        return offerPrice;
    }

    public void setOfferPrice(BigDecimal offerPrice) {
        this.offerPrice = offerPrice;
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

    /*
    public SellerProductEntity toEntity(SellerEntity seller)
    {
        var result =  new SellerProductEntity();
        result.setId(this.id);
        result.setSeller(seller);
        result.setProduct(this.product);
        result.setPrice(this.price);
        result.setOfferPrice(this.offerPrice);
        result.setOfferStartDate(this.offerStartDate);
        result.setOfferEndDate(this.offerEndDate);
        result.setStock(this.stock);
        return result;
    }
    */
}
