package com.sl2425.da.sellersapp.restapi.controllers;

import com.sl2425.da.sellersapp.Model.Entities.SellerEntity;
import com.sl2425.da.sellersapp.restapi.model.dao.ICategoryEntityDAO;
import com.sl2425.da.sellersapp.restapi.model.dao.IProductEntityDAO;
import com.sl2425.da.sellersapp.restapi.model.dao.ISellerEntityDAO;
import com.sl2425.da.sellersapp.restapi.model.dao.ISellerProductEntityDAO;
import com.sl2425.da.sellersapp.restapi.model.dto.SellerDTO;
import com.sl2425.da.sellersapp.restapi.model.dto.SellerUpdateDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;

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
        SellerUpdateDTO sellerDTO = new SellerUpdateDTO((SellerEntity)sellerDAO.findByCif("admin")); // TODO: Change this to the actual cif
        model.addAttribute("sellerDTO", sellerDTO);
        return "sellers-save";
    }

    @PutMapping({"/web/sellers-save", "/web/sellers-save.html"})
    public String saveSeller(SellerUpdateDTO sellerDTO, Model model)
    {
        SellerEntity existingSeller = sellerDAO.findByCif(sellerDTO.getCif());
        if (existingSeller != null)  // TODO: Move it to server and check that the changes are valid
        {
            SellerEntity updatedSeller = sellerDTO.toSellerEntity();
            updatedSeller.setId(existingSeller.getId());
            if (!sellerDTO.wasPasswordChanged())
                updatedSeller.setPassword(existingSeller.getPassword());
            else if (!sellerDTO.isPasswordConfirmed())
            {
                model.addAttribute("error", "Passwords do not match");
                model.addAttribute("sellerDTO", sellerDTO);
                return "sellers-save";
            }
            else
            updatedSeller.setPlainPassword(existingSeller.getPlainPassword()); // Remove on production
            sellerDAO.save(updatedSeller);
            model.addAttribute("success", "Seller updated successfully!");
        }
        else {
            model.addAttribute("error", "Seller not found");
        }
        model.addAttribute("sellerDTO", sellerDTO);
        return "sellers-save";
    }

}
