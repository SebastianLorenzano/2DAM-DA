package com.sl2425.da.sellersapp.restapi.services;

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
import com.sl2425.da.sellersapp.restapi.model.dto.SellerProductDTO;
import org.apache.commons.lang3.NotImplementedException;
import org.apache.commons.lang3.tuple.Pair;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


import java.math.BigDecimal;
import java.time.LocalDate;
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
    @Autowired
    private SellerProductValidations sellerProductValidations;
    @Autowired
    private SellerProductUtils sellerProductUtils;


    public SellerProductEntity findSellerProductById(int sellerProductId)
    {
        Optional<SellerProductEntity> optional = sellerProductDAO.findById(sellerProductId);
        return optional.orElse(null);
    }

    public List<SellerProductEntity> findAllBySellerAndOfferExpired(SellerEntity seller)
    {
        return sellerProductDAO.findAllBySellerAndOfferExpired(seller);
    }

    public List<SellerProductEntity> findAllSellerProductsBySeller(SellerEntity seller)
    {
        return sellerProductDAO.findAllBySeller(seller);
    }

    public Set<SellerProductCodeStatus> saveSellerProduct(SellerProductDTO sellerProductDTO, Utils.HttpRequests requestType)
    {
        Set <SellerProductCodeStatus> statutes = new HashSet<>();
        Pair<Optional<SellerEntity>, LoginCodeStatus> pair = sellerServices.getSellerByCif(sellerProductDTO.getCif());
        if (pair.getLeft().isEmpty())
        {
            statutes.add(SellerProductCodeStatus.SELLER_NOT_FOUND);
            return statutes;
        }
        SellerEntity seller = pair.getLeft().get();
        Pair<SellerProductEntity, Set<SellerProductCodeStatus>> sellerProductPair =  fromDTO(sellerProductDTO, seller);
        statutes = sellerProductPair.getRight();
        if (!statutes.isEmpty())
            return sellerProductPair.getRight();

        SellerProductEntity sellerProduct = sellerProductPair.getLeft();
        if (requestType == Utils.HttpRequests.POST)
            statutes = createSellerProduct(sellerProduct);
        else  if (requestType == Utils.HttpRequests.PUT)
            statutes = updateSellerProduct(sellerProduct, sellerProductDTO);
        else
            statutes.add(SellerProductCodeStatus.INVALID_REQUEST_TYPE);
        return statutes;
    }

    private Set<SellerProductCodeStatus> createSellerProduct(SellerProductEntity sellerProduct)
    {
        Set<SellerProductCodeStatus> result = sellerProductValidations.validateCreate(sellerProduct);
        if (result.isEmpty())
        {
            sellerProductDAO.save(sellerProduct);
            result.add(SellerProductCodeStatus.SUCCESS);
        }
        return result;
    }

    private Set<SellerProductCodeStatus> updateSellerProduct(SellerProductEntity sellerProduct, SellerProductDTO sellerProductDTO)
    {
        Set<SellerProductCodeStatus> result = new HashSet<>();
        if (sellerProduct.getOfferEndDate() != null && sellerProduct.getOfferEndDate().isAfter(LocalDate.now()))
        {
            result.add(SellerProductCodeStatus.OFFER_HASNT_ENDED);
            // return result; // If we don't want the code to keep looking for errors
        }
        result = sellerProductValidations.validateUpdate(sellerProduct);
        if (result.isEmpty())
        {
            sellerProductDAO.save(sellerProduct);
            result.add(SellerProductCodeStatus.SUCCESS);
        }
        return result;
    }

    private Pair<SellerProductEntity, Set<SellerProductCodeStatus>> fromDTO(SellerProductDTO dto, SellerEntity sellerEntity)
    {
        var result =  new SellerProductEntity();
        Optional<ProductEntity> product = productDAO.findById(dto.getProductId());
        Set<SellerProductCodeStatus> statutes = new HashSet<>();
        if (product.isEmpty())
        {
            statutes.add(SellerProductCodeStatus.PRODUCT_NOT_FOUND);
            return Pair.of(null, statutes);
        }
        BigDecimal offerPrice = sellerProductUtils.getOfferPrice(dto.getPrice(), dto.getDiscount());

        result.setId(dto.getId());
        result.setSeller(sellerEntity);
        result.setProduct(product.get());
        result.setPrice(dto.getPrice());
        result.setOfferPrice(offerPrice);
        result.setOfferStartDate(dto.getOfferStartDate());
        result.setOfferEndDate(dto.getOfferEndDate());
        result.setStock(dto.getStock());
        return Pair.of(result, statutes);
    }
}
