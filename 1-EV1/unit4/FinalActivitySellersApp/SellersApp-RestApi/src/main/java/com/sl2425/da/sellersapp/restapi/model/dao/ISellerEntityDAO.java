package com.sl2425.da.sellersapp.restapi.model.dao;

import com.sl2425.da.sellersapp.Model.Entities.SellerEntity;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import java.util.Optional;

public interface ISellerEntityDAO extends CrudRepository<SellerEntity, Integer>
{
    Optional<SellerEntity> findByCifAndPassword(String cif, String password);
    Optional<SellerEntity> findByCif(String cif);

    /*
    @Query("SELECT s FROM SellerEntity s WHERE s.cif = ?1 AND s.password = ?2")
    SellerEntity findByCifAndPassword(String cif, String password);

     */
}
