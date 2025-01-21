package com.sl2425.da.sellersapp.Model.Entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import org.hibernate.annotations.ColumnDefault;

import java.io.Serializable;
import java.util.LinkedHashSet;
import java.util.Set;

@Entity
@Table(name = "sellers")
public class SellerEntity implements Serializable
{
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sellers_id_gen")
    @SequenceGenerator(name = "sellers_id_gen", sequenceName = "sellers_seller_id_seq", allocationSize = 1)
    @Column(name = "seller_id", nullable = false)
    private Integer id;

    @Column(name = "cif", nullable = false, length = 20)
    private String cif;

    @Column(name = "name", nullable = false, length = 100)
    private String name;

    @Column(name = "business_name", length = 100)
    private String businessName;

    @Column(name = "phone", length = 15)
    private String phone;

    @Column(name = "email", length = 90)
    private String email;

    @Column(name = "plain_password", nullable = false, length = 50)
    private String plainPassword;

    @Column(name = "password", nullable = false, length = 100)
    private String password;

    @OneToMany(mappedBy = "seller")
    @JsonIgnore
    private Set<SellerProductEntity> sellerProducts = new LinkedHashSet<>();

    @Column(name = "url")
    private String url;

    @ColumnDefault("false")
    @Column(name = "pro", nullable = false)
    private Boolean pro = false;

    public Boolean getPro()
    {
        return pro;
    }

    public void setPro(Boolean pro)
    {
        this.pro = pro;
    }

    public String getUrl()
    {
        return url;
    }

    public void setUrl(String url)
    {
        this.url = url;
    }

    public Integer getId()
    {
        return id;
    }

    public void setId(Integer id)
    {
        this.id = id;
    }

    public String getCif()
    {
        return cif;
    }

    public void setCif(String cif)
    {
        this.cif = cif;
    }

    public String getName()
    {
        return name;
    }

    public void setName(String name)
    {
        this.name = name;
    }

    public String getBusinessName()
    {
        return businessName;
    }

    public void setBusinessName(String businessName)
    {
        this.businessName = businessName;
    }

    public String getPhone()
    {
        return phone;
    }

    public void setPhone(String phone)
    {
        this.phone = phone;
    }

    public String getEmail()
    {
        return email;
    }

    public void setEmail(String email)
    {
        this.email = email;
    }

    public String getPlainPassword()
    {
        return plainPassword;
    }

    public void setPlainPassword(String plainPassword)
    {
        this.plainPassword = plainPassword;
    }

    public String getPassword()
    {
        return password;
    }

    public void setPassword(String password)
    {
        this.password = password;
    }

    public Set<SellerProductEntity> getSellerProducts()
    {
        return sellerProducts;
    }

    public void setSellerProducts(Set<SellerProductEntity> sellerProducts)
    {
        this.sellerProducts = sellerProducts;
    }

    public String getURL()
    {
        return url;
    }

    public void setURL(String url)
    {
        this.url = url;
    }

}