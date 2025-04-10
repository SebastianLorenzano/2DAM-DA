package com.sl2425.da.sellersapp.Model.Database;

import com.sl2425.da.sellersapp.Model.Entities.CategoryEntity;
import com.sl2425.da.sellersapp.Model.Entities.ProductEntity;
import com.sl2425.da.sellersapp.Model.Entities.SellerEntity;
import com.sl2425.da.sellersapp.Model.Entities.SellerProductEntity;

import java.math.BigDecimal;
import java.util.List;

public class RestApiDBManager extends DatabaseManager
{



    public SellerEntity SelectSellerWithCifAndPassword(String cif, String password)
    {
        return null;
    }

    public boolean updateSeller(SellerEntity updatedSeller)
    {
        return false;
    }

    public List<CategoryEntity> SelectCategories()
    {
        return List.of();
    }

    public List<SellerProductEntity> SelectSellerProducts(SellerEntity seller)
    {
        return List.of();
    }

    public List<ProductEntity> SelectAvailableProducts(SellerEntity seller, CategoryEntity category)
    {
        return List.of();
    }

    public boolean InsertSellerProduct(SellerEntity seller, ProductEntity product, BigDecimal price, int stock)
    {
        return false;
    }

    public boolean AddOffer(SellerProductEntity updatedSellerProduct)
    {
        return false;
    }
}
