package com.sl2425.da.sellersapp.restapi.model.dao;

import com.sl2425.da.sellersapp.Model.Entities.SellerEntity;
import org.springframework.data.repository.CrudRepository;

public interface ISellerEntityDAO extends CrudRepository<SellerEntity, Integer>
{
}
