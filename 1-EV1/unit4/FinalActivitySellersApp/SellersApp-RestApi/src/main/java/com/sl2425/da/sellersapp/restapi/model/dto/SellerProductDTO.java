package com.sl2425.da.sellersapp.restapi.model.dto;

import com.sl2425.da.sellersapp.Model.Entities.ProductEntity;
import com.sl2425.da.sellersapp.Model.Entities.SellerEntity;
import com.sl2425.da.sellersapp.Model.Entities.SellerProductEntity;
import com.sun.istack.NotNull;
import jakarta.validation.constraints.*;

import java.math.BigDecimal;
import java.time.LocalDate;

public class SellerProductDTO
{
    private Integer id;
    @NotNull
    @NotBlank (message = "Seller is required")
    private SellerLoginDTO sellerDTO;

    @Positive(message = "Product ID must be a positive number")
    private int productId;

    @NotNull
    @DecimalMin(value = "0.0", inclusive = true, message = "Price must be greater or equal than 0")
    private BigDecimal price;

    @DecimalMin(value = "0.0", inclusive = true, message = "Offer price cannot be negative")
    private BigDecimal offerPrice;

    @FutureOrPresent(message = "Offer start date must be today or in the future")
    private LocalDate offerStartDate;

    @FutureOrPresent(message = "Offer end date must be today or in the future")
    private LocalDate offerEndDate;

    @PositiveOrZero(message = "Stock must be equal or greater than 0")
    private Integer stock;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public SellerLoginDTO getSellerDTO() {
        return sellerDTO;
    }

    public void setSellerDTO(SellerLoginDTO sellerLoginDTO) {
        this.sellerDTO = sellerLoginDTO;
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
