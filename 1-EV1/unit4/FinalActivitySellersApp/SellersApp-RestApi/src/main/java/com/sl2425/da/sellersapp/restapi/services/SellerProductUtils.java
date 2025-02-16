package com.sl2425.da.sellersapp.restapi.services;

import com.sl2425.da.sellersapp.Model.Entities.SellerEntity;
import com.sl2425.da.sellersapp.Model.Entities.SellerProductEntity;
import com.sl2425.da.sellersapp.restapi.model.Utils;
import com.sl2425.da.sellersapp.restapi.model.dto.SellerProductDTO;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;

@Component
public class SellerProductUtils
{

    public int getMaxCollisions(SellerEntity seller)
    {
        return seller.getPro() ? Utils.MAX_COLLISIONS_ALLOWED_PRO : Utils.MAX_COLLISIONS_ALLOWED;
    }

    public boolean datePeriodCollide(LocalDate startDate1, LocalDate endDate1, LocalDate startDate2, LocalDate endDate2)
    {
        if (startDate1 == null || endDate1 == null || startDate2 == null || endDate2 == null)
            return false;
        return ((startDate1.isBefore(startDate2) && endDate1.isAfter(startDate2)) ||   // If the period starts before the other period and ends after the other period
                (startDate2.isBefore(startDate1) && endDate2.isAfter(startDate1)) ||  // If the other period starts before this period and ends after this period
                startDate1.isEqual(startDate2));                                   // If the periods start at the same time
    }

    public long daysBetween(LocalDate startDate, LocalDate endDate)
    {
        return ChronoUnit.DAYS.between(startDate, endDate);
    }

    public BigDecimal getOfferPrice(BigDecimal price, int discount)
    {
        BigDecimal discountBigDecimal = BigDecimal.valueOf(discount).divide(BigDecimal.valueOf(100));
        BigDecimal offerPrice = price.subtract(price.multiply(discountBigDecimal));

        return offerPrice.setScale(2, RoundingMode.HALF_UP);
    }

    public int getDiscount(BigDecimal price, BigDecimal offerPrice) {
        if (price == null || offerPrice == null) {
            throw new IllegalArgumentException("Price and offer price must not be null.");
        }
        if (offerPrice.compareTo(price) >= 0)
            return 0;
        BigDecimal discount = price.subtract(offerPrice);
        return discount.multiply(BigDecimal.valueOf(100)).divide(price, 2, RoundingMode.HALF_UP).intValue();
    }
}
