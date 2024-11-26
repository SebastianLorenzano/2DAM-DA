package com.sl2425.da.sellersapp.Model.XML;

import com.sl2425.da.sellersapp.Model.Entities.SellerEntity;
import com.sl2425.da.sellersapp.Model.RememberObj;
import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

public class RememberObjHandler extends DefaultHandler
{
    private RememberObj rememberObj;
    private StringBuilder currentValue = new StringBuilder();

    public RememberObj getRememberObj()
    {
        return rememberObj;
    }

    @Override
    public void startDocument() throws SAXException
    {
        rememberObj = new RememberObj(); // Initialize the SellerEntity object when the document starts
    }

    @Override
    public void startElement(String uri, String localName, String qName, Attributes attributes) throws SAXException
    {
        currentValue.setLength(0); // Clear the current value buffer when starting a new element
    }

    @Override
    public void characters(char[] ch, int start, int length) throws SAXException
    {
        currentValue.append(ch, start, length); // Collect characters inside the element
    }

    @Override
    public void endElement(String uri, String localName, String qName) throws SAXException
    {
        // Set the values to the seller entity based on the XML element's name
        switch (qName)
        {
            case "cif":
                rememberObj.setCif(currentValue.toString());
                break;
            case "remember":
                String rememberString = currentValue.toString();
                if (rememberString.equals("true"))
                    rememberObj.setRemember("true");
                else
                    rememberObj.setRemember("false");
                break;
        }
    }

    @Override
    public void endDocument() throws SAXException
    {
        // Document parsing has finished
    }
}


