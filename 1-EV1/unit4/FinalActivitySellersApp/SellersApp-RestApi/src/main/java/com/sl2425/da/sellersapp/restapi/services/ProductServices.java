package com.sl2425.da.sellersapp.restapi.services;

import com.sl2425.da.sellersapp.Model.Entities.ProductEntity;
import com.sl2425.da.sellersapp.Model.Entities.SellerProductEntity;
import com.sl2425.da.sellersapp.restapi.model.dao.IProductEntityDAO;
import com.sl2425.da.sellersapp.restapi.model.dto.SellerContactDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class ProductServices
{
    @Autowired
    private IProductEntityDAO productDAO;

    @Autowired
    private SellerProductServices sellerProductServices;

    public List<ProductEntity> getProducts(String cif, Integer categoryId, boolean remainingProducts)
    {
        if (cif == null || categoryId == null)
            return null;
        return (remainingProducts)
                ? productDAO.selectAvailableProducts(cif, categoryId)
                : productDAO.selectAvailableProducts(cif, categoryId);
                //: productDAO.findByCategoryId(cif, categoryId);
    }

    public List<SellerContactDTO> getSellerContactDTOByProductId(int productId)
    {
        Optional<ProductEntity> product = productDAO.findById(productId);
        if (product.isEmpty())
            return null;
        List<SellerProductEntity> sellerProducts = sellerProductServices.findSellerProductsByProduct(product.get());
        List<SellerContactDTO> result = new ArrayList<SellerContactDTO>();
        for (SellerProductEntity sellerProduct : sellerProducts)
        {
            SellerContactDTO contact = SellerContactDTO.toDTO(sellerProduct.getSeller());
            result.add(contact);
        }
        return result;
    }
}
