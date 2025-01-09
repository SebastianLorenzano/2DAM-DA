package com.sl2425.da.sellersapp.restapi.model.dto;

import com.sl2425.da.sellersapp.Model.Entities.ProductEntity;

import java.math.BigDecimal;

public class SellerProductRequestDTO
{
    private SellerDTO sellerDTO;
    private ProductEntity product;
    private BigDecimal price;
    private int stock;

    // Getters and setters
    public SellerDTO getSellerDTO() {
        return sellerDTO;
    }

    public void setSellerDTO(SellerDTO sellerDTO) {
        this.sellerDTO = sellerDTO;
    }

    public ProductEntity getProduct() {
        return product;
    }

    public void setProduct(ProductEntity product) {
        this.product = product;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    public int getStock() {
        return stock;
    }

    public void setStock(int stock) {
        this.stock = stock;
    }
}
