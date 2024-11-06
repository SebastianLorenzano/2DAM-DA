package com.sl2425.da.sellersapp.Model.Entities;

import jakarta.persistence.*;

import java.time.LocalDate;
import java.util.LinkedHashSet;
import java.util.Set;

@Entity
@Table(name = "users")
public class UserEntity
{
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "users_id_gen")
    @SequenceGenerator(name = "users_id_gen", sequenceName = "users_user_id_seq", allocationSize = 1)
    @Column(name = "user_id", nullable = false)
    private Integer id;

    @Column(name = "username", nullable = false, length = 50)
    private String username;

    @Column(name = "email", nullable = false, length = 100)
    private String email;

    @Column(name = "plain_password", nullable = false, length = 50)
    private String plainPassword;

    @Column(name = "registration_date")
    private LocalDate registrationDate;

    @Column(name = "password", length = 100)
    private String password;

    @OneToMany(mappedBy = "user")
    private Set<com.sl2425.da.sellersapp.Model.Entities.OrderEntity> orders = new LinkedHashSet<>();

    public Integer getId()
    {
        return id;
    }

    public void setId(Integer id)
    {
        this.id = id;
    }

    public String getUsername()
    {
        return username;
    }

    public void setUsername(String username)
    {
        this.username = username;
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

    public LocalDate getRegistrationDate()
    {
        return registrationDate;
    }

    public void setRegistrationDate(LocalDate registrationDate)
    {
        this.registrationDate = registrationDate;
    }

    public String getPassword()
    {
        return password;
    }

    public void setPassword(String password)
    {
        this.password = password;
    }

    public Set<com.sl2425.da.sellersapp.Model.Entities.OrderEntity> getOrders()
    {
        return orders;
    }

    public void setOrders(Set<com.sl2425.da.sellersapp.Model.Entities.OrderEntity> orders)
    {
        this.orders = orders;
    }

}