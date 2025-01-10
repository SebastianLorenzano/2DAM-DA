package com.sl2425.da.sellersapp.restapi.services;

import com.sl2425.da.sellersapp.Model.Entities.SellerEntity;
import com.sl2425.da.sellersapp.Model.Entities.SellerProductEntity;
import com.sl2425.da.sellersapp.restapi.model.dao.IProductEntityDAO;
import com.sl2425.da.sellersapp.restapi.model.dao.ISellerEntityDAO;
import com.sl2425.da.sellersapp.restapi.model.dao.ISellerProductEntityDAO;
import com.sl2425.da.sellersapp.restapi.model.dto.SellerProductDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestBody;

import java.math.BigDecimal;
import java.util.List;

@Service
public class SellerProductServices
{
    @Autowired
    private ISellerProductEntityDAO sellerProductDAO;
    private ISellerEntityDAO sellerDAO;
    private IProductEntityDAO productDAO;



    public List<SellerProductEntity> findAllSellerProductsBySeller(SellerEntity seller)
    {
        return sellerProductDAO.findAllBySeller(seller);
    }
    // Validate para que tenga que respetar las comprobaciones de la entidad como @NotNull
    public ResponseEntity<SellerProductEntity> saveSellerProduct(SellerProductDTO s)
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
        SellerProductEntity sellerProduct =  s.toEntity(seller);
        return ResponseEntity.ok().body(sellerProductDAO.save(sellerProduct));
    }
}
