package com.sl2425.da.sellersapp.restapi.model.dto;

import com.sl2425.da.sellersapp.Model.Entities.ProductEntity;
import com.sl2425.da.sellersapp.Model.Entities.SellerEntity;
import com.sl2425.da.sellersapp.Model.Entities.SellerProductEntity;

import java.math.BigDecimal;
import java.time.LocalDate;

public class SellerProductDTO
{
    private Integer id;
    private SellerLoginDTO sellerDTO;
    private ProductEntity product;
    private BigDecimal price;
    private BigDecimal offerPrice;
    private LocalDate offerStartDate;
    private LocalDate offerEndDate;
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
}
