package com.sl2425.da.sellersapp.restapi.model.dto;

public class CifAndCategoryDTO
{
    private String cif;
    private Integer categoryId;

    public String getCif()
    {
        return cif;
    }

    public void setCif(String cif)
    {
        this.cif = cif;
    }

    public Integer getCategoryId()
    {
        return categoryId;
    }

    public void setCategoryId(Integer categoryId)
    {
        this.categoryId = categoryId;
    }
}
