package com.sl2425.da.sellersapp.restapi.services;

import com.sl2425.da.sellersapp.Model.Entities.SellerProductEntity;
import com.sl2425.da.sellersapp.restapi.model.Utils;
import com.sl2425.da.sellersapp.restapi.model.codeStatus.SellerProductCodeStatus;
import com.sl2425.da.sellersapp.restapi.model.dao.ISellerProductEntityDAO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.sound.midi.SysexMessage;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Component
public class SellerProductValidations
{

    @Autowired
    private ISellerProductEntityDAO sellerProductDAO;
    @Autowired
    private SellerProductUtils spUtils;

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

        if (!isPriceValid(sellerProduct))
            result.add(SellerProductCodeStatus.PRICE_NOT_VALID);

        if (!isStockValid(sellerProduct))
            result.add(SellerProductCodeStatus.STOCK_NOT_VALID);

        if (!dayPeriodNull(sellerProduct))
            result.add(SellerProductCodeStatus.DATE_PERIOD_NOT_NULL);

        if (!offerPriceNull(sellerProduct))
            result.add(SellerProductCodeStatus.OFFER_PRICE_NOT_NULL);
        return result;
    }

    public Set<SellerProductCodeStatus> validateUpdate(SellerProductEntity sellerProduct)
    {
        Set<SellerProductCodeStatus> result = new HashSet<>();
        if (sellerProductNull(sellerProduct))
        {
            result.add(SellerProductCodeStatus.SELLER_PRODUCT_IS_NULL);
            return result;
        }
        if (!sellerProductExists(sellerProduct))
        {
            result.add(SellerProductCodeStatus.SELLER_PRODUCT_NOT_FOUND);
            return result;
        }

        if (dayPeriodNull(sellerProduct))
            result.add(SellerProductCodeStatus.DATE_PERIOD_NULL);

        if (offerPriceNull(sellerProduct))
            result.add(SellerProductCodeStatus.OFFER_PRICE_NULL);

        if (!isOfferPriceValid(sellerProduct))
            result.add(SellerProductCodeStatus.OFFER_PRICE_NOT_VALID);

        if (!isOfferPriceWithinRange(sellerProduct))
            result.add(SellerProductCodeStatus.OFFER_PRICE_TOO_HIGH);

        if (!dayPeriodPresentOrFuture(sellerProduct))
            result.add(SellerProductCodeStatus.DATE_PERIOD_NOT_PRESENT_OR_FUTURE);

        if (!daysBetweenIsWithinRange(sellerProduct))
            result.add(SellerProductCodeStatus.DATE_PERIOD_TOO_LONG);

        if (!dayPeriodAvailable(sellerProduct))
            result.add(SellerProductCodeStatus.DATE_PERIOD_NOT_AVAILABLE);
        for (SellerProductCodeStatus s : result)
            System.out.println(s);
        return result; //NOT COMPLETED

    }

    private boolean isOfferPriceWithinRange(SellerProductEntity sellerProduct)
    {
        BigDecimal minimumOfferPrice = spUtils.getOfferPrice(sellerProduct.getPrice(), getMaxDiscount(sellerProduct));
        return sellerProduct.getOfferPrice().compareTo(minimumOfferPrice) >= 0;
    }


    private boolean daysBetweenIsWithinRange(SellerProductEntity sellerProduct)
    {
        long daysBetween = spUtils.daysBetween(sellerProduct.getOfferStartDate(), sellerProduct.getOfferEndDate());
        return daysBetween <= Utils.OFFER_MAX_DAYS_BETWEEN;
    }

    private boolean sellerProductNull(SellerProductEntity sellerProduct)
    {
        return sellerProduct == null;
    }


    private boolean sellerProductExists(SellerProductEntity sellerProduct)
    {
        return sellerProductDAO.existsBySellerAndProduct(sellerProduct.getSeller(), sellerProduct.getProduct());
    }

    private boolean isStockValid(SellerProductEntity sellerProduct)
    {
        return sellerProduct.getStock() != null && sellerProduct.getStock() >= 0 && sellerProduct.getStock() < Utils.MAX_STOCK;
    }

    private boolean isPriceValid(SellerProductEntity sellerProduct)
    {
        BigDecimal price = sellerProduct.getPrice();
        if (price == null)
            return false;
        int integerPart = price.precision() - price.scale();
        int fractionPart = price.scale();
        return integerPart <= 8 && fractionPart <= 2 && price.compareTo(BigDecimal.ZERO) > 0;
    }

    private boolean offerPriceNull(SellerProductEntity sellerProduct)
    {
        return sellerProduct.getOfferPrice() == null;
    }

    private boolean isOfferPriceValid(SellerProductEntity sellerProduct)
    {
        BigDecimal price = sellerProduct.getOfferPrice();
        if (price == null)
            return false;
        int integerPart = price.precision() - price.scale();
        int fractionPart = price.scale();
        return integerPart <= 8 && fractionPart <= 2 && price.compareTo(BigDecimal.ZERO) > 0;
    }

    private int getMaxDiscount(SellerProductEntity sellerProduct)
    {
        long da = spUtils.daysBetween(sellerProduct.getOfferStartDate(), sellerProduct.getOfferEndDate());

        return (sellerProduct.getSeller().getPro())
                ? getMaxDiscountPro(da)
                : getMaxDiscountNormal(da);
    }

    private int getMaxDiscountNormal(long daysBetween)
    {
        if (daysBetween > 15)
            return 10;
        if (daysBetween > 7)
            return 15;
        if (daysBetween > 3)
            return 20;
        if (daysBetween > 1)
            return 30;
        return 50;
    }

    private int getMaxDiscountPro(long daysBetween)
    {
        if (daysBetween > 7)
            return 20;
        if (daysBetween > 3)
            return 30;
        return 50;
    }

    private boolean dayPeriodNull(SellerProductEntity sellerProduct)
    {
        return (sellerProduct.getOfferStartDate() == null) && (sellerProduct.getOfferEndDate() == null);
    }

    private boolean dayPeriodPresentOrFuture(SellerProductEntity sellerProduct)
    {
        return ( sellerProduct.getOfferStartDate().isEqual(LocalDate.now()) || sellerProduct.getOfferStartDate().isAfter(LocalDate.now()) )
                && sellerProduct.getOfferEndDate().isAfter(LocalDate.now());
    }

    private boolean dayPeriodAvailable(SellerProductEntity sellerProduct)
    {
        if (sellerProduct.getOfferStartDate() == null || sellerProduct.getOfferEndDate() == null)
            return false;
        System.out.println("Checking collisions");
        List<SellerProductEntity> sellerProducts = sellerProductDAO.findAllBySellerId(sellerProduct.getSeller().getId());
        int collisions = 0;
        int maxCollisions = spUtils.getMaxCollisions(sellerProduct.getSeller());
        for (SellerProductEntity s : sellerProducts)
        {
            if (spUtils.datePeriodCollide(sellerProduct.getOfferStartDate(), sellerProduct.getOfferEndDate(),
                    s.getOfferStartDate(), s.getOfferEndDate()))
            {
                System.out.println("Collision found");
                collisions++;
                System.out.println("Total Collisions: " + collisions);
                if (collisions > maxCollisions) {
                    System.out.println("Collisions exceeded");
                    return false;
                }
            }
        }
        System.out.println("Collisions within range");
        System.out.println("Total Collisions: " + collisions);
        return true;
    }




}
