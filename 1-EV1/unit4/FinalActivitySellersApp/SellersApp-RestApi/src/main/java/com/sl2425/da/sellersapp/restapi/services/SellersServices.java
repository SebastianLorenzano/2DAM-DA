package com.sl2425.da.sellersapp.restapi.services;

import com.sl2425.da.sellersapp.Model.Entities.SellerEntity;
import com.sl2425.da.sellersapp.restapi.model.Utils;
import com.sl2425.da.sellersapp.restapi.model.codeStatus.LoginCodeStatus;
import com.sl2425.da.sellersapp.restapi.model.codeStatus.SellerCodeStatus;
import com.sl2425.da.sellersapp.restapi.model.dao.ISellerEntityDAO;
import com.sl2425.da.sellersapp.restapi.model.dto.SellerDTO;
import com.sl2425.da.sellersapp.restapi.model.dto.SellerUpdateDTO;
import org.apache.commons.lang3.tuple.Pair;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.ui.Model;

import java.util.HashSet;
import java.util.Set;

@Service
public class SellersServices
{

    @Autowired
    private ISellerEntityDAO sellerDAO;


    public Pair<SellerEntity, LoginCodeStatus> getSellerByCifAndPassword(SellerDTO sellerDTO)
    {
        SellerEntity seller = sellerDAO.findByCif(sellerDTO.getCif());
        if (seller == null)
            return Pair.of(null, LoginCodeStatus.CIF_NOT_FOUND);
        if (!seller.getPassword().equals(sellerDTO.getPassword()))
            return Pair.of(null, LoginCodeStatus.INCORRECT_PASSWORD);
        return Pair.of(seller, LoginCodeStatus.SUCCESS);
    }

    public Set<SellerCodeStatus> saveSeller(SellerUpdateDTO sellerDTO)
    {
        Set<SellerCodeStatus> errors = new HashSet<>();
        SellerEntity existingSeller = sellerDAO.findByCif(sellerDTO.getCif());
        if (existingSeller == null)
            errors.add(SellerCodeStatus.SELLER_NOT_FOUND);

        SellerEntity updatedSeller = sellerDTO.toSellerEntity();
        updatedSeller.setId(existingSeller.getId());

        if (!sellerDTO.wasPasswordChanged())
            updatedSeller.setPassword(existingSeller.getPassword());
        else if (!sellerDTO.isPasswordConfirmed())
            errors.add(SellerCodeStatus.PASSWORDS_DO_NOT_MATCH);

        updatedSeller.setPlainPassword(existingSeller.getPlainPassword()); // Remove on production
        if (errors.isEmpty())
        {
            sellerDAO.save(updatedSeller);
            errors.add(SellerCodeStatus.SUCCESS);
        }
        return errors;
    }
}
