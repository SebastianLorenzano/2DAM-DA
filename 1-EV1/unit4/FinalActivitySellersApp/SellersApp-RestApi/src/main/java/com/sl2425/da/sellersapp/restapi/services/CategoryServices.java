package com.sl2425.da.sellersapp.restapi.services;

import com.sl2425.da.sellersapp.Model.Entities.CategoryEntity;
import com.sl2425.da.sellersapp.restapi.model.dao.ICategoryEntityDAO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class CategoryServices
{
    @Autowired
    private ICategoryEntityDAO categoryDAO;

    public List<CategoryEntity> getAllCategories()
    {
        return (ArrayList<CategoryEntity>)categoryDAO.findAll();
    }
}
