package com.sl2425.da.sellersapp.restapi.model.dto;

public class SellerDTO
{
    private String cif;
    private String password;

    public SellerDTO()
    {
    }

    public SellerDTO(String cif, String password)
    {
        this.cif = cif;
        this.password = password;
    }

    public String getCif()
    {
        return cif;
    }

    public void setCif(String cif)
    {
        this.cif = cif;
    }

    public String getPassword()
    {
        return password;
    }

    public void setPassword(String password)
    {
        this.password = password;
    }
}
