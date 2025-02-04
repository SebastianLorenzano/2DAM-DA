package com.sl2425.da.sellersapp.restapi.services;

import com.sl2425.da.sellersapp.Model.Entities.CategoryEntity;
import com.sl2425.da.sellersapp.Model.Entities.ProductEntity;
import com.sl2425.da.sellersapp.Model.Entities.SellerEntity;
import com.sl2425.da.sellersapp.Model.Entities.SellerProductEntity;
import com.sl2425.da.sellersapp.restapi.model.Utils;
import com.sl2425.da.sellersapp.restapi.model.codeStatus.LoginCodeStatus;
import com.sl2425.da.sellersapp.restapi.model.codeStatus.SellerProductCodeStatus;
import com.sl2425.da.sellersapp.restapi.model.dao.ICategoryEntityDAO;
import com.sl2425.da.sellersapp.restapi.model.dao.IProductEntityDAO;
import com.sl2425.da.sellersapp.restapi.model.dao.ISellerEntityDAO;
import com.sl2425.da.sellersapp.restapi.model.dao.ISellerProductEntityDAO;
import com.sl2425.da.sellersapp.restapi.model.dto.SellerLoginDTO;
import com.sl2425.da.sellersapp.restapi.model.dto.SellerProductDTO;
import com.sl2425.da.sellersapp.restapi.model.dto.SellerUpdateDTO;
import org.apache.commons.lang3.tuple.Pair;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.ui.Model;


import java.math.BigDecimal;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;

@Service
public class SellerProductServices
{
    @Autowired
    private ISellerProductEntityDAO sellerProductDAO;
    @Autowired
    private ISellerEntityDAO sellerDAO;
    @Autowired
    private IProductEntityDAO productDAO;
    @Autowired
    private ICategoryEntityDAO categoryDAO;
    @Autowired
    private SellerServices sellerServices;



    public List<SellerProductEntity> findAllSellerProductsBySeller(SellerEntity seller)
    {
        return sellerProductDAO.findAllBySeller(seller);
    }
    // Validate para que tenga que respetar las comprobaciones de la entidad como @NotNull
    public Set<SellerProductCodeStatus> saveSellerProduct(SellerProductDTO sellerProductDTO, Utils.HttpRequests RequestType)
    {
        Set <SellerProductCodeStatus> statutes = new HashSet<>();
        Pair<Optional<SellerEntity>, LoginCodeStatus> pair = sellerServices.getSellerByCifAndPassword(sellerProductDTO.getSellerDTO());
        if (pair.getLeft().isEmpty())
        {
            statutes.add(SellerProductCodeStatus.SELLER_NOT_FOUND);
            return statutes;
        }
        SellerEntity seller = pair.getLeft().get();
        Pair<SellerProductEntity, Set<SellerProductCodeStatus>> sellerProductPair =  fromDTO(sellerProductDTO, seller);
        if (!sellerProductPair.getRight().isEmpty())
            return sellerProductPair.getRight();

        SellerProductEntity sellerProduct = sellerProductPair.getLeft();
        Boolean exists = sellerProductDAO.existsBySellerAndProduct(seller, sellerProduct.getProduct());
        if (RequestType == Utils.HttpRequests.POST && exists)
            statutes.add(SellerProductCodeStatus.SELLER_PRODUCT_ALREADY_EXISTS);
        else if (RequestType == Utils.HttpRequests.PUT && !exists)
            statutes.add(SellerProductCodeStatus.SELLER_PRODUCT_NOT_FOUND);
        if (statutes.isEmpty())
        {
            sellerProductDAO.save(sellerProduct);
            statutes.add(SellerProductCodeStatus.SUCCESS);
        }
        return statutes;
    }


    public String postSellerProduct(SellerUpdateDTO sellerDTO, Model model)
    {
        SellerEntity existingSeller = sellerDAO.findByCif(sellerDTO.getCif());
        if (existingSeller == null)  // TODO: Move it to server and check that the changes are valid
            model.addAttribute("error", "Seller not found");
        else {


            model.addAttribute("success", "Seller Product updated successfully!");
        }

        model.addAttribute("sellerDTO", sellerDTO);
        return "sellerProducts-post";
    }




    /*
    private SellerEntity fromDTO(SellerProductDTO s, SellerEntity seller)
    {
        if (s == null)
            throw new IllegalArgumentException("Invalid sellerProduct");
        ProductEntity product = productDAO.findById(s.getProductId());
        if (product == null)
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

     */

    private Pair<SellerProductEntity, Set<SellerProductCodeStatus>> fromDTO(SellerProductDTO dto, SellerEntity sellerEntity)
    {
        var result =  new SellerProductEntity();
        ProductEntity product = productDAO.findById(dto.getProductId());
        Set<SellerProductCodeStatus> statutes = new HashSet<>();
        if (product == null)
        {
            statutes.add(SellerProductCodeStatus.PRODUCT_NOT_FOUND);
            return Pair.of(null, statutes);
        }
        result.setId(dto.getId());
        result.setSeller(sellerEntity);
        result.setProduct(product);
        result.setPrice(dto.getPrice());
        result.setOfferPrice(dto.getOfferPrice());
        result.setOfferStartDate(dto.getOfferStartDate());
        result.setOfferEndDate(dto.getOfferEndDate());
        result.setStock(dto.getStock());
        return Pair.of(result, statutes);
    }
}
