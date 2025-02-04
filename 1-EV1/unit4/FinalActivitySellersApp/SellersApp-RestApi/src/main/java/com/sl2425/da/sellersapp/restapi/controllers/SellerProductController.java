package com.sl2425.da.sellersapp.restapi.controllers;

import com.sl2425.da.sellersapp.Model.Entities.SellerEntity;
import com.sl2425.da.sellersapp.Model.Entities.SellerProductEntity;
import com.sl2425.da.sellersapp.restapi.model.Utils;
import com.sl2425.da.sellersapp.restapi.model.codeStatus.SellerProductCodeStatus;
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
import java.util.Set;

@RestController
@RequestMapping("api/sellerProducts")
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
    public ResponseEntity<?> saveSellerProduct(@RequestBody SellerProductDTO s)
    {
        Set<SellerProductCodeStatus> statuses = sellerProductServices.saveSellerProduct(s, Utils.HttpRequests.POST);
        if (statuses.contains(SellerProductCodeStatus.SUCCESS))
            return ResponseEntity.ok().body("SellerProduct saved successfully");
        for (SellerProductCodeStatus status : statuses)
        {
            if (status == SellerProductCodeStatus.SELLER_NOT_FOUND)
                return ResponseEntity.badRequest().body("Seller not found");
            if (status == SellerProductCodeStatus.PRODUCT_NOT_FOUND)
                return ResponseEntity.badRequest().body("Product not found");
            if (status == SellerProductCodeStatus.SELLER_PRODUCT_ALREADY_EXISTS)
                return ResponseEntity.badRequest().body("SellerProduct already exists");
        }
        return ResponseEntity.badRequest().body("Error saving SellerProduct");
    }

    @PutMapping
    public ResponseEntity<?> updateSellerProduct(@RequestBody SellerProductDTO s)
    {
        Set<SellerProductCodeStatus> statuses = sellerProductServices.saveSellerProduct(s, Utils.HttpRequests.PUT);
        if (statuses.contains(SellerProductCodeStatus.SUCCESS))
            return ResponseEntity.ok().body("SellerProduct updated successfully");
        for (SellerProductCodeStatus status : statuses)
        {
            if (status == SellerProductCodeStatus.SELLER_NOT_FOUND)
                return ResponseEntity.badRequest().body("Seller not found");
            if (status == SellerProductCodeStatus.PRODUCT_NOT_FOUND)
                return ResponseEntity.badRequest().body("Product not found");
            if (status == SellerProductCodeStatus.SELLER_PRODUCT_NOT_FOUND)
                return ResponseEntity.badRequest().body("SellerProduct not found");
        }
        return ResponseEntity.badRequest().body("Error updating SellerProduct");
    }


}
