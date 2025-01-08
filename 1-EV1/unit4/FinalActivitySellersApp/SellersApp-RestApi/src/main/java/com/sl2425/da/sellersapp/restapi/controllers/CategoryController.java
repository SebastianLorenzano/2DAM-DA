package com.sl2425.da.sellersapp.restapi.controllers;

import com.sl2425.da.sellersapp.Model.Entities.CategoryEntity;
import com.sl2425.da.sellersapp.restapi.model.dao.ICategoryEntityDAO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("categories")
public class CategoryController
{

    @Autowired
    private ICategoryEntityDAO categoryDAO;

    @GetMapping
    public List<CategoryEntity> findAllCategories()
    {
        return (List<CategoryEntity>)categoryDAO.findAll();
    }

}
