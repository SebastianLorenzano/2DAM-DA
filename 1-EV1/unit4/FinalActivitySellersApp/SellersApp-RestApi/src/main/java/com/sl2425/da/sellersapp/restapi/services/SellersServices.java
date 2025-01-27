package com.sl2425.da.sellersapp.restapi.services;

import com.sl2425.da.sellersapp.Model.Entities.SellerEntity;
import com.sl2425.da.sellersapp.restapi.model.Utils;
import com.sl2425.da.sellersapp.restapi.model.codeStatus.SellerCodeStatus;
import com.sl2425.da.sellersapp.restapi.model.dao.ISellerEntityDAO;
import com.sl2425.da.sellersapp.restapi.model.dto.SellerUpdateDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.ui.Model;

@Service
public class SellersServices
{

    @Autowired
    private ISellerEntityDAO sellerDAO;


    public SellerCodeStatus saveSeller(SellerUpdateDTO sellerDTO)
    {
        SellerEntity existingSeller = sellerDAO.findByCif(sellerDTO.getCif());
        if (existingSeller == null)
            return SellerCodeStatus.SELLER_NOT_FOUND;

        SellerEntity updatedSeller = sellerDTO.toSellerEntity();
        updatedSeller.setId(existingSeller.getId());

        if (!sellerDTO.wasPasswordChanged())
            updatedSeller.setPassword(existingSeller.getPassword());
        else if (!sellerDTO.isPasswordConfirmed())
            return SellerCodeStatus.PASSWORDS_DO_NOT_MATCH;

        updatedSeller.setPlainPassword(existingSeller.getPlainPassword()); // Remove on production
        sellerDAO.save(updatedSeller);
        return SellerCodeStatus.SUCCESS;
    }
}
