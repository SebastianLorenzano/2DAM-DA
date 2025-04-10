package com.sl2425.da.sellersapp.Model.Entities;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;

import java.util.LinkedHashSet;
import java.util.Set;

@Entity
@Table(name = "categories")
public class CategoryEntity
{
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "categories_id_gen")
    @SequenceGenerator(name = "categories_id_gen", sequenceName = "categories_category_id_seq", allocationSize = 1)
    @Column(name = "category_id", nullable = false)
    private Integer id;

    @Column(name = "category_name", nullable = false, length = 50)
    private String categoryName;

    @OneToMany(mappedBy = "category")
    @JsonIgnoreProperties("category")
    private Set<com.sl2425.da.sellersapp.Model.Entities.ProductEntity> products = new LinkedHashSet<>();

    public Integer getId()
    {
        return id;
    }

    public void setId(Integer id)
    {
        this.id = id;
    }

    public String getCategoryName()
    {
        return categoryName;
    }

    public void setCategoryName(String categoryName)
    {
        this.categoryName = categoryName;
    }

    public Set<com.sl2425.da.sellersapp.Model.Entities.ProductEntity> getProducts()
    {
        return products;
    }

    public void setProducts(Set<com.sl2425.da.sellersapp.Model.Entities.ProductEntity> products)
    {
        this.products = products;
    }

}