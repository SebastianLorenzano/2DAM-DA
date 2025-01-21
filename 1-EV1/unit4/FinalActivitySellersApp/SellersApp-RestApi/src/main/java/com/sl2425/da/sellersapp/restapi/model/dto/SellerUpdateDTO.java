package com.sl2425.da.sellersapp.restapi.model.dto;

import com.sl2425.da.sellersapp.Model.Entities.SellerEntity;

public class SellerUpdateDTO
{
    private String cif;
    private String name;
    private String businessName;
    private String phone;
    private String email;
    private String url;
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
        this.url = sellerEntity.getURL();
    }

    public static SellerUpdateDTO fromSellerEntity(SellerEntity sellerEntity)
    {
        return new SellerUpdateDTO(sellerEntity);
    }

    public SellerEntity toSellerEntity()
    {
        SellerEntity sellerEntity = new SellerEntity();
        sellerEntity.setCif(cif);
        sellerEntity.setName(name);
        sellerEntity.setBusinessName(businessName);
        sellerEntity.setPhone(phone);
        sellerEntity.setEmail(email);
        sellerEntity.setURL(url);
        if (wasPasswordChanged() && isPasswordConfirmed())
            sellerEntity.setPassword(newPassword);
        return sellerEntity;
    }

    public boolean wasPasswordChanged()
    {
        return (newPassword != null && !newPassword.isEmpty()) ||
                (confirmNewPassword != null && !confirmNewPassword.isEmpty());
    }

    public boolean isPasswordConfirmed()
    {
        return newPassword.equals(confirmNewPassword);
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
