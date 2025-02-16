package com.sl2425.da.sellersapp.restapi.controllers.web;

import com.sl2425.da.sellersapp.Model.Entities.SellerEntity;
import com.sl2425.da.sellersapp.restapi.model.codeStatus.LoginCodeStatus;
import com.sl2425.da.sellersapp.restapi.model.codeStatus.SellerCodeStatus;
import com.sl2425.da.sellersapp.restapi.model.dto.SellerUpdateDTO;
import com.sl2425.da.sellersapp.restapi.services.SellerServices;
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
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

@Controller
@RequestMapping("/web")
public class SellerWebController
{
    @Autowired
    private SellerServices sellerServices;

    @GetMapping({"/sellers/save", "/sellers-save.html"} )
    public String showSeller(@AuthenticationPrincipal UserDetails user, Model model)
    {
        Pair<Optional<SellerEntity>, LoginCodeStatus> pair = sellerServices.getSellerByCif(user.getUsername());
        if (pair.getLeft().isEmpty())
        {
            model.addAttribute("error", "Seller not found");
            return "index";
        }
        SellerUpdateDTO sellerUpdateDTO = new SellerUpdateDTO(pair.getLeft().get());
        model.addAttribute("SellerUpdateDTO", sellerUpdateDTO);
        return "sellers-save";
    }

    @PutMapping({"/sellers/save", "/sellers-save.html"})
    public String saveSeller(@Valid @ModelAttribute("SellerUpdateDTO") SellerUpdateDTO sellerUpdateDTO,
                             BindingResult bindingResult, Model model)
    {
        if (bindingResult.hasErrors())
        {
            List<String> validationErrors = bindingResult.getAllErrors().stream()
                    .map(error -> error.getDefaultMessage())
                    .collect(Collectors.toList());
            model.addAttribute("errors", validationErrors);
            model.addAttribute("SellerUpdateDTO", sellerUpdateDTO);
            return "sellers-save";
        }
        Set<SellerCodeStatus> updateStatus = sellerServices.updateSeller(sellerUpdateDTO);
        for (SellerCodeStatus status : updateStatus)
        {
            switch (status)
            {
                case SELLER_NOT_FOUND -> model.addAttribute("errors", List.of("Seller not found"));
                case SELLER_UNCHANGED -> model.addAttribute("errors", List.of("There were no changes to update"));
                case PASSWORDS_DO_NOT_MATCH -> model.addAttribute("errors", List.of("Passwords do not match."));
                case SUCCESS -> model.addAttribute("success", "Seller updated successfully!");
            }
        }
        model.addAttribute("SellerUpdateDTO", sellerUpdateDTO);
        return "sellers-save";
    }
}
