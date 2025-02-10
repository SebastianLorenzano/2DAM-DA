package com.sl2425.da.sellersapp.restapi.services;

import com.sl2425.da.sellersapp.Model.Entities.ProductEntity;
import com.sl2425.da.sellersapp.restapi.model.dao.IProductEntityDAO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProductServices
{
    @Autowired
    private IProductEntityDAO productDAO;

    public List<ProductEntity> getProducts(String cif, Integer categoryId, boolean remainingProducts)
    {
        if (cif == null || categoryId == null)
            return null;
        return (remainingProducts)
                ? productDAO.selectAvailableProducts(cif, categoryId)
                : productDAO.selectAvailableProducts(cif, categoryId);
                //: productDAO.findByCategoryId(cif, categoryId);
    }
}
