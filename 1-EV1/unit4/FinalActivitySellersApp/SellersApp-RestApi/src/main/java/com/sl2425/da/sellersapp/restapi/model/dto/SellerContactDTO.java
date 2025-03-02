package com.sl2425.da.sellersapp.restapi.model.dto;

import com.sl2425.da.sellersapp.restapi.model.Entities.SellerEntity;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public class SellerContactDTO
{
    private Integer id;

    private String cif;

    private String name;

    private String phone;

    private String email;

    SellerContactDTO()
    {

    }

    public static SellerContactDTO toDTO(SellerEntity sellerEntity)
    {
        var result = new SellerContactDTO();

        if (sellerEntity == null)
            return null;

        result.setId(sellerEntity.getId());
        result.setCif(sellerEntity.getCif());
        result.setName(sellerEntity.getName());

        result.setCif(sellerEntity.getCif());
        if (sellerEntity.getEmail() != null)
            result.setEmail(sellerEntity.getEmail());
        else
            result.setEmail("N/A");

        if (sellerEntity.getPhone() != null)
            result.setPhone(sellerEntity.getPhone());
        else
            result.setPhone("N/A");

        return result;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getCif() {
        return cif;
    }

    public void setCif(String cif) {
        this.cif = cif;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
