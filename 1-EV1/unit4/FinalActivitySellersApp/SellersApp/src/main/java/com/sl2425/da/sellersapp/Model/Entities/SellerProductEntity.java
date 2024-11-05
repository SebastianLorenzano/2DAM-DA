package com.sl2425.da.sellersapp.Model.Entities;

import jakarta.persistence.*;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.LinkedHashSet;
import java.util.Set;

@Entity
@Table(name = "seller_products")
public class SellerProductEntity
{
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "seller_products_id_gen")
    @SequenceGenerator(name = "seller_products_id_gen", sequenceName = "seller_products_seller_product_id_seq", allocationSize = 1)
    @Column(name = "seller_product_id", nullable = false)
    private Integer id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "seller_id")
    private com.sl2425.da.sellersapp.Controllers.SellerEntity seller;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "product_id")
    private com.sl2425.da.sellersapp.Controllers.ProductEntity product;

    @Column(name = "price", nullable = false, precision = 10, scale = 2)
    private BigDecimal price;

    @Column(name = "offer_price", precision = 10, scale = 2)
    private BigDecimal offerPrice;

    @Column(name = "offer_start_date")
    private LocalDate offerStartDate;

    @Column(name = "offer_end_date")
    private LocalDate offerEndDate;

    @Column(name = "stock", nullable = false)
    private Integer stock;

    @OneToMany(mappedBy = "sellerProduct")
    private Set<com.sl2425.da.sellersapp.Controllers.OrderDetailEntity> orderDetails = new LinkedHashSet<>();

    public Integer getId()
    {
        return id;
    }

    public void setId(Integer id)
    {
        this.id = id;
    }

    public com.sl2425.da.sellersapp.Controllers.SellerEntity getSeller()
    {
        return seller;
    }

    public void setSeller(com.sl2425.da.sellersapp.Controllers.SellerEntity seller)
    {
        this.seller = seller;
    }

    public com.sl2425.da.sellersapp.Controllers.ProductEntity getProduct()
    {
        return product;
    }

    public void setProduct(com.sl2425.da.sellersapp.Controllers.ProductEntity product)
    {
        this.product = product;
    }

    public BigDecimal getPrice()
    {
        return price;
    }

    public void setPrice(BigDecimal price)
    {
        this.price = price;
    }

    public BigDecimal getOfferPrice()
    {
        return offerPrice;
    }

    public void setOfferPrice(BigDecimal offerPrice)
    {
        this.offerPrice = offerPrice;
    }

    public LocalDate getOfferStartDate()
    {
        return offerStartDate;
    }

    public void setOfferStartDate(LocalDate offerStartDate)
    {
        this.offerStartDate = offerStartDate;
    }

    public LocalDate getOfferEndDate()
    {
        return offerEndDate;
    }

    public void setOfferEndDate(LocalDate offerEndDate)
    {
        this.offerEndDate = offerEndDate;
    }

    public Integer getStock()
    {
        return stock;
    }

    public void setStock(Integer stock)
    {
        this.stock = stock;
    }

    public Set<com.sl2425.da.sellersapp.Controllers.OrderDetailEntity> getOrderDetails()
    {
        return orderDetails;
    }

    public void setOrderDetails(Set<com.sl2425.da.sellersapp.Controllers.OrderDetailEntity> orderDetails)
    {
        this.orderDetails = orderDetails;
    }

}