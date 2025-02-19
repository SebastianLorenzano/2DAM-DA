package com.sl2425.da.sellersapp.restapi.controllers.web;


import com.sl2425.da.sellersapp.Model.Entities.CategoryEntity;
import com.sl2425.da.sellersapp.Model.Entities.ProductEntity;
import com.sl2425.da.sellersapp.Model.Entities.SellerEntity;
import com.sl2425.da.sellersapp.Model.Entities.SellerProductEntity;
import com.sl2425.da.sellersapp.restapi.model.codeStatus.LoginCodeStatus;
import com.sl2425.da.sellersapp.restapi.model.dto.SellerProductDTO;
import com.sl2425.da.sellersapp.restapi.services.SellerProductServices;
import com.sl2425.da.sellersapp.restapi.services.SellerServices;
import org.apache.commons.lang3.tuple.Pair;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Controller
@RequestMapping("/web")
public class ShowSellerProductsWebController
{

    @Autowired
    SellerServices sellerServices;

    @Autowired
    SellerProductServices sellerProductServices;

    @GetMapping({"/sellerProducts/seller", "/sellerProducts-post.html"})
    public String showSellerProductsPost(@AuthenticationPrincipal UserDetails user, Model model,
                                         @RequestParam(name = "sellerId", required = false, defaultValue = "0") String sellerId)
    {

        List<SellerEntity> sellers = sellerServices.getAllSellers();
        model.addAttribute("sellers", sellers);

        List<SellerProductEntity> sellerProducts = new ArrayList<>();
        if (sellerId != null) {
            Pair<Optional<SellerEntity>, LoginCodeStatus> seller = sellerServices.getSellerByCif(sellerId);
            if (seller.getLeft().isPresent())
            {
                sellerProducts = sellerProductServices.findAllSellerProductsBySeller(seller.getLeft().get());
                model.addAttribute("sellerProducts", sellerProducts);
            }

        }
        return "sellerProducts-seller";

    }

}
