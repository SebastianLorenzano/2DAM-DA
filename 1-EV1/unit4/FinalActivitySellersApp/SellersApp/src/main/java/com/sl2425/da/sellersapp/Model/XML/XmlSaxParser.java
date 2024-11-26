package com.sl2425.da.sellersapp.Model.XML;

import com.sl2425.da.sellersapp.Model.Entities.SellerEntity;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;
import org.xml.sax.XMLReader;
import org.xml.sax.helpers.XMLReaderFactory;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;

public class XmlSaxParser {

    public static SellerEntity parseSellerEntityFromXml(String filePath) {
        try {
            // Create an XMLReader instance using SAX
            XMLReader xmlReader = XMLReaderFactory.createXMLReader();

            // Create and set the content handler
            SellerEntityHandler handler = new SellerEntityHandler();
            xmlReader.setContentHandler(handler);

            // Parse the XML file
            FileInputStream fileInputStream = new FileInputStream(new File(filePath));
            xmlReader.parse(new InputSource(fileInputStream));

            // Return the populated SellerEntity
            return handler.getSeller();

        } catch (SAXException | IOException e) {
            e.printStackTrace();
        }

        return null;
    }


    public static void main(String[] args) {
        String filePath = "seller2.xml";

        // Parse the XML to create a SellerEntity
        SellerEntity seller = parseSellerEntityFromXml(filePath);

        if (seller != null) {
            System.out.println("Loaded Seller:");
            System.out.println("CIF: " + seller.getCif());
            System.out.println("Name: " + seller.getName());
            System.out.println("Business Name: " + seller.getBusinessName());
            System.out.println("Phone: " + seller.getPhone());
            System.out.println("Email: " + seller.getEmail());
            System.out.println("Password: " + seller.getPassword());
        }
        XMLDomSaver.saveSellerEntityToXml(seller, "seller2.xml");
    }
}

