package com.sl2425.da.sellersapp.Model.Entities;

import jakarta.persistence.*;

import java.math.BigDecimal;
import java.util.LinkedHashSet;
import java.util.Set;

@Entity
@Table(name = "order_details")
public class OrderDetailEntity
{
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "order_details_id_gen")
    @SequenceGenerator(name = "order_details_id_gen", sequenceName = "order_details_order_detail_id_seq", allocationSize = 1)
    @Column(name = "order_detail_id", nullable = false)
    private Integer id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "order_id")
    private com.sl2425.da.sellersapp.Model.Entities.OrderEntity order;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "seller_product_id")
    private com.sl2425.da.sellersapp.Model.Entities.SellerProductEntity sellerProduct;

    @Column(name = "quantity", nullable = false)
    private Integer quantity;

    @Column(name = "subtotal_price", precision = 10, scale = 2)
    private BigDecimal subtotalPrice;

    @OneToMany(mappedBy = "orderDetail")
    private Set<com.sl2425.da.sellersapp.Model.Entities.ReviewEntity> reviews = new LinkedHashSet<>();

    public Integer getId()
    {
        return id;
    }

    public void setId(Integer id)
    {
        this.id = id;
    }

    public com.sl2425.da.sellersapp.Model.Entities.OrderEntity getOrder()
    {
        return order;
    }

    public void setOrder(com.sl2425.da.sellersapp.Model.Entities.OrderEntity order)
    {
        this.order = order;
    }

    public com.sl2425.da.sellersapp.Model.Entities.SellerProductEntity getSellerProduct()
    {
        return sellerProduct;
    }

    public void setSellerProduct(com.sl2425.da.sellersapp.Model.Entities.SellerProductEntity sellerProduct)
    {
        this.sellerProduct = sellerProduct;
    }

    public Integer getQuantity()
    {
        return quantity;
    }

    public void setQuantity(Integer quantity)
    {
        this.quantity = quantity;
    }

    public BigDecimal getSubtotalPrice()
    {
        return subtotalPrice;
    }

    public void setSubtotalPrice(BigDecimal subtotalPrice)
    {
        this.subtotalPrice = subtotalPrice;
    }

    public Set<com.sl2425.da.sellersapp.Model.Entities.ReviewEntity> getReviews()
    {
        return reviews;
    }

    public void setReviews(Set<com.sl2425.da.sellersapp.Model.Entities.ReviewEntity> reviews)
    {
        this.reviews = reviews;
    }

}