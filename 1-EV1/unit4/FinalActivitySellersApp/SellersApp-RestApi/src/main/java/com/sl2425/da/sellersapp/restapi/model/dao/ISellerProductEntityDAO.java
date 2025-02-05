package com.sl2425.da.sellersapp.restapi.model.dao;


import com.sl2425.da.sellersapp.Model.Entities.ProductEntity;
import com.sl2425.da.sellersapp.Model.Entities.SellerEntity;
import com.sl2425.da.sellersapp.Model.Entities.SellerProductEntity;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface ISellerProductEntityDAO extends CrudRepository<SellerProductEntity, Integer>
{
    public List<SellerProductEntity> findAllBySeller(SellerEntity seller);

    Boolean existsBySellerAndProduct(SellerEntity seller, ProductEntity product);

    List<SellerProductEntity> findAllBySellerId(int sellerId);


    //             Query<SellerProductEntity> query = session.createQuery(
    //                    "from SellerProductEntity where seller = :seller", SellerProductEntity.class);


}
