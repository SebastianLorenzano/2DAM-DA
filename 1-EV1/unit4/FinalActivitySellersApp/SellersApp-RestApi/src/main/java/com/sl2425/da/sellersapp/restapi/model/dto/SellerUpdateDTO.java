package com.sl2425.da.sellersapp.restapi.model.dto;

import com.sl2425.da.sellersapp.Model.Entities.SellerEntity;
import jakarta.validation.constraints.*;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.Objects;


public class SellerUpdateDTO
{
    private Integer id;

    @NotBlank(message = "CIF cannot be blank")
    @Size(max = 9, message = "CIF must be 9 characters long")
    private String cif;

    @NotBlank(message = "Name cannot be blank")
    @Size(max = 100, message = "Name must be at most 100 characters long")
    @Pattern(regexp ="^[A-Za-zÀ-ÖØ-öø-ÿ0-9 .,*&@#$%^()!?'-]{1,100}$", message = "Name can only contain letters, numbers, and special characters")
    private String name;

    @Size(max = 100, message = "Business name must be at most 150 characters long")
    @Pattern(regexp ="^$|^[A-Za-zÀ-ÖØ-öø-ÿ0-9 .,*&@#$%^()!?'-]{1,100}$", message = "Business name can only contain letters, numbers, and special characters")
    private String businessName;

    @Pattern(
            regexp = "^$|^[+\\-\\d\\s]{1,15}$",
            message = "Phone number is invalid")
    @Size(max = 15, message = "Phone number must be up to 15 characters")
    private String phone;

    @Email(message = "Invalid email format")
    @Size(max = 254, message = "Email must be at most 254 characters long")
    private String email;

    @Pattern(regexp = "^$|^(https?:\\/\\/)" +                    // Protocol (http/https)
            "((([a-zA-Z0-9-]+\\.)+[a-zA-Z]{2,})|" +              // Domain (e.g., example.com)
            "((\\d{1,3}\\.){3}\\d{1,3}))" +                      // OR IP (e.g., 192.168.1.1)
            "(\\:\\d+)?(\\/[^\\s]*)?" +                          // Optional port and path
            "(\\?[\\w=&%-]*)?" +                                 // Optional query params
            "(\\#[\\w-]*)?$", message = "URL is invalid")        // Optional fragment
    private String url;

    private String oldPassword;

    @Pattern(
            regexp = "^$|^(?=.*[A-Z])(?=.*[a-z])(?=.*[0-9]).{8,}$",
            message = "Password must be at least 8 characters long, containing one uppercase letter, one lowercase letter, and one number")
    private String newPassword;
    private String confirmNewPassword;

    public SellerUpdateDTO()
    {
    }

    public SellerUpdateDTO(SellerEntity sellerEntity)
    {
        this.cif = sellerEntity.getCif();
        this.name = sellerEntity.getName();
        this.businessName = sellerEntity.getBusinessName();
        this.phone = sellerEntity.getPhone();
        this.email = sellerEntity.getEmail();
        this.url = sellerEntity.getUrl();
    }

    public SellerEntity toSellerEntity(SellerEntity oldSeller, PasswordEncoder passwordEncoder)
    {
        SellerEntity sellerEntity = new SellerEntity();
        sellerEntity.setId(oldSeller.getId());
        sellerEntity.setCif(cif);
        sellerEntity.setName(name);
        sellerEntity.setBusinessName(businessName);
        sellerEntity.setPhone(phone);
        sellerEntity.setEmail(email);
        sellerEntity.setUrl(url);
        sellerEntity.setPassword(oldSeller.getPassword());
        sellerEntity.setPlainPassword(oldSeller.getPlainPassword()); // Remove on production
        if (userIsChangingPasswordCorrectly())
            sellerEntity.setPassword(passwordEncoder.encode(newPassword));

        sellerEntity.setPro(oldSeller.getPro());
        return sellerEntity;
    }

    public boolean userIsChangingPasswordCorrectly()
    {
        return wasPasswordChanged() && doesNewPasswordsMatch();
    }

    public boolean wasPasswordChanged()
    {
        return ((newPassword != null && !newPassword.isEmpty()) ||
                (confirmNewPassword != null && !confirmNewPassword.isEmpty()));
    }

    public boolean doesNewPasswordsMatch()
    {
        return Objects.equals(newPassword, confirmNewPassword);
    }


    public Integer getId()
    {
        return id;
    }

    public void setId(Integer id)
    {
        this.id = id;
    }

    public String getOldPassword()
    {
        return oldPassword;
    }

    public void setOldPassword(String oldPassword)
    {
        this.oldPassword = oldPassword;
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

    public String getNewPassword()
    {
        return newPassword;
    }

    public void setNewPassword(String newPassword)
    {
        this.newPassword = newPassword;
    }

    public String getConfirmNewPassword()
    {
        return confirmNewPassword;
    }

    public void setConfirmNewPassword(String confirmNewPassword)
    {
        this.confirmNewPassword = confirmNewPassword;
    }

    public String getUrl()
    {
        return url;
    }

    public void setUrl(String url)
    {
        this.url = url;
    }
}
