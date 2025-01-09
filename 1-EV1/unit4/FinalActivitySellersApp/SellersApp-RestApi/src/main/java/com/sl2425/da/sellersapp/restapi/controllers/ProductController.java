package com.sl2425.da.sellersapp.restapi.controllers;

import com.sl2425.da.sellersapp.Model.Entities.ProductEntity;
import com.sl2425.da.sellersapp.restapi.model.dao.IProductEntityDAO;
import jakarta.persistence.criteria.CriteriaBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("products")
public class ProductController
{
    @Autowired
    private IProductEntityDAO productDAO;

    @GetMapping
    public List<ProductEntity> selectAvailableProducts(
            @RequestParam("sellerId") int sellerId,
            @RequestParam("categoryId") int categoryId)
    {
        return (List<ProductEntity>)productDAO.SelectAvailableProducts(sellerId, categoryId);
    }


}
