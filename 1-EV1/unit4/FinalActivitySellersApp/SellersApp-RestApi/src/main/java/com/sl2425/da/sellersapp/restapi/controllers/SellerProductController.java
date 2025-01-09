package com.sl2425.da.sellersapp.restapi.controllers;

import com.sl2425.da.sellersapp.Model.Entities.SellerEntity;
import com.sl2425.da.sellersapp.Model.Entities.SellerProductEntity;
import com.sl2425.da.sellersapp.restapi.model.dao.ISellerProductEntityDAO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("sellerProducts")
public class SellerProductController
{
    @Autowired
    private ISellerProductEntityDAO sellerProductDAO;

    @GetMapping
    public List<SellerProductEntity> findAllSellerProductsBySeller(
            @RequestBody SellerEntity seller)
    {
        return sellerProductDAO.findAllBySeller(seller);
    }

    @PostMapping
    public SellerProductEntity saveSellerProduct(@RequestBody SellerProductEntity sellerProduct)
    {
        return sellerProductDAO.save(sellerProduct);
    }


}
