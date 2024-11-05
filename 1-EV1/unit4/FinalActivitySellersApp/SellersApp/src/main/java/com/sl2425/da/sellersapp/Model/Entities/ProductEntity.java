package com.sl2425.da.sellersapp.Model.Entities;

import jakarta.persistence.*;

import java.util.LinkedHashSet;
import java.util.Set;

@Entity
@Table(name = "products")
public class ProductEntity
{
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "products_id_gen")
    @SequenceGenerator(name = "products_id_gen", sequenceName = "products_product_id_seq", allocationSize = 1)
    @Column(name = "product_id", nullable = false)
    private Integer id;

    @Column(name = "product_name", nullable = false, length = 100)
    private String productName;

    @Lob
    @Column(name = "description")
    private String description;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "category_id")
    private com.sl2425.da.sellersapp.Controllers.CategoryEntity category;

    @Column(name = "active", nullable = false)
    private Boolean active = false;

    @OneToMany(mappedBy = "product")
    private Set<com.sl2425.da.sellersapp.Controllers.SellerProductEntity> sellerProducts = new LinkedHashSet<>();

    public Integer getId()
    {
        return id;
    }

    public void setId(Integer id)
    {
        this.id = id;
    }

    public String getProductName()
    {
        return productName;
    }

    public void setProductName(String productName)
    {
        this.productName = productName;
    }

    public String getDescription()
    {
        return description;
    }

    public void setDescription(String description)
    {
        this.description = description;
    }

    public com.sl2425.da.sellersapp.Controllers.CategoryEntity getCategory()
    {
        return category;
    }

    public void setCategory(com.sl2425.da.sellersapp.Controllers.CategoryEntity category)
    {
        this.category = category;
    }

    public Boolean getActive()
    {
        return active;
    }

    public void setActive(Boolean active)
    {
        this.active = active;
    }

    public Set<com.sl2425.da.sellersapp.Controllers.SellerProductEntity> getSellerProducts()
    {
        return sellerProducts;
    }

    public void setSellerProducts(Set<com.sl2425.da.sellersapp.Controllers.SellerProductEntity> sellerProducts)
    {
        this.sellerProducts = sellerProducts;
    }

}