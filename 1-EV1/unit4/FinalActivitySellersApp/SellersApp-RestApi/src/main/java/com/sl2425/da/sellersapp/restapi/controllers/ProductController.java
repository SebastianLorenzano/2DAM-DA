package com.sl2425.da.sellersapp.restapi.controllers;

import com.sl2425.da.sellersapp.Model.Entities.ProductEntity;
import com.sl2425.da.sellersapp.restapi.model.dao.IProductEntityDAO;
import com.sl2425.da.sellersapp.restapi.model.dto.ProductDTO;
import com.sl2425.da.sellersapp.restapi.services.ProductServices;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/products")
public class ProductController
{
    @Autowired
    private ProductServices productServices;

    @GetMapping
    public ResponseEntity<List<ProductEntity>> selectAvailableProducts(@RequestBody ProductDTO dto)
    {
        return ResponseEntity.ok().body(productServices.selectProducts(
                dto.getCif(), dto.getCategoryId(), dto.getRemainingProducts()));
    }
}
