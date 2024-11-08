package com.sl2425.da.sellersapp.Model;

import com.sl2425.da.sellersapp.Model.Entities.SellerEntity;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;


public class Utils
{
    public static SellerEntity currentSeller;

    public static String encryptToMD5(String password)
    {
        try {
            MessageDigest md = MessageDigest.getInstance("MD5");
            md.update(password.getBytes());
            byte[] digest = md.digest();
            StringBuilder sb = new StringBuilder();
            for (byte b : digest)
                sb.append(String.format("%02x", b & 0xff));
            return sb.toString();
        }
        catch (NoSuchAlgorithmException e)
        {
            e.printStackTrace();
            return null;
        }
    }

}
