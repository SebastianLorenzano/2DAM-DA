package com.sl2425.da.demo1.model.dao;

import com.sl2425.da.demo1.model.entities.DeptEntity;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface IDeptEntityDAO extends CrudRepository<DeptEntity, Integer>
{
}
