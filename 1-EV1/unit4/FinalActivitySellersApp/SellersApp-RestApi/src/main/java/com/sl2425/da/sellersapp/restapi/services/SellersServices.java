package com.sl2425.da.sellersapp.restapi.services;

import com.sl2425.da.sellersapp.Model.Entities.SellerEntity;
import com.sl2425.da.sellersapp.restapi.model.codeStatus.LoginCodeStatus;
import com.sl2425.da.sellersapp.restapi.model.codeStatus.SellerCodeStatus;
import com.sl2425.da.sellersapp.restapi.model.dao.ISellerEntityDAO;
import com.sl2425.da.sellersapp.restapi.model.dto.SellerLoginDTO;
import com.sl2425.da.sellersapp.restapi.model.dto.SellerUpdateDTO;
import org.apache.commons.lang3.tuple.Pair;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.Set;

@Service
public class SellersServices
{

    @Autowired
    private ISellerEntityDAO sellerDAO;


    public Pair<SellerEntity, LoginCodeStatus> getSellerByCifAndPassword(SellerLoginDTO sellerLoginDTO)
    {
        SellerEntity seller = sellerDAO.findByCif(sellerLoginDTO.getCif());
        if (seller == null)
            return Pair.of(null, LoginCodeStatus.CIF_NOT_FOUND);
        if (!seller.getPassword().equals(sellerLoginDTO.getPassword()))
            return Pair.of(null, LoginCodeStatus.INCORRECT_PASSWORD);
        return Pair.of(seller, LoginCodeStatus.SUCCESS);
    }

    public Set<SellerCodeStatus> updateSeller(SellerUpdateDTO sellerDTO)
    {
        Set<SellerCodeStatus> statuses = new HashSet<>();
        SellerEntity existingSeller = sellerDAO.findByCif(sellerDTO.getCif());
        if (existingSeller == null)
            statuses.add(SellerCodeStatus.SELLER_NOT_FOUND);


        SellerEntity updatedSeller = sellerDTO.toSellerEntity();
        updatedSeller.setId(existingSeller.getId());

        if (!sellerDTO.wasPasswordChanged())
            updatedSeller.setPassword(existingSeller.getPassword());
        else if (!sellerDTO.isPasswordConfirmed())
            statuses.add(SellerCodeStatus.PASSWORDS_DO_NOT_MATCH);

        updatedSeller.setPlainPassword(existingSeller.getPlainPassword()); // Remove on production
        if (statuses.isEmpty())
        {
            sellerDAO.save(updatedSeller);
            statuses.add(SellerCodeStatus.SUCCESS);
        }
        return statuses;
    }
}
