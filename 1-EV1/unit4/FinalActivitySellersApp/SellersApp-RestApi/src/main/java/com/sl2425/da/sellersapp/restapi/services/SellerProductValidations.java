package com.sl2425.da.sellersapp.restapi.services;

import com.sl2425.da.sellersapp.Model.Entities.SellerProductEntity;
import com.sl2425.da.sellersapp.restapi.model.codeStatus.SellerProductCodeStatus;
import com.sl2425.da.sellersapp.restapi.model.dao.ISellerProductEntityDAO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Component
public class SellerProductValidations
{

    @Autowired
    private ISellerProductEntityDAO sellerProductDAO;

    public Set<SellerProductCodeStatus> validateCreate(SellerProductEntity sellerProduct)
    {
        Set<SellerProductCodeStatus> result = new HashSet<>();
        if (sellerProductNull(sellerProduct))
        {
            result.add(SellerProductCodeStatus.SELLER_PRODUCT_IS_NULL);
            return result;
        }

        if (sellerProductExists(sellerProduct))
            result.add(SellerProductCodeStatus.SELLER_PRODUCT_ALREADY_EXISTS);

        if (datePeriodCollide())
        return result; // TODO: Implement this method
    }

    public Set<SellerProductCodeStatus> validateUpdate(SellerProductEntity sellerProduct)
    {
        Set<SellerProductCodeStatus> result = new HashSet<>();
        if (sellerProductNull(sellerProduct))
        {
            result.add(SellerProductCodeStatus.SELLER_PRODUCT_IS_NULL);
            return result;
        }
        return null; // TODO: Implement this method
    }



    private boolean sellerProductNull(SellerProductEntity sellerProduct)
    {
        return sellerProduct == null;
    }


    private boolean sellerProductExists(SellerProductEntity sellerProduct)
    {
        return sellerProductDAO.existsById(sellerProduct.getId());
    }




    private boolean dayPeriodAvailable(SellerProductEntity sellerProduct)
    {
        if (sellerProduct.getOfferStartDate() == null || sellerProduct.getOfferEndDate() == null)
            return false;
        List<SellerProductEntity> sellerProducts = sellerProductDAO.findAllBySellerId(sellerProduct.getId());
        int collisions = 0;
        int maxCollisions = getMaxCollisions(sellerProduct);
        for (SellerProductEntity s : sellerProducts)
        {
            if (datePeriodCollide(s.getOfferStartDate(), s.getOfferEndDate(),
                    s.getOfferStartDate(), s.getOfferEndDate()))
            {
                collisions++;
                if (collisions >= maxCollisions)
                    return false;
            }
        }
        return true;
    }

    private boolean datePeriodCollide(LocalDate startDate1, LocalDate endDate1, LocalDate startDate2, LocalDate endDate2)
    {
        if (startDate1 == null || endDate1 == null || startDate2 == null || endDate2 == null)
            return false;
        return ((startDate1.isBefore(startDate2) && endDate1.isAfter(startDate2)) ||   // If the period starts before the other period and ends after the other period
                (startDate2.isBefore(startDate1) && endDate2.isAfter(startDate1)) ||  // If the other period starts before this period and ends after this period
                startDate1.isEqual(startDate2));                                   // If the periods start at the same time
    }


}
