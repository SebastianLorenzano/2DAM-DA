package com.sl2425.da.sellersapp.restapi.controllers;

import com.sl2425.da.sellersapp.Model.Entities.SellerEntity;
import com.sl2425.da.sellersapp.restapi.model.codeStatus.LoginCodeStatus;
import com.sl2425.da.sellersapp.restapi.model.codeStatus.SellerCodeStatus;
import com.sl2425.da.sellersapp.restapi.model.dao.ICategoryEntityDAO;
import com.sl2425.da.sellersapp.restapi.model.dao.IProductEntityDAO;
import com.sl2425.da.sellersapp.restapi.model.dao.ISellerEntityDAO;
import com.sl2425.da.sellersapp.restapi.model.dao.ISellerProductEntityDAO;
import com.sl2425.da.sellersapp.restapi.model.dto.SellerLoginDTO;
import com.sl2425.da.sellersapp.restapi.model.dto.SellerUpdateDTO;
import com.sl2425.da.sellersapp.restapi.services.SellerProductServices;
import com.sl2425.da.sellersapp.restapi.services.SellersServices;
import jakarta.validation.Valid;
import org.apache.commons.lang3.tuple.Pair;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;

import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

@Controller
public class ViewController
{

    @Autowired
    private ISellerEntityDAO sellerDAO;
    @Autowired
    private ICategoryEntityDAO categoryDAO;
    @Autowired
    private IProductEntityDAO productDAO;
    @Autowired
    private ISellerProductEntityDAO sellerProductDAO;
    @Autowired
    private SellersServices sellersServices;
    @Autowired
    private SellerProductServices sellerProductServices;

    @GetMapping({"/web/", "/web/index", "/web/index.html"})
    public String index()
    {
        return "index";
    }

    @GetMapping({"/web/login", "/web/login.html"})
    public String showLogin(Model model)
    {
        model.addAttribute("sellerDTO", new SellerLoginDTO());
        return "login";
    }

    @GetMapping({"/web/sellers-save", "/web/sellers-save.html"} )
    public String showSeller(@AuthenticationPrincipal UserDetails user, Model model)
    {
        Pair<Optional<SellerEntity>, LoginCodeStatus> pair = sellersServices.getSellerByCif(user.getUsername());
        if (pair.getLeft().isEmpty())
        {
            model.addAttribute("error", "Seller not found");
            return "index";
        }
        SellerUpdateDTO sellerUpdateDTO = new SellerUpdateDTO(pair.getLeft().get());
        model.addAttribute("SellerUpdateDTO", sellerUpdateDTO);
        return "sellers-save";
    }

    @PutMapping({"/web/sellers-save", "/web/sellers-save.html"})
    public String saveSeller(@Valid @ModelAttribute("SellerUpdateDTO") SellerUpdateDTO sellerUpdateDTO,
                             BindingResult bindingResult, Model model)
    {
        // Handle validation errors
        if (bindingResult.hasErrors()) {
            List<String> validationErrors = bindingResult.getAllErrors().stream()
                    .map(error -> error.getDefaultMessage())
                    .collect(Collectors.toList());

            model.addAttribute("errors", validationErrors);
            return "sellers-save";
        }

        // Check password confirmation
        if (!sellerUpdateDTO.isNewPasswordCorrect()) {
            model.addAttribute("errors", List.of("Passwords do not match"));
            return "sellers-save";
        }

        // Update seller and handle status codes
        Set<SellerCodeStatus> updateStatus = sellersServices.updateSeller(sellerUpdateDTO);
        for (SellerCodeStatus status : updateStatus) {
            switch (status) {
                case SELLER_NOT_FOUND -> model.addAttribute("errors", List.of("Seller not found"));
                case PASSWORDS_DO_NOT_MATCH -> model.addAttribute("errors", List.of("Passwords do not match"));
                case SUCCESS -> model.addAttribute("success", "Seller updated successfully!");
            }
        }

        model.addAttribute("SellerUpdateDTO", sellerUpdateDTO);
        return "sellers-save";
    }

    @GetMapping({"/web/sellerProducts-post", "/web/sellerProducts-post.html"})
    public String showSellerProductsPost(Model model)
    {
        return sellerProductServices.showSellerProductsPost(model);
    }

    @PostMapping({"/web/sellerProducts-post", "/web/sellerProducts-post.html"})
    public String postSellerProduct(SellerUpdateDTO sellerDTO, Model model)
    {
        return sellerProductServices.postSellerProduct(sellerDTO, model);
    }
}
