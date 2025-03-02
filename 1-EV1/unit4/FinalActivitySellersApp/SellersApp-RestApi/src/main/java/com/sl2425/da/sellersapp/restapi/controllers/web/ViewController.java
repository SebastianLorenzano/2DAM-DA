package com.sl2425.da.sellersapp.restapi.controllers.web;

import com.sl2425.da.sellersapp.restapi.model.Entities.SellerEntity;
import com.sl2425.da.sellersapp.restapi.model.codeStatus.LoginCodeStatus;
import com.sl2425.da.sellersapp.restapi.model.dto.SellerLoginDTO;
import com.sl2425.da.sellersapp.restapi.services.SellerServices;
import org.apache.commons.lang3.tuple.Pair;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import java.util.Optional;

@Controller
@RequestMapping("/web")
public class ViewController
{
    @Autowired
    private SellerServices sellerServices;

    @GetMapping({"/", "/", "/index.html"})
    public String index()
    {
        return "index";
    }

    @GetMapping({"/login", "/login.html"})
    public String showLogin(Model model)
    {
        model.addAttribute("sellerDTO", new SellerLoginDTO());
        return "login";
    }



    private Pair<Optional<SellerEntity>, LoginCodeStatus> getSellerByCif(String cif)
    {
        return sellerServices.getSellerByCif(cif);
    }


}
