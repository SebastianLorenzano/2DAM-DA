package com.sl2425.da.sellersapp.restapi.controllers;


import com.sl2425.da.sellersapp.Model.Entities.SellerEntity;
import com.sl2425.da.sellersapp.restapi.model.codeStatus.LoginCodeStatus;
import com.sl2425.da.sellersapp.restapi.model.codeStatus.SellerCodeStatus;
import com.sl2425.da.sellersapp.restapi.model.dao.ISellerEntityDAO;
import com.sl2425.da.sellersapp.restapi.model.dto.SellerLoginDTO;
import com.sl2425.da.sellersapp.restapi.model.dto.SellerUpdateDTO;
import com.sl2425.da.sellersapp.restapi.services.SellersServices;
import org.apache.commons.lang3.tuple.Pair;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.Set;

@RestController
@RequestMapping("api/sellers")
public class SellerController
{
    @Autowired
    private ISellerEntityDAO sellerDAO;
    @Autowired
    private SellersServices sellersServices;

    @GetMapping
    public ResponseEntity<?> getSellerByCifAndPassword(@RequestBody SellerLoginDTO s)
    {
        Pair<Optional<SellerEntity>, LoginCodeStatus> result = sellersServices.getSellerByCifAndPassword(s);
        if (result.getLeft().isPresent())
            return ResponseEntity.ok().body(result.getLeft());
        return ResponseEntity.badRequest().body(result.getRight());
    }

    @PutMapping
    public ResponseEntity<?> updateSeller(@RequestBody SellerUpdateDTO sellerUpdateDTO)
    {
        Set<SellerCodeStatus> statuses = sellersServices.updateSeller(sellerUpdateDTO);
        if (statuses.contains(SellerCodeStatus.SUCCESS))
            return ResponseEntity.ok().build();
        Map<String, Object> errorResponse = new HashMap<>();
        errorResponse.put("message", "Seller update failed.");
        errorResponse.put("errors", statuses);
        return ResponseEntity.badRequest().body(errorResponse);
    }
}
