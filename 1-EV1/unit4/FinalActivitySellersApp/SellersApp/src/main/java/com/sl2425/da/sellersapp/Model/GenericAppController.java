package com.sl2425.da.sellersapp.Model;

import com.sl2425.da.sellersapp.Model.Entities.SellerEntity;

import java.util.logging.Logger;

public class GenericAppController
{
    protected static DatabaseManager database;
    protected static SellerEntity currentSeller;

    public GenericAppController()
    {
    }

    public GenericAppController(DatabaseManager database, SellerEntity currentSeller)
    {
        GenericAppController.database = database;
        GenericAppController.currentSeller = currentSeller;
    }



}
