package com.sl2425.da.sellersapp.restapi.controllers.api;

import com.sl2425.da.sellersapp.restapi.model.Entities.ProductEntity;
import com.sl2425.da.sellersapp.restapi.services.SellerProductServices;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import com.sl2425.da.sellersapp.restapi.model.dto.ProductDTO;
import com.sl2425.da.sellersapp.restapi.services.ProductServices;

import java.util.List;

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

    @GetMapping({"/bySeller"})
    public ResponseEntity<?> getSellersWithProductId(
            @RequestParam(name = "productId", required = true, defaultValue = "-1") int productId)
    {
        if (productId <= 0)
            return ResponseEntity.badRequest().body("ERROR: ProductId must be greater than 0.");
        try
        {
            var result = productServices.getSellerContactDTOByProductId(productId);
            return ResponseEntity.ok().body(result);
        }
        catch (Exception ex)
        {
            return ResponseEntity.badRequest().body(ex.getMessage());
        }

    }

}
