package com.sl2425.da.sellersapp.restapi.controllers;

import com.sl2425.da.sellersapp.Model.Entities.CategoryEntity;
import com.sl2425.da.sellersapp.Model.Entities.ProductEntity;
import com.sl2425.da.sellersapp.Model.Entities.SellerEntity;
import com.sl2425.da.sellersapp.Model.Entities.SellerProductEntity;
import com.sl2425.da.sellersapp.restapi.model.Utils;
import com.sl2425.da.sellersapp.restapi.model.codeStatus.LoginCodeStatus;
import com.sl2425.da.sellersapp.restapi.model.codeStatus.SellerCodeStatus;
import com.sl2425.da.sellersapp.restapi.model.codeStatus.SellerProductCodeStatus;
import com.sl2425.da.sellersapp.restapi.model.dao.ICategoryEntityDAO;
import com.sl2425.da.sellersapp.restapi.model.dao.IProductEntityDAO;
import com.sl2425.da.sellersapp.restapi.model.dao.ISellerEntityDAO;
import com.sl2425.da.sellersapp.restapi.model.dao.ISellerProductEntityDAO;
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

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

@Controller
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

    @GetMapping({"/web/sellers/save", "/web/sellers-save.html"} )
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

    @PutMapping({"/web/sellers/save", "/web/sellers-save.html"})
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


    @GetMapping({"/web/sellerProducts/post", "/web/sellerProducts-post.html"})
    public String showSellerProductsPost(@AuthenticationPrincipal UserDetails user, Model model,
                                         @RequestParam(name = "category", required = false, defaultValue = "0") int categoryId,
                                         @RequestParam(name = "productId", required = false, defaultValue = "0") int productId)
    {
        Pair<Optional<SellerEntity>, LoginCodeStatus> pair = getSellerByCif(user.getUsername());
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

    @PostMapping({"/web/sellerProducts/post", "/web/sellerProducts-post.html"})
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

    @GetMapping({"/web/sellerProducts/addOffer", "/web/sellerProducts-addOffer.html"})
    public String showSellerProductsAddOffer(@AuthenticationPrincipal UserDetails user, Model model,
                                             @RequestParam(name = "sellerProductId", required = false, defaultValue = "0") int sellerProductId)
    {
        Pair<Optional<SellerEntity>, LoginCodeStatus> pair = getSellerByCif(user.getUsername());
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

    @PutMapping({"/web/sellerProducts/addOffer", "/web/sellerProducts-addOffer.html"})
    public String addOffer(@Valid @ModelAttribute("sellerProductDTO") SellerProductDTO sellerProductDTO,
                                    BindingResult bindingResult, Model model) {
        SellerEntity seller = sellerServices.getSellerByCif(sellerProductDTO.getCif()).getLeft().orElse(null);
        if (seller == null) {
            System.out.println("Seller not found");
            model.addAttribute("error", "Seller not found");
            return "index";
        }
        List<SellerProductEntity> sellerProducts = sellerProductServices.findAllSellerProductsBySeller(seller);
        model.addAttribute("sellerProducts", sellerProducts); // Ensure it's always populated

        if (bindingResult.hasErrors()) {
            List<String> validationErrors = bindingResult.getAllErrors().stream()
                    .map(error -> error.getDefaultMessage())
                    .collect(Collectors.toList());
            model.addAttribute("errors", validationErrors);
            model.addAttribute("sellerProductDTO", sellerProductDTO);
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
                case SUCCESS -> model.addAttribute("success", "Seller Product's offer saved successfully!");
            }
        }
        SellerProductDTO newSellerProductDTO = new SellerProductDTO();
        sellerProductDTO.setCif(sellerProductDTO.getCif());
        model.addAttribute("sellerProductDTO", newSellerProductDTO);
        return "sellerProducts-addOffer";
    }



    private Pair<Optional<SellerEntity>, LoginCodeStatus> getSellerByCif(String cif)
    {
        return sellerServices.getSellerByCif(cif);
    }


}
