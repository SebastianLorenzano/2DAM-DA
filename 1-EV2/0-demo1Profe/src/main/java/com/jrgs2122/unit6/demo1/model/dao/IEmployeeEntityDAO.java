package com.jrgs2122.unit6.demo1.model.dao;

import com.jrgs2122.unit6.demo1.model.entities.EmployeeJPAEntity;
import org.springframework.data.repository.CrudRepository;

public interface IEmployeeEntityDAO extends CrudRepository<EmployeeJPAEntity, Integer> {
}
