package com.sl2425.da.sellersapp.restapi.controllers;

import com.sl2425.da.sellersapp.Model.Entities.SellerEntity;
import com.sl2425.da.sellersapp.restapi.model.dao.ICategoryEntityDAO;
import com.sl2425.da.sellersapp.restapi.model.dao.IProductEntityDAO;
import com.sl2425.da.sellersapp.restapi.model.dao.ISellerEntityDAO;
import com.sl2425.da.sellersapp.restapi.model.dao.ISellerProductEntityDAO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class ViewController
{

    @Autowired
    private ICategoryEntityDAO categoryDAO;
    @Autowired
    private IProductEntityDAO productDAO;
    @Autowired
    private ISellerEntityDAO sellerDAO;
    @Autowired
    private ISellerProductEntityDAO sellerProductDAO;

    @GetMapping({"/web/", "/web/index", "/web/index.html"})
    public String index()
    {
        return "index";
    }

    @GetMapping({"/web/sellers-save", "/web/sellers-save.html"})
    public String showSeller(Model model)
    {
        SellerEntity seller = (SellerEntity) sellerDAO.findByCif("admin"); // TODO: Change this to the actual cif
        model.addAttribute("seller", seller);
        return "sellers-save";
    }

    @PostMapping({"/web/sellers-save", "/web/sellers-save.html"})
    public String saveDepartment(SellerEntity seller, Model model)
    {
        SellerEntity seller1 = sellerDAO.findByCif(seller.getCif());
        if (seller1 != null)  // TODO: Move it to server and check that the changes are valid
            seller.setId(seller1.getId());
        System.out.println(seller.getCif());
        sellerDAO.save(seller);
        model.addAttribute("seller", seller);
        return "sellers-save";
    }

}
