package com.sl2425.da.sellersapp.Model.XML;

import com.sl2425.da.sellersapp.Model.Entities.SellerEntity;

import java.io.*;

public class ObjectParser
{

    public static void Serialize(SellerEntity seller, String path)
    {
        if (path == null || seller == null)
            throw new IllegalArgumentException();
        File outputFile = new File(path);

        try (FileOutputStream file = new FileOutputStream(outputFile);
             ObjectOutputStream outStream = new ObjectOutputStream(file))
        {
            outStream.writeObject(seller);
        }
        catch (IOException e)
        {
            throw new RuntimeException("Error occurred during serialization", e);
        }
    }

    public static SellerEntity Deserialize(String filePath)
    {
        try (FileInputStream fileIn = new FileInputStream(filePath);
             ObjectInputStream inStream = new ObjectInputStream(fileIn)) {

            return (SellerEntity)inStream.readObject();

        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
            System.err.println("Unable to found a Contact List. Creating one");
            return new SellerEntity();
        }
    }
}
