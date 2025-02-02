package com.sl2425.da.sellersapp.restapi.services;

import com.sl2425.da.sellersapp.Model.Entities.SellerEntity;
import com.sl2425.da.sellersapp.restapi.model.codeStatus.SellerCodeStatus;

import java.util.HashSet;
import java.util.Objects;
import java.util.Set;
import java.util.regex.Pattern;

public class SellerValidations
{

    public static Set<SellerCodeStatus> validateSeller(SellerEntity oldSeller, SellerEntity newSeller)
    {
        Set<SellerCodeStatus> result = new HashSet<>();
        if (isSellerNull(newSeller))
        {
            result.add(SellerCodeStatus.SELLER_IS_NULL);
            return result;                                      // Return early to avoid Null Pointer Exception
        }
        if (isSellerEqualTOSeller(oldSeller, newSeller))
            result.add(SellerCodeStatus.SELLER_UNCHANGED);
        if (result.isEmpty())
            result.add(SellerCodeStatus.SUCCESS);
        return result;
    }

    private static boolean isSellerEqualTOSeller(SellerEntity oldSeller, SellerEntity newSeller)
    {
        return newSeller != null &&
                Objects.equals(newSeller.getCif(), oldSeller.getCif()) &&
                Objects.equals(newSeller.getName(), oldSeller.getName()) &&
                Objects.equals(newSeller.getBusinessName(), oldSeller.getBusinessName()) &&
                Objects.equals(newSeller.getPhone(), oldSeller.getPhone()) &&
                Objects.equals(newSeller.getEmail(), oldSeller.getEmail()) &&
                Objects.equals(newSeller.getPassword(), oldSeller.getPassword()) &&
                (!newSeller.getPro() || Objects.equals(newSeller.getUrl(), oldSeller.getUrl()));
    }

    private static boolean isSellerNull(SellerEntity seller)
    {
        return seller == null;
    }

}
