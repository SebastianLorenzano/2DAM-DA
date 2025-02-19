package com.sl2425.da.sellersapp.restapi.controllers.api;

import com.sl2425.da.sellersapp.Model.Entities.ProductEntity;
import com.sl2425.da.sellersapp.Model.Entities.SellerEntity;
import com.sl2425.da.sellersapp.restapi.model.dto.SellerContactDTO;
import com.sl2425.da.sellersapp.restapi.model.dto.SellerUpdateDTO;
import com.sl2425.da.sellersapp.restapi.services.SellerProductServices;
import org.apache.coyote.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import com.sl2425.da.sellersapp.restapi.model.dto.ProductDTO;
import com.sl2425.da.sellersapp.restapi.services.ProductServices;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/products")
public class ProductController
{
    @Autowired
    private ProductServices productServices;
    @Autowired
    private SellerProductServices sellerProductServices;

    @GetMapping
    public ResponseEntity<List<ProductEntity>> selectAvailableProducts(@RequestBody ProductDTO dto)
    {
        return ResponseEntity.ok().body(productServices.getProducts(
                dto.getCif(), dto.getCategoryId(), dto.getRemainingProducts()));
    }

    @GetMapping({"/api/products/productId"})
    public ResponseEntity<?> getSellersWithProductId(
            @RequestParam(name = "productId", required = true) int productId)
    {
        var result = productServices.getSellerContactDTOByProductId(productId);
        if (result == null)
            return ResponseEntity.badRequest().body("Product doesn't exist.");
        return ResponseEntity.ok().body(result);
    }

}
