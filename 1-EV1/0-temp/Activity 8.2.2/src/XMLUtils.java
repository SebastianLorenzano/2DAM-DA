import org.xml.sax.SAXException;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;
import java.io.*;

public class XMLUtils
{
    public static String XML_FILE = "contacts.xml";

    public static ContactList readXMLContactList(File file)
    {
        if (file == null)
            throw new IllegalArgumentException("File cannot be null");
        try
        {
            SAXParser saxParser = SAXParserFactory.newInstance().newSAXParser();
            ContactListHandler contactListHandler = new ContactListHandler();
            saxParser.parse(file, contactListHandler);
            return contactListHandler.getContactList();
        }
        catch (ParserConfigurationException | IOException | SAXException e)
        {
            System.err.println("Error while parsing XML file: " + e.getMessage());
        }
        return null;
    }

    public static void writeXMLContactList(File file, ContactList contactList)
    {
        if (file == null)
            throw new IllegalArgumentException("File cannot be null");
        if (contactList == null)
            throw new IllegalArgumentException("Contact list cannot be null");
        try (PrintWriter printWriter = new PrintWriter(new BufferedWriter(new FileWriter(file))))
        {
            printWriter.println("<?xml version=\"1.0\" encoding=\"UTF-8\"?>");
            printWriter.println("<contactList>");
            for (Contact contact : contactList.getContacts())
            {
                printWriter.println("  <contact>");
                printWriter.println("    <name>" + contact.getName() + "</name>");
                printWriter.println("    <surname>" + contact.getSurname() + "</surname>");
                printWriter.println("    <phone>" + contact.getPhoneNumber() + "</phone>");
                printWriter.println("    <email>" + contact.getEmail() + "</email>");
                printWriter.println("    <description>" + contact.getDescription() + "</description>");
                printWriter.println("  </contact>");
            }
            printWriter.println("</contactList>");
        }
        catch (IOException e)
        {
            System.err.println("Error while writing XML file: " + e.getMessage());
        }
    }

    public static boolean fileExists(File file)
    {
        if (file == null)
            return false;
        return file.exists() && file.isFile() && file.canRead() && file.getName().endsWith(".xml");
    }
}
