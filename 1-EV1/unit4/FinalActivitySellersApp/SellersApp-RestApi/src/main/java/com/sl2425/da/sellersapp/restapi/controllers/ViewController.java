package com.sl2425.da.sellersapp.restapi.controllers;

import com.sl2425.da.sellersapp.Model.Entities.CategoryEntity;
import com.sl2425.da.sellersapp.Model.Entities.ProductEntity;
import com.sl2425.da.sellersapp.Model.Entities.SellerEntity;
import com.sl2425.da.sellersapp.restapi.model.codeStatus.SellerCodeStatus;
import com.sl2425.da.sellersapp.restapi.model.dao.ICategoryEntityDAO;
import com.sl2425.da.sellersapp.restapi.model.dao.IProductEntityDAO;
import com.sl2425.da.sellersapp.restapi.model.dao.ISellerEntityDAO;
import com.sl2425.da.sellersapp.restapi.model.dao.ISellerProductEntityDAO;
import com.sl2425.da.sellersapp.restapi.model.dto.SellerDTO;
import com.sl2425.da.sellersapp.restapi.model.dto.SellerProductDTO;
import com.sl2425.da.sellersapp.restapi.model.dto.SellerUpdateDTO;
import com.sl2425.da.sellersapp.restapi.services.SellerProductServices;
import com.sl2425.da.sellersapp.restapi.services.SellersServices;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;

import java.util.List;

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

    @GetMapping({"/web/sellers-save", "/web/sellers-save.html"})
    public String showSeller(Model model)
    {
        SellerUpdateDTO sellerUpdateDTO = new SellerUpdateDTO((SellerEntity)sellerDAO.findByCif("admin")); // TODO: Change this to the actual cif
        model.addAttribute("sellerUpdateDTO", sellerUpdateDTO);
        return "sellers-save";
    }


    @PutMapping({"/web/sellers-save", "/web/sellers-save.html"})
    public String saveSeller(SellerUpdateDTO sellerUpdateDTO, Model model)
    {
        SellerCodeStatus status = sellersServices.saveSeller(sellerUpdateDTO);
        if (status == SellerCodeStatus.SELLER_NOT_FOUND)
            model.addAttribute("error", "Seller not found");
        if (status == SellerCodeStatus.PASSWORDS_DO_NOT_MATCH)
            model.addAttribute("error", "Passwords do not Match");
        if (status == SellerCodeStatus.SUCCESS)
            model.addAttribute("success", "Seller updated successfully!");
        model.addAttribute("sellerUpdateDTO", sellerUpdateDTO);
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
