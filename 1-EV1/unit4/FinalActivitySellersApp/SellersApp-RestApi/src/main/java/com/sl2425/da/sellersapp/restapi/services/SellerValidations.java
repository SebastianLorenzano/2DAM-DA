package com.sl2425.da.sellersapp.restapi.services;

import com.sl2425.da.sellersapp.Model.Entities.SellerEntity;
import com.sl2425.da.sellersapp.restapi.model.codeStatus.SellerCodeStatus;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.util.HashSet;
import java.util.Objects;
import java.util.Set;
import java.util.regex.Pattern;

@Component
public class SellerValidations
{

    @Autowired
    private PasswordEncoder passwordEncoder;


    public Set<SellerCodeStatus> validateSeller(SellerEntity oldSeller, SellerEntity newSeller)
    {
        Set<SellerCodeStatus> result = new HashSet<>();
        if (isSellerNull(newSeller))
        {
            result.add(SellerCodeStatus.SELLER_IS_NULL); // Return early to avoid Null Pointer Exception
            return result;
        }
        if (isSellerEqualToSeller(oldSeller, newSeller))
            result.add(SellerCodeStatus.SELLER_UNCHANGED);
        return result;
    }

    private boolean isSellerEqualToSeller(SellerEntity oldSeller, SellerEntity newSeller) {
        if (!Objects.equals(newSeller.getCif(), oldSeller.getCif())) {
            System.out.println("CIF mismatch: " + newSeller.getCif() + " != " + oldSeller.getCif());
            return false;
        }

        if (!Objects.equals(newSeller.getName(), oldSeller.getName())) {
            System.out.println("Name mismatch: " + newSeller.getName() + " != " + oldSeller.getName());
            return false;
        }

        if (!Objects.equals(newSeller.getBusinessName(), oldSeller.getBusinessName())) {
            System.out.println("Business Name mismatch: " + newSeller.getBusinessName() + " != " + oldSeller.getBusinessName());
            return false;
        }

        System.out.println("Old phone: " + oldSeller.getPhone());
        System.out.println("New phone: " + newSeller.getPhone());
        if (!Objects.equals(normalizePhone(newSeller.getPhone()), normalizePhone(oldSeller.getPhone()))) {
            System.out.println("Phone mismatch: " + normalizePhone(newSeller.getPhone()) + " != " + normalizePhone(oldSeller.getPhone()));
            return false;
        }

        if (!Objects.equals(newSeller.getEmail(), oldSeller.getEmail())) {
            System.out.println("Email mismatch: " + newSeller.getEmail() + " != " + oldSeller.getEmail());
            return false;
        }

        System.out.println("Old password: " + oldSeller.getPassword());
        System.out.println("New password: " + newSeller.getPassword());
        if (!Objects.equals(newSeller.getPassword(), oldSeller.getPassword())) {
            System.out.println("Password mismatch: Provided password does not match the stored password.");
            return false;
        }

        if (!Objects.equals(newSeller.getUrl(), oldSeller.getUrl())) {
            System.out.println("URL mismatch: " + newSeller.getUrl() + " != " + oldSeller.getUrl());
            return false;
        }

        return true; // All checks passed
    }

    private boolean isSellerNull(SellerEntity seller)
    {
        return seller == null;
    }



    private String normalizePhone(String phone) {
        if (phone == null) return null;
        return phone.replaceAll("[\\s\\-()]", "").trim(); // Remove spaces, dashes, parentheses
    }

}
