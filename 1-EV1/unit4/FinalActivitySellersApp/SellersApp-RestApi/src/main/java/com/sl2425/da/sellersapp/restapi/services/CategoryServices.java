package com.sl2425.da.sellersapp.restapi.services;

import com.sl2425.da.sellersapp.Model.Entities.CategoryEntity;
import com.sl2425.da.sellersapp.restapi.model.dao.ICategoryEntityDAO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CategoryServices
{
    @Autowired
    private ICategoryEntityDAO categoryDAO;

    public ResponseEntity<List<CategoryEntity>> findAllCategories()
    {
        return ResponseEntity.ok().body((List<CategoryEntity>)categoryDAO.findAll());
    }
}
