package com.sl2425.da.sellersapp.restapi.controllers.web;


import com.sl2425.da.sellersapp.Model.Entities.SellerEntity;
import com.sl2425.da.sellersapp.Model.Entities.SellerProductEntity;
import com.sl2425.da.sellersapp.restapi.model.Utils;
import com.sl2425.da.sellersapp.restapi.model.codeStatus.LoginCodeStatus;
import com.sl2425.da.sellersapp.restapi.model.codeStatus.SellerProductCodeStatus;
import com.sl2425.da.sellersapp.restapi.model.dto.SellerProductDTO;
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
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

@Controller
@RequestMapping("/web")
public class SellerProductAddOfferWebController
{
    @Autowired
    private SellerServices sellerServices;
    @Autowired
    private SellerProductServices sellerProductServices;

    @GetMapping({"/sellerProducts/addOffer", "/sellerProducts-addOffer.html"})
    public String showSellerProductsAddOffer(@AuthenticationPrincipal UserDetails user, Model model,
                                             @RequestParam(name = "sellerProductId", required = false, defaultValue = "0") int sellerProductId)
    {
        Pair<Optional<SellerEntity>, LoginCodeStatus> pair = sellerServices.getSellerByCif(user.getUsername());
        if (pair.getLeft().isEmpty())
        {
            model.addAttribute("error", "Seller not found");
            return "index";
        }
        List<SellerProductEntity> sellerProducts = sellerProductServices.findAllSellerProductsBySeller(pair.getLeft().get());
        model.addAttribute("sellerProducts", sellerProducts);

        SellerProductDTO sellerProductDTO = new SellerProductDTO();
        if (sellerProductId != 0)
        {
            SellerProductEntity sellerProduct = sellerProductServices.findSellerProductById(sellerProductId);
            if (sellerProduct == null)
            {
                model.addAttribute("error", "Seller Product not found");
                return "index";
            }
            sellerProductDTO = sellerProductServices.toDTO(sellerProduct);

            if (sellerProduct.getOfferStartDate() != null && sellerProduct.getOfferEndDate() != null && sellerProduct.getOfferPrice() != null)
                if (sellerProduct.getOfferEndDate().isAfter(LocalDate.now()))
                {
                    model.addAttribute("currentOfferStartDate", sellerProduct.getOfferStartDate());
                    model.addAttribute("currentOfferEndDate", sellerProduct.getOfferEndDate());
                    model.addAttribute("currentOfferPrice", sellerProduct.getOfferPrice());
                }
        }
        model.addAttribute("sellerProductDTO", sellerProductDTO);
        return "sellerProducts-addOffer";
    }

    @PutMapping({"/sellerProducts/addOffer", "/sellerProducts-addOffer.html"})
    public String addOffer(@Valid @ModelAttribute("sellerProductDTO") SellerProductDTO sellerProductDTO,
                           BindingResult bindingResult, Model model, RedirectAttributes redirectAttributes) {
        SellerEntity seller = sellerServices.getSellerByCif(sellerProductDTO.getCif()).getLeft().orElse(null);
        if (seller == null) {
            model.addAttribute("error", "Seller not found");
            return "index";
        }
        model.addAttribute("sellerProductDTO", sellerProductDTO);
        List<SellerProductEntity> sellerProducts = sellerProductServices.findAllSellerProductsBySeller(seller);
        model.addAttribute("sellerProducts", sellerProducts); // Ensure it's always populated

        if (bindingResult.hasErrors()) {
            List<String> validationErrors = bindingResult.getAllErrors().stream()
                    .map(error -> error.getDefaultMessage())
                    .collect(Collectors.toList());
            model.addAttribute("errors", validationErrors);
            sellerProducts = sellerProductServices.findAllSellerProductsBySeller(seller);
            model.addAttribute("sellerProducts", sellerProducts); // Ensure it's always popula
            return "sellerProducts-addOffer";
        }

        Set<SellerProductCodeStatus> statuses = sellerProductServices.saveSellerProduct(sellerProductDTO, Utils.HttpRequests.PUT);
        for (SellerProductCodeStatus status : statuses) {
            switch (status) {
                case PRODUCT_NOT_FOUND -> model.addAttribute("errors", List.of("Product not found"));
                case SELLER_PRODUCT_IS_NULL -> model.addAttribute("errors", List.of("Seller Product is null"));
                case SELLER_PRODUCT_NOT_FOUND -> model.addAttribute("errors", List.of("Seller Product already exists"));
                case PRICE_NOT_VALID -> model.addAttribute("errors", List.of("Price is not valid"));
                case STOCK_NOT_VALID -> model.addAttribute("errors", "Stock is not valid");
                case DATE_PERIOD_NULL -> model.addAttribute("errors", "Date period is null");
                case OFFER_PRICE_NULL -> model.addAttribute("errors", "Offer price is null");
                case OFFER_PRICE_NOT_VALID -> model.addAttribute("errors", "Offer price is not valid");
                case OFFER_PRICE_TOO_HIGH -> model.addAttribute("errors", "Offer price is too high");
                case DATE_PERIOD_NOT_PRESENT_OR_FUTURE -> model.addAttribute("errors", "Date period is not present or future");
                case DATE_PERIOD_TOO_LONG -> model.addAttribute("errors", "Date period is too long");
                case DATE_PERIOD_NOT_AVAILABLE -> model.addAttribute("errors", "Date period is already taken. Please choose another date period.");
                case SUCCESS -> {
                    model.addAttribute("currentOfferStartDate", sellerProductDTO.getOfferStartDate());
                    model.addAttribute("currentOfferEndDate", sellerProductDTO.getOfferEndDate());
                    model.addAttribute("currentOfferPrice", sellerProductServices.getOfferPrice(sellerProductDTO.getPrice(), sellerProductDTO.getDiscount()));
                    model.addAttribute("success", "Seller Product's offer saved successfully!");
                }
            }
        }

        return "sellerProducts-addOffer";
    }
}
