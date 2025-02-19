package com.sl2425.da.sellersapp.restapi.controllers.web;


import com.sl2425.da.sellersapp.Model.Entities.SellerEntity;
import com.sl2425.da.sellersapp.Model.Entities.SellerProductEntity;
import com.sl2425.da.sellersapp.restapi.model.codeStatus.LoginCodeStatus;
import com.sl2425.da.sellersapp.restapi.services.CategoryServices;
import com.sl2425.da.sellersapp.restapi.services.ProductServices;
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
import java.util.Objects;
import java.util.Optional;

@Controller
@RequestMapping("/web")
public class SellerProductListBySellerWebController
{
    @Autowired
    private SellerServices sellerServices;
    @Autowired
    private SellerProductServices sellerProductServices;


    @GetMapping({"/sellerProducts/showProducts"})
    public String showSellerProductsBySeller(@AuthenticationPrincipal UserDetails user, Model model,
                                             @RequestParam(name = "checkValue", required = false, defaultValue = "") String checkValue)
    {
        Pair<Optional<SellerEntity>, LoginCodeStatus> pair = sellerServices.getSellerByCif(user.getUsername());
        if (pair.getLeft().isEmpty())
        {
            model.addAttribute("error", "Seller not found");
            return "index";
        }

        List<SellerProductEntity> sellerProducts = sellerProductServices.findAllSellerProductsBySeller(pair.getLeft().get());
        if (Objects.equals(checkValue, "on"))
        {
            List<SellerProductEntity> sellerProductsWithActiveOffer = new ArrayList<>();
            for (SellerProductEntity sellerProduct : sellerProducts)
            {
                if (sellerProduct.getOfferStartDate() != null &&
                        sellerProduct.getOfferEndDate() != null &&
                        sellerProductServices.dayPeriodPresentOrFuture(sellerProduct))
                    sellerProductsWithActiveOffer.add(sellerProduct);
            }
            model.addAttribute("sellerProducts", sellerProductsWithActiveOffer);
            model.addAttribute("priceName", "Offer price");
            model.addAttribute("checkbox", "on");
        }
        else
        {
            model.addAttribute("sellerProducts", sellerProducts);
            model.addAttribute("priceName", "Price");
            model.addAttribute("checkbox", "off");
        }

        return "sellerProducts-showProducts";
    }
}
