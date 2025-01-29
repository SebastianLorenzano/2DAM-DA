package com.sl2425.da.sellersapp.restapi.services;

import com.sl2425.da.sellersapp.Model.Entities.SellerEntity;
import com.sl2425.da.sellersapp.restapi.model.codeStatus.SellerCodeStatus;

import java.util.HashSet;
import java.util.Objects;
import java.util.Set;
import java.util.regex.Pattern;

public class SellerValidations
{

    public static Set<SellerCodeStatus> isNewSellerValid(SellerEntity oldSeller, SellerEntity newSeller)
    {
        Set<SellerCodeStatus> result = new HashSet<>();
        if (isSellerNull(newSeller))
        {
            result.add(SellerCodeStatus.SELLER_IS_NULL);
            return result;                                      // Return early to avoid Null Pointer Exception
        }
        if (!isNameValid(newSeller))
            result.add(SellerCodeStatus.INVALID_NAME);
        if (!isBusinessNameValid(newSeller))
            result.add(SellerCodeStatus.INVALID_BUSINESS_NAME);
        if (!isPhoneValid(newSeller))
            result.add(SellerCodeStatus.INVALID_PHONE);
        if (!isEmailValid(newSeller))
            result.add(SellerCodeStatus.INVALID_EMAIL);
        if (!isPasswordValid(newSeller))
            result.add(SellerCodeStatus.INVALID_PASSWORD);
        if (!isUrlValid(newSeller))
            result.add(SellerCodeStatus.INVALID_URL);
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

    private static boolean isNameValid(SellerEntity seller)
    {
        return (seller.getName() != null && !seller.getName().trim().isEmpty() &&
                seller.getName().matches("^[A-Za-zÀ-ÖØ-öø-ÿ0-9 .,*&@#$%^()!?'-]{1,150}$") &&
                seller.getName().length() <= 150);
    }

    private static boolean isBusinessNameValid(SellerEntity seller)
    {
        return (seller.getBusinessName() != null && !seller.getBusinessName().trim().isEmpty() &&
                seller.getBusinessName().matches("^[A-Za-zÀ-ÖØ-öø-ÿ0-9 .,*&@#$%^()!?'-]{1,150}$") &&
                seller.getBusinessName().length() <= 150);
    }

    private static boolean isPhoneValid(SellerEntity seller) {
        return seller.getPhone() != null && (
                seller.getPhone().trim().isEmpty() ||  // Allows empty phone numbers (but not null)
                        seller.getPhone().matches(
                                "^(\\+\\d{1,4}[ -]?)?" +        // Optional country code: +1, +34, +44, etc.
                                "(\\(?\\d{1,4}\\)?[ -]?)?" +    // Optional area code: (123), (44), etc.
                                "\\d{3,4}([ -]?\\d{2,4})*$"     // Main number with optional separators
                        )
        );
    }

    private static boolean isEmailValid(SellerEntity seller)
    {
        return (seller.getEmail() != null && seller.getEmail().trim().isEmpty() ||
                seller.getEmail().matches("^[A-Za-z0-9._%+-]{1,64}@[A-Za-z0-9.-]+\\.[A-Za-z]{2,}$") &&
                seller.getEmail().length() <= 254);
    }

    private static boolean isPasswordValid(SellerEntity seller)
    {
        return (seller.getPassword() != null && !seller.getPassword().trim().isEmpty() &&
                seller.getPassword().matches("^(?=.*[A-Z])(?=.*[a-z])(?=.*[0-9]).{8,}$"));

    }

    private static boolean isUrlValid(SellerEntity seller)
    {
        if (seller.getUrl().trim().isEmpty())
            return true;
        String url = seller.getUrl().trim();

        final String urlRegex = "^(https?:\\/\\/)" +      // Protocol (http/https)
                "((([a-zA-Z0-9-]+\\.)+[a-zA-Z]{2,})|" +   // Domain (e.g., example.com)
                "((\\d{1,3}\\.){3}\\d{1,3}))" +           // OR IP (e.g., 192.168.1.1)
                "(\\:\\d+)?(\\/[^\\s]*)?" +               // Optional port and path
                "(\\?[\\w=&%-]*)?" +                      // Optional query params
                "(\\#[\\w-]*)?$";                         // Optional fragment

        return Pattern.compile(urlRegex).matcher(url).matches();
    }

}
