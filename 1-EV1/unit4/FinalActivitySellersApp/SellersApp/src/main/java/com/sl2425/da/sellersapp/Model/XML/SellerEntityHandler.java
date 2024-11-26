package com.sl2425.da.sellersapp.Model.XML;

import com.sl2425.da.sellersapp.Model.Entities.SellerEntity;
import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

public class SellerEntityHandler extends DefaultHandler {

    private SellerEntity seller;
    private StringBuilder currentValue = new StringBuilder();

    public SellerEntity getSeller() {
        return seller;
    }

    @Override
    public void startDocument() throws SAXException {
        seller = new SellerEntity(); // Initialize the SellerEntity object when the document starts
    }

    @Override
    public void startElement(String uri, String localName, String qName, Attributes attributes) throws SAXException {
        currentValue.setLength(0); // Clear the current value buffer when starting a new element
    }

    @Override
    public void characters(char[] ch, int start, int length) throws SAXException {
        currentValue.append(ch, start, length); // Collect characters inside the element
    }

    @Override
    public void endElement(String uri, String localName, String qName) throws SAXException {
        // Set the values to the seller entity based on the XML element's name
        switch (qName) {
            case "cif":
                seller.setCif(currentValue.toString());
                break;
            case "name":
                seller.setName(currentValue.toString());
                break;
            case "bname":
                seller.setBusinessName(currentValue.toString());
                break;
            case "phone":
                seller.setPhone(currentValue.toString());
                break;
            case "email":
                seller.setEmail(currentValue.toString());
                break;
            case "password":
                seller.setPassword(currentValue.toString()); // Note: be careful with passwords in real applications
                break;
        }
    }

    @Override
    public void endDocument() throws SAXException {
        // Document parsing has finished
    }
}
