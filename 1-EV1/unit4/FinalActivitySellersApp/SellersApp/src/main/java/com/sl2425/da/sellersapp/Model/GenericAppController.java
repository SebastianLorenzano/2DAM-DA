package com.sl2425.da.sellersapp.Model;

import com.sl2425.da.sellersapp.Model.Database.DatabaseManager;
import com.sl2425.da.sellersapp.Model.Entities.SellerEntity;

public class GenericAppController
{
    protected static DatabaseManager database;
    protected static SellerEntity currentSeller;

    public static void initialize(DatabaseManager database)
    {
        GenericAppController.database = database;
    }



}
