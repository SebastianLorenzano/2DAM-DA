package com.sl2425.da.sellersapp.Model.XML;

import org.w3c.dom.Document;
import org.w3c.dom.Element;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

import com.sl2425.da.sellersapp.Model.Entities.SellerEntity;

public class XMLSaver
{


    public static void saveSellerEntityToXml(SellerEntity seller, String filePath) {

        if (seller == null) {
            System.out.println("Seller is null, cannot save to XML");
            return;
        }

        try (FileWriter fileWriter = new FileWriter(filePath)) {
            fileWriter.write("<?xml version=\"1.0\" encoding=\"UTF-8\"?>\n");
            fileWriter.write("<seller>\n");

            if (seller.getCif() != null)
                fileWriter.write("    <cif>" + seller.getCif() + "</cif>\n");
            if (seller.getName() != null)
                fileWriter.write("    <name>" + seller.getName() + "</name>\n");
            if (seller.getBusinessName() != null)
                fileWriter.write("    <bname>" + seller.getBusinessName() + "</bname>\n");
            if (seller.getPhone() != null)
                fileWriter.write("    <phone>" + seller.getPhone() + "</phone>\n");
            if (seller.getEmail() != null)
                fileWriter.write("    <email>" + seller.getEmail() + "</email>\n");
            if (seller.getPassword() != null)
                fileWriter.write("    <password>" + seller.getPassword() + "</password>\n");
            fileWriter.write("</seller>\n");
            System.out.println("XML saved successfully to " + filePath);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    // Method to convert SellerEntity to XML and save it to a file using DOM
    public static void saveSellerEntityToXmlWithDOM(SellerEntity seller, String filePath) {
        if (seller == null)
        {
            System.out.println("Seller is null, cannot save to XML");
            return;
        }

        try {
            // Create a new DocumentBuilderFactory and DocumentBuilder
            DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
            DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();

            // Create a new Document
            Document doc = dBuilder.newDocument();

            // Root element
            Element rootElement = doc.createElement("seller");
            doc.appendChild(rootElement);

            // Create child elements and append them to the root
            if (seller.getCif() != null) {
                Element cif = doc.createElement("cif");
                cif.appendChild(doc.createTextNode(seller.getCif()));
                rootElement.appendChild(cif);
            }

            if (seller.getName() != null) {
                Element name = doc.createElement("name");
                name.appendChild(doc.createTextNode(seller.getName()));
                rootElement.appendChild(name);
            }

            if (seller.getBusinessName() != null) {
                Element bname = doc.createElement("bname");
                bname.appendChild(doc.createTextNode(seller.getBusinessName()));
                rootElement.appendChild(bname);
            }

            if (seller.getPhone() != null) {
                Element phone = doc.createElement("phone");
                phone.appendChild(doc.createTextNode(seller.getPhone()));
                rootElement.appendChild(phone);
            }

            if (seller.getEmail() != null) {
                Element email = doc.createElement("email");
                email.appendChild(doc.createTextNode(seller.getEmail()));
                rootElement.appendChild(email);
            }

            if (seller.getPassword() != null) {
                Element password = doc.createElement("password");
                password.appendChild(doc.createTextNode(seller.getPassword()));
                rootElement.appendChild(password);
            }

            // Write the content into XML file
            TransformerFactory transformerFactory = TransformerFactory.newInstance();
            Transformer transformer = transformerFactory.newTransformer();
            DOMSource domSource = new DOMSource(doc);
            StreamResult streamResult = new StreamResult(new File(filePath));

            transformer.transform(domSource, streamResult);

            System.out.println("XML saved successfully to " + filePath);

        } catch (ParserConfigurationException | TransformerException e) {
            e.printStackTrace();
        }
    }
}
