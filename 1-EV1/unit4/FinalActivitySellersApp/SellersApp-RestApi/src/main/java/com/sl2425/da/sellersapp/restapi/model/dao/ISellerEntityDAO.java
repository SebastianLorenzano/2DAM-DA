package com.sl2425.da.sellersapp.restapi.model.dao;

import com.sl2425.da.sellersapp.Model.Entities.SellerEntity;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

public interface ISellerEntityDAO extends CrudRepository<SellerEntity, Integer>
{
    SellerEntity findByCifAndPassword(String cif, String password);

    /*
    @Query("SELECT s FROM SellerEntity s WHERE s.cif = ?1 AND s.password = ?2")
    SellerEntity findByCifAndPassword(String cif, String password);

     */
}
