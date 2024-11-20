package com.sl2425.da.sellersapp.Model;

import com.sl2425.da.sellersapp.Model.Entities.SellerEntity;
import javafx.scene.control.Alert;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.time.LocalDate;


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

    public  static void showConfirmation(String message) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Confirmation");
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }

    public static void showError(String message) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Error");
        alert.setHeaderText("Something went wrong");
        alert.setContentText(message);
        alert.showAndWait();
    }

    public static boolean doesDatePeriodCollide(LocalDate startDate1, LocalDate endDate1, LocalDate startDate2, LocalDate endDate2)
    {
        if (startDate1 == null || endDate1 == null || startDate2 == null || endDate2 == null)
            return false;
        return (startDate1.isBefore(endDate2) && endDate1.isAfter(startDate2) ||   // If the period starts before the other period and ends after the other period
                (startDate2.isBefore(endDate1) && endDate2.isAfter(startDate1)) ||  // If the other period starts before this period and ends after this period
                startDate1.isEqual(startDate2));                                   // If the periods start at the same time
    }

    

}
