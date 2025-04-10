package com.sl2425.da.demo1.model.dao;

import com.sl2425.da.demo1.model.entities.EmployeeEntity;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface IEmployeeEntityDAO extends CrudRepository<EmployeeEntity, Integer>
{

}
