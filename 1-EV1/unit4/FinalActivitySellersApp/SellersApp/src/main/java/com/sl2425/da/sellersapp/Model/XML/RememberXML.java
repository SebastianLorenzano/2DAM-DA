package com.sl2425.da.sellersapp.Model.XML;

import com.sl2425.da.sellersapp.Model.Entities.SellerEntity;
import com.sl2425.da.sellersapp.Model.RememberObj;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;
import org.xml.sax.XMLReader;
import org.xml.sax.helpers.XMLReaderFactory;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileWriter;
import java.io.IOException;

public class RememberXML
{
    public static void SerializeXML(RememberObj rememberObj, String filePath) {

        if (rememberObj == null) {
            System.out.println("rememberObj is null, cannot save to XML");
            return;
        }

        try (FileWriter fileWriter = new FileWriter(filePath)) {
            fileWriter.write("<?xml version=\"1.0\" encoding=\"UTF-8\"?>\n");
            fileWriter.write("<rememberObj>\n");

            if (rememberObj.getCif() != null)
                fileWriter.write("    <cif>" + rememberObj.getCif() + "</cif>\n");
            fileWriter.write("    <remember>" + rememberObj.getRemember() + "</remember>\n");
            fileWriter.write("</rememberObj>\n");
            System.out.println("XML saved successfully to " + filePath);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static RememberObj DeserializeXML(String filePath)
    {
        try {
            XMLReader xmlReader = XMLReaderFactory.createXMLReader();
            RememberObjHandler handler = new RememberObjHandler();
            xmlReader.setContentHandler(handler);

            FileInputStream fileInputStream = new FileInputStream(new File(filePath));
            xmlReader.parse(new InputSource(fileInputStream));

            // Return the populated SellerEntity
            return handler.getRememberObj();

        } catch (SAXException | IOException e) {
            e.printStackTrace();
        }

        return null;
    }

}
