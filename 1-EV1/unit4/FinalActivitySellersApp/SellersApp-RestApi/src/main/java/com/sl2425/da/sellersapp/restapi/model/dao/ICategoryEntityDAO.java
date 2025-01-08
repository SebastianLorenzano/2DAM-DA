package com.sl2425.da.sellersapp.restapi.model.dao;

import com.sl2425.da.sellersapp.Model.Entities.CategoryEntity;
import org.springframework.data.repository.CrudRepository;

public interface ICategoryEntityDAO extends CrudRepository<CategoryEntity, Integer>
{
}
