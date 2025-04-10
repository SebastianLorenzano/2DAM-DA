package com.sl2425.da.sellersapp.Model.Database;

import com.sl2425.da.sellersapp.Model.Entities.CategoryEntity;
import com.sl2425.da.sellersapp.Model.Entities.ProductEntity;
import com.sl2425.da.sellersapp.Model.Entities.SellerEntity;
import com.sl2425.da.sellersapp.Model.Entities.SellerProductEntity;

import java.math.BigDecimal;
import java.util.List;

public abstract class DatabaseManager
{
    public abstract SellerEntity SelectSellerWithCifAndPassword(String cif, String password);
    public abstract boolean updateSeller(SellerEntity updatedSeller);
    public abstract List<CategoryEntity> SelectCategories();
    public abstract List<SellerProductEntity> SelectSellerProducts(SellerEntity seller);
    public abstract List<ProductEntity> SelectAvailableProducts(SellerEntity seller, CategoryEntity category);
    public abstract boolean InsertSellerProduct(SellerEntity seller, ProductEntity product, BigDecimal price, int stock);
    public abstract boolean AddOffer(SellerProductEntity updatedSellerProduct);




}
