package com.sl2425.da.sellersapp.restapi.model.dao;

import com.sl2425.da.sellersapp.restapi.model.Entities.SellerEntity;
import org.springframework.data.repository.CrudRepository;

import java.util.Optional;

public interface ISellerEntityDAO extends CrudRepository<SellerEntity, Integer>
{
    SellerEntity findByCifAndPassword(String cif, String password);
    SellerEntity findByCif(String cif);
    boolean existsByCif(String cif);

    /*
    @Query("SELECT s FROM SellerEntity s WHERE s.cif = ?1 AND s.password = ?2")
    SellerEntity findByCifAndPassword(String cif, String password);

     */
}
