package com.sl2425.da.sellersapp.restapi.services;

import com.sl2425.da.sellersapp.Model.Entities.SellerEntity;
import com.sl2425.da.sellersapp.Model.Entities.SellerProductEntity;
import com.sl2425.da.sellersapp.restapi.model.Utils;
import com.sl2425.da.sellersapp.restapi.model.dao.IProductEntityDAO;
import com.sl2425.da.sellersapp.restapi.model.dao.ISellerEntityDAO;
import com.sl2425.da.sellersapp.restapi.model.dao.ISellerProductEntityDAO;
import com.sl2425.da.sellersapp.restapi.model.dto.SellerProductDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


import java.math.BigDecimal;
import java.util.List;

@Service
public class SellerProductServices
{
    @Autowired
    private ISellerProductEntityDAO sellerProductDAO;
    @Autowired
    private ISellerEntityDAO sellerDAO;
    @Autowired
    private IProductEntityDAO productDAO;



    public List<SellerProductEntity> findAllSellerProductsBySeller(SellerEntity seller)
    {
        return sellerProductDAO.findAllBySeller(seller);
    }
    // Validate para que tenga que respetar las comprobaciones de la entidad como @NotNull
    public SellerProductEntity saveSellerProduct(SellerProductDTO s, Utils.HttpRequests RequestType)
    {
        SellerEntity seller = getSellerFromDTO(s);
        SellerProductEntity sellerProduct =  s.toEntity(seller);

        Boolean exists = sellerProductDAO.findById(sellerProduct.getId()).isPresent();
        if (RequestType == Utils.HttpRequests.POST && exists)
            throw new IllegalArgumentException("Resource already exists");
        else if (RequestType == Utils.HttpRequests.PUT && !exists)
            throw new IllegalArgumentException("Resource doesn't exist");
        return sellerProductDAO.save(sellerProduct);
    }




    private SellerEntity getSellerFromDTO(SellerProductDTO s)
    {
        if (s == null)
            throw new IllegalArgumentException("Invalid sellerProduct");
        if (s.getProduct() == null || !productDAO.findById(s.getProduct().getId()).isPresent())
            throw new IllegalArgumentException("Invalid product");
        if (s.getPrice().compareTo(BigDecimal.ZERO) <= 0)
            throw new IllegalArgumentException("Invalid price");
        if (s.getOfferPrice() != null && s.getOfferPrice().compareTo(s.getPrice()) > 0)
            throw new IllegalArgumentException("Invalid offer price");
        if (s.getOfferStartDate().isAfter(s.getOfferEndDate()) ||
            s.getOfferStartDate().isEqual(s.getOfferEndDate()))
            throw new IllegalArgumentException("Invalid offer dates");
        SellerEntity seller = sellerDAO.findByCifAndPassword(
                s.getSellerDTO().getCif(), s.getSellerDTO().getPassword());
        if (seller == null)
            throw new IllegalArgumentException("Seller not found");
        return seller;
    }


}
