package com.sl2425.da.sellersapp.restapi.model.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;

public class SellerLoginDTO
{
    @NotBlank(message = "CIF cannot be blank")
    @Size(max = 9, message = "CIF must be 9 characters long")
    private String cif;

    @Pattern(
            regexp = "^$|^(?=.*[A-Z])(?=.*[a-z])(?=.*[0-9]).{8,}$",
            message = "Password must be at least 8 characters long, containing one uppercase letter, one lowercase letter, and one number")
    private String password;

    public SellerLoginDTO()
    {
    }

    public SellerLoginDTO(String cif, String password)
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
