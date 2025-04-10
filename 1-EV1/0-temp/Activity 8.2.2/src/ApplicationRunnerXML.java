import java.io.File;

public class ApplicationRunnerXML
{
    public static void run()
    {
        File xmlSaveFile = new File(XMLUtils.XML_FILE);
        File objectSaveFile = new File(Utils.FILE_NAME);
        var xmlExists = XMLUtils.fileExists(xmlSaveFile);
        var objectExists = Serializer.fileExists(objectSaveFile);
        boolean isXML;
        ContactList contactList;
        if (xmlExists)
        {
            contactList = XMLUtils.readXMLContactList(xmlSaveFile);
            if (contactList == null)
            {
                System.err.println("Error loading contact list from XML");
                return;
            }
            System.out.println("Contact list loaded from XML");
            isXML = true;
        }
        else if (objectExists)
        {
            contactList = Serializer.deserialize(objectSaveFile);
            if (contactList == null)
            {
                System.err.println("Error loading contact list from object");
                return;
            }
            System.out.println("Contact list loaded from object");
            isXML = false;
        }
        else
        {
            contactList = new ContactList();
            System.out.println("New contact list created");
            isXML = false;
        }
        XMLMenu menu = new XMLMenu(contactList, isXML, xmlSaveFile, objectSaveFile);
        menu.run();

        if (menu.isXML())
        {
            XMLUtils.writeXMLContactList(xmlSaveFile, contactList);
            System.out.println("Contact list saved to XML");
        }
        else
        {
            Serializer.serialize(objectSaveFile, contactList);
            System.out.println("Contact list saved to object");
        }
    }
}
