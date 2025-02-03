package com.sl2425.da.sellersapp.restapi.model.dto;

public class ProductDTO
{
    private String cif;
    private Integer categoryId;
    private boolean remainingProducts;

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

    public boolean getRemainingProducts() {
        return remainingProducts;
    }

    public void setRemainingProducts(boolean remainingProducts) {
        this.remainingProducts = remainingProducts;
    }
}
