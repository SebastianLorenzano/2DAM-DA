package com.sl2425.da.sellersapp.restapi.controllers;


import com.sl2425.da.sellersapp.Model.Entities.SellerEntity;
import com.sl2425.da.sellersapp.restapi.model.dao.ICategoryEntityDAO;
import com.sl2425.da.sellersapp.restapi.model.dao.ISellerEntityDAO;
import com.sl2425.da.sellersapp.restapi.model.dto.SellerDTO;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.swing.text.html.Option;
import java.util.Optional;

@RestController
@RequestMapping("sellers")
public class SellerController
{
    @Autowired
    private ISellerEntityDAO sellerDAO;

    @GetMapping
    public ResponseEntity<SellerEntity> getSellerByCifAndPassword(@Validated @RequestBody SellerDTO s)
    {
        SellerEntity seller = sellerDAO.findByCifAndPassword(s.getCif(), s.getPassword());
        if (seller == null)
            return ResponseEntity.notFound().build();
        return ResponseEntity.ok().body(seller);
    }

    @PutMapping
    public ResponseEntity<?> updateSeller(@Validated @RequestBody SellerEntity value) {
        SellerEntity seller = sellerDAO.findByCif(value.getCif());
        if (seller == null)
            return ResponseEntity.badRequest().build();
        sellerDAO.save(value);
        return ResponseEntity.ok().body("Updated");
    }

}
