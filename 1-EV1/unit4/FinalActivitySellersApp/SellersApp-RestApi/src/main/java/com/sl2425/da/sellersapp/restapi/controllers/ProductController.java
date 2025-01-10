package com.sl2425.da.sellersapp.restapi.controllers;

import com.sl2425.da.sellersapp.Model.Entities.ProductEntity;
import com.sl2425.da.sellersapp.restapi.model.dao.IProductEntityDAO;
import com.sl2425.da.sellersapp.restapi.model.dto.CifAndCategoryDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("products")
public class ProductController
{
    @Autowired
    private IProductEntityDAO productDAO;

    @GetMapping
    public ResponseEntity<List<ProductEntity>> selectAvailableProducts(
            @RequestBody CifAndCategoryDTO dto)
    {
        return ResponseEntity.ok().body(productDAO.SelectAvailableProducts(dto.getCif(), dto.getCategoryId()));
    }


}
