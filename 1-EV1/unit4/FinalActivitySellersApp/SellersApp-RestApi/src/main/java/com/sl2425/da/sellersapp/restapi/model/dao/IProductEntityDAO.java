package com.sl2425.da.sellersapp.restapi.model.dao;

import com.sl2425.da.sellersapp.Model.Entities.ProductEntity;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import java.util.List;
import java.util.Optional;

public interface IProductEntityDAO extends CrudRepository<ProductEntity, Integer>
{
    @Query(value = "SELECT * FROM select_available_products_sl2425_2(:cif, :categoryId)", nativeQuery = true)
    List<ProductEntity> selectAvailableProducts(String cif, int categoryId);

    Optional<ProductEntity> findById(Integer id);

    //List<ProductEntity> findByCategoryId(String cif, Integer categoryId);
}
