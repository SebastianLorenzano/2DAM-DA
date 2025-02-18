package com.sl2425.da.sellersapp.restapi.controllers.api;

import com.sl2425.da.sellersapp.Model.Entities.SellerEntity;
import com.sl2425.da.sellersapp.Model.Entities.SellerProductEntity;
import com.sl2425.da.sellersapp.restapi.model.Utils;
import com.sl2425.da.sellersapp.restapi.model.codeStatus.SellerProductCodeStatus;
import com.sl2425.da.sellersapp.restapi.model.dto.SellerProductDTO;
import com.sl2425.da.sellersapp.restapi.services.SellerProductServices;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

@RestController
@RequestMapping("/api/sellerProducts")
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
        {
            Map<String, Object> successResponse = new HashMap<>();
            successResponse.put("message", "SellerProduct saved successfully.");
            return ResponseEntity.ok().body(successResponse);
        }
        Map<String, Object> errorResponse = new HashMap<>();
        errorResponse.put("message", "Seller Product saving failed.");
        errorResponse.put("errors", statuses);
        return ResponseEntity.badRequest().body(errorResponse);
    }

    @PutMapping
    public ResponseEntity<?> updateSellerProduct(@RequestBody SellerProductDTO s)
    {
        Set<SellerProductCodeStatus> statuses = sellerProductServices.saveSellerProduct(s, Utils.HttpRequests.PUT);
        if (statuses.contains(SellerProductCodeStatus.SUCCESS))
        {
            Map<String, Object> successResponse = new HashMap<>();
            successResponse.put("message", "SellerProduct saved successfully.");
            return ResponseEntity.ok().body(successResponse);
        }
        Map<String, Object> errorResponse = new HashMap<>();
        errorResponse.put("message", "Seller Product saving failed.");
        errorResponse.put("errors", statuses);
        return ResponseEntity.badRequest().body(errorResponse);
    }
}
