package com.sl2425.da.sellersapp.restapi.controllers.web;

import com.sl2425.da.sellersapp.restapi.model.Entities.CategoryEntity;
import com.sl2425.da.sellersapp.restapi.model.Entities.ProductEntity;
import com.sl2425.da.sellersapp.restapi.model.Entities.SellerEntity;
import com.sl2425.da.sellersapp.restapi.model.Utils;
import com.sl2425.da.sellersapp.restapi.model.codeStatus.LoginCodeStatus;
import com.sl2425.da.sellersapp.restapi.model.codeStatus.SellerProductCodeStatus;
import com.sl2425.da.sellersapp.restapi.model.dto.SellerProductDTO;
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

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;


@Controller
@RequestMapping("/web")
public class SellerProductSaveWebController
{
    @Autowired
    private ProductServices productServices;
    @Autowired
    CategoryServices categoryServices;
    @Autowired
    private SellerServices sellerServices;
    @Autowired
    private SellerProductServices sellerProductServices;


    @GetMapping({"/sellerProducts/post", "/sellerProducts-post.html"})
    public String showSellerProductsPost(@AuthenticationPrincipal UserDetails user, Model model,
                                         @RequestParam(name = "category", required = false, defaultValue = "0") int categoryId,
                                         @RequestParam(name = "productId", required = false, defaultValue = "0") int productId)
    {
        Pair<Optional<SellerEntity>, LoginCodeStatus> pair = sellerServices.getSellerByCif(user.getUsername());
        if (pair.getLeft().isEmpty())
        {
            model.addAttribute("error", "Seller not found");
            return "index";
        }

        List<CategoryEntity> categories = categoryServices.getAllCategories();
        model.addAttribute("categories", categories);

        List<ProductEntity> products = new ArrayList<>();
        if (categoryId != 0) {
            products = productServices.getProducts(user.getUsername(), categoryId, true);
            model.addAttribute("selectedCategory", categoryId);
        }

        SellerProductDTO sellerProductDTO = new SellerProductDTO();
        sellerProductDTO.setCif(user.getUsername());
        if (productId != 0)
        {
            sellerProductDTO.setProductId(productId);
        }
        model.addAttribute("products", products);
        model.addAttribute("sellerProductDTO", sellerProductDTO);
        return "sellerProducts-post";

    }

    @PostMapping({"/sellerProducts/post", "/sellerProducts-post.html"})
    public String saveSellerProduct(@Valid @ModelAttribute("sellerProductDTO") SellerProductDTO sellerProductDTO,
                                    BindingResult bindingResult, Model model) {
        List<CategoryEntity> categories = categoryServices.getAllCategories();
        model.addAttribute("categories", categories); // Ensure it's always populated

        if (bindingResult.hasErrors()) {
            List<String> validationErrors = bindingResult.getAllErrors().stream()
                    .map(error -> error.getDefaultMessage())
                    .collect(Collectors.toList());
            validationErrors.remove("Discount percentage must be greater than 0"); // Remove discount error message, because it's not required
            if (!validationErrors.isEmpty()) {
                model.addAttribute("errors", validationErrors);
                model.addAttribute("sellerProductDTO", sellerProductDTO);
                return "sellerProducts-post";
            }
        }

        Set<SellerProductCodeStatus> statuses = sellerProductServices.saveSellerProduct(sellerProductDTO, Utils.HttpRequests.POST);
        for (SellerProductCodeStatus status : statuses) {
            switch (status) {
                case PRODUCT_NOT_FOUND -> model.addAttribute("errors", List.of("Product not found"));
                case SELLER_PRODUCT_IS_NULL -> model.addAttribute("errors", List.of("Seller Product is null"));
                case SELLER_PRODUCT_ALREADY_EXISTS -> model.addAttribute("errors", List.of("Seller Product already exists"));
                case PRICE_NOT_VALID -> model.addAttribute("errors", List.of("Price is not valid"));
                case STOCK_NOT_VALID -> model.addAttribute("errors", "Stock is not valid");
                case SUCCESS -> model.addAttribute("success", "Seller Product saved successfully!");
            }
        }
        SellerProductDTO newSellerProductDTO = new SellerProductDTO();
        sellerProductDTO.setCif(sellerProductDTO.getCif());
        model.addAttribute("sellerProductDTO", newSellerProductDTO);
        return "sellerProducts-post";
    }
}
