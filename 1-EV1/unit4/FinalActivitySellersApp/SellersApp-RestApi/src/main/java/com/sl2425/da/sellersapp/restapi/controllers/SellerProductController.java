package com.sl2425.da.sellersapp.restapi.controllers;

import com.sl2425.da.sellersapp.Model.Entities.SellerEntity;
import com.sl2425.da.sellersapp.Model.Entities.SellerProductEntity;
import com.sl2425.da.sellersapp.restapi.model.dao.IProductEntityDAO;
import com.sl2425.da.sellersapp.restapi.model.dao.ISellerEntityDAO;
import com.sl2425.da.sellersapp.restapi.model.dao.ISellerProductEntityDAO;
import com.sl2425.da.sellersapp.restapi.model.dto.SellerProductDTO;
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
    private ISellerProductEntityDAO sellerProductDAO;
    private ISellerEntityDAO sellerDAO;
    private IProductEntityDAO productDAO;

    @GetMapping
    public List<SellerProductEntity> findAllSellerProductsBySeller(
            @RequestBody SellerEntity seller)
    {
        return sellerProductDAO.findAllBySeller(seller);
    }

    @PostMapping
    public ResponseEntity<SellerProductEntity> saveSellerProduct(@RequestBody SellerProductDTO s)
    {
        if (s == null || s.getProduct() == null)
            throw new IllegalArgumentException("Invalid sellerProduct");
        SellerEntity seller = sellerDAO.findByCifAndPassword(
                s.getSellerDTO().getCif(), s.getSellerDTO().getPassword());
        if (seller == null)
            throw new IllegalArgumentException("Seller not found");
        if (productDAO.findById(s.getProduct().getId()).isEmpty())
            throw new IllegalArgumentException("Product not found");
        if (s.getPrice().compareTo(BigDecimal.ZERO) <= 0)
            throw new IllegalArgumentException("Invalid price");
        SellerProductEntity sellerProduct = new SellerProductEntity(s, seller);

        return ResponseEntity.ok().body(sellerProductDAO.save(sellerProduct));
    }


}
