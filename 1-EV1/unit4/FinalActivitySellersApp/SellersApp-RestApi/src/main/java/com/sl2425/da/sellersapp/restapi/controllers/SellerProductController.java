package com.sl2425.da.sellersapp.restapi.controllers;

import com.sl2425.da.sellersapp.Model.Entities.SellerEntity;
import com.sl2425.da.sellersapp.Model.Entities.SellerProductEntity;
import com.sl2425.da.sellersapp.restapi.model.dao.IProductEntityDAO;
import com.sl2425.da.sellersapp.restapi.model.dao.ISellerEntityDAO;
import com.sl2425.da.sellersapp.restapi.model.dao.ISellerProductEntityDAO;
import com.sl2425.da.sellersapp.restapi.model.dto.SellerProductDTO;
import com.sl2425.da.sellersapp.restapi.services.SellerProductServices;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.util.List;

@RestController
@RequestMapping("sellerProducts")
public class SellerProductController
{
    @Autowired
    private SellerProductServices sellerProductServices;


    @GetMapping
    public ResponseEntity<List<SellerProductEntity>> findAllSellerProductsBySeller(
            @RequestBody SellerEntity seller)
    {
        return ResponseEntity.ok().body(sellerProductServices.findAllSellerProductsBySeller(seller));
    }

    @PostMapping
    public ResponseEntity<SellerProductEntity> saveSellerProduct(@RequestBody SellerProductDTO s)
    {
        return sellerProductServices.saveSellerProduct(s);
    }


}
