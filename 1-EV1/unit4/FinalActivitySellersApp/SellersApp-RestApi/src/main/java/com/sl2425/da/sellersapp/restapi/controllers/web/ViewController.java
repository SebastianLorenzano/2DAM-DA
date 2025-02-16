package com.sl2425.da.sellersapp.restapi.controllers.web;

import com.sl2425.da.sellersapp.Model.Entities.CategoryEntity;
import com.sl2425.da.sellersapp.Model.Entities.ProductEntity;
import com.sl2425.da.sellersapp.Model.Entities.SellerEntity;
import com.sl2425.da.sellersapp.Model.Entities.SellerProductEntity;
import com.sl2425.da.sellersapp.restapi.model.Utils;
import com.sl2425.da.sellersapp.restapi.model.codeStatus.LoginCodeStatus;
import com.sl2425.da.sellersapp.restapi.model.codeStatus.SellerCodeStatus;
import com.sl2425.da.sellersapp.restapi.model.codeStatus.SellerProductCodeStatus;
import com.sl2425.da.sellersapp.restapi.model.dto.SellerLoginDTO;
import com.sl2425.da.sellersapp.restapi.model.dto.SellerProductDTO;
import com.sl2425.da.sellersapp.restapi.model.dto.SellerUpdateDTO;
import com.sl2425.da.sellersapp.restapi.services.CategoryServices;
import com.sl2425.da.sellersapp.restapi.services.ProductServices;
import com.sl2425.da.sellersapp.restapi.services.SellerProductServices;
import com.sl2425.da.sellersapp.restapi.services.SellerServices;
import jakarta.validation.Valid;
import org.apache.commons.lang3.tuple.Pair;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

@Controller
@RequestMapping("/web")
public class ViewController
{
    @Autowired
    private ProductServices productServices;
    @Autowired
    CategoryServices categoryServices;
    @Autowired
    private SellerServices sellerServices;
    @Autowired
    private SellerProductServices sellerProductServices;

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
