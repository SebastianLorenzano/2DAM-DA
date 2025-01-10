package com.sl2425.da.sellersapp.restapi.controllers;


import com.sl2425.da.sellersapp.Model.Entities.SellerEntity;
import com.sl2425.da.sellersapp.restapi.model.dao.ICategoryEntityDAO;
import com.sl2425.da.sellersapp.restapi.model.dao.ISellerEntityDAO;
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
    public ResponseEntity<?> getSellerByCifAndPassword(
            @RequestParam("cif") String cif,
            @RequestBody String password)
    {
        SellerEntity result = sellerDAO.findByCifAndPassword(cif, password);
        if (result == null)
            return ResponseEntity.notFound().build();
        return ResponseEntity.ok().body(result);
    }

    @PutMapping
    public ResponseEntity<?> updateSeller(@Validated @RequestBody SellerEntity value) {
        Optional<SellerEntity> seller = sellerDAO.findById(value.getId());
        if (!seller.isPresent())
            return ResponseEntity.badRequest().build();
        sellerDAO.save(value);
        return ResponseEntity.ok().body("Updated");
    }

}
