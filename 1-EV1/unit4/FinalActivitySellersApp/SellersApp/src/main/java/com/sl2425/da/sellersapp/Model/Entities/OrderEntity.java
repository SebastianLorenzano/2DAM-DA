package com.sl2425.da.sellersapp.Model.Entities;

import jakarta.persistence.*;

import java.time.LocalDate;
import java.util.LinkedHashSet;
import java.util.Set;

@Entity
@Table(name = "orders")
public class OrderEntity
{
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "orders_id_gen")
    @SequenceGenerator(name = "orders_id_gen", sequenceName = "orders_order_id_seq", allocationSize = 1)
    @Column(name = "order_id", nullable = false)
    private Integer id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private com.sl2425.da.sellersapp.Controllers.UserEntity user;

    @Column(name = "order_date")
    private LocalDate orderDate;

    @OneToMany(mappedBy = "order")
    private Set<com.sl2425.da.sellersapp.Controllers.OrderDetailEntity> orderDetails = new LinkedHashSet<>();

    public Integer getId()
    {
        return id;
    }

    public void setId(Integer id)
    {
        this.id = id;
    }

    public com.sl2425.da.sellersapp.Controllers.UserEntity getUser()
    {
        return user;
    }

    public void setUser(com.sl2425.da.sellersapp.Controllers.UserEntity user)
    {
        this.user = user;
    }

    public LocalDate getOrderDate()
    {
        return orderDate;
    }

    public void setOrderDate(LocalDate orderDate)
    {
        this.orderDate = orderDate;
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