package com.sl2425.da.sellersapp.restapi.model.dao;

import com.sl2425.da.sellersapp.Model.Entities.ProductEntity;
import com.sl2425.da.sellersapp.Model.Entities.SellerEntity;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface IProductEntityDAO extends CrudRepository<ProductEntity, Integer>
{
    @Query(value = "SELECT * FROM select_available_products_sl2425(:sellerId, :categoryId)", nativeQuery = true)
    List<ProductEntity> SelectAvailableProducts(int sellerId, int categoryId);
}
