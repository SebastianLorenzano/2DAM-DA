import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import java.io.File;
import java.util.ArrayList;

public class NewContactListSAXHandler extends DefaultHandler {
    private ArrayList<NewContact> contactList;
    private NewContact currentContact;
    private StringBuilder tagContent;

    public NewContactListSAXHandler(ArrayList<NewContact> contactList) {
        this.contactList = contactList;
        this.tagContent = new StringBuilder();
    }

    @Override
    public void startElement(String uri, String localName, String qName, Attributes attributes) throws SAXException {
        tagContent.setLength(0);  // Clear the tagContent buffer at the start of an element

        if (qName.equalsIgnoreCase("contact")) {
            currentContact = new NewContact();  // Start a new contact
        }
    }

    @Override
    public void characters(char[] ch, int start, int length) throws SAXException {
        tagContent.append(ch, start, length); // Accumulate characters for the current tag
    }

    @Override
    public void endElement(String uri, String localName, String qName) throws SAXException {
        if (currentContact != null) {
            switch (qName.toLowerCase()) {
                case "name":
                    currentContact.setName(tagContent.toString().trim());
                    break;
                case "surname":
                    currentContact.setSurname(tagContent.toString().trim());
                    break;
                case "phone":
                    currentContact.addNumber(tagContent.toString().trim());
                    break;
                case "email":
                    currentContact.addEmail(tagContent.toString().trim());
                    break;
                case "description":
                    currentContact.setDescription(tagContent.toString().trim());
                    break;
                case "contact":
                    if (currentContact.isValid()) {
                        contactList.add(currentContact);
                    }
                    currentContact = null;  // Reset currentContact for the next contact
                    break;
            }
        }
        tagContent.setLength(0);  // Clear the tagContent buffer at the end of an element
    }

    public static void loadFromXML(String xmlFilePath, ArrayList<NewContact> contactList) {
        try {
            SAXParserFactory factory = SAXParserFactory.newInstance();
            SAXParser saxParser = factory.newSAXParser();
            NewContactListSAXHandler handler = new NewContactListSAXHandler(contactList);
            saxParser.parse(new File(xmlFilePath), handler);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void saveToXML(String xmlFilePath, ArrayList<NewContact> contactList) {
        try {
            DocumentBuilderFactory docFactory = DocumentBuilderFactory.newInstance();
            DocumentBuilder docBuilder = docFactory.newDocumentBuilder();

            // Root element
            Document doc = docBuilder.newDocument();
            Element rootElement = doc.createElement("contactList");
            doc.appendChild(rootElement);

            for (NewContact contact : contactList) {
                // Contact element
                Element contactElement = doc.createElement("contact");
                rootElement.appendChild(contactElement);

                // Name element
                Element name = doc.createElement("name");
                name.appendChild(doc.createTextNode(contact.getName()));
                contactElement.appendChild(name);

                // Surname element
                Element surname = doc.createElement("surname");
                surname.appendChild(doc.createTextNode(contact.getSurname()));
                contactElement.appendChild(surname);

                // Phone elements
                for (String number : contact.getNumbers()) {
                    Element phoneElement = doc.createElement("phone");
                    phoneElement.appendChild(doc.createTextNode(number));
                    contactElement.appendChild(phoneElement);
                }

                // Email elements
                for (String email : contact.getEmails()) {
                    Element emailElement = doc.createElement("email");
                    emailElement.appendChild(doc.createTextNode(email));
                    contactElement.appendChild(emailElement);
                }

                // Description element
                Element description = doc.createElement("description");
                description.appendChild(doc.createTextNode(contact.getDescription()));
                contactElement.appendChild(description);
            }

            // Write the content into XML file
            TransformerFactory transformerFactory = TransformerFactory.newInstance();
            Transformer transformer = transformerFactory.newTransformer();
            transformer.setOutputProperty(OutputKeys.INDENT, "yes");
            DOMSource source = new DOMSource(doc);
            StreamResult result = new StreamResult(new File(xmlFilePath));

            transformer.transform(source, result);

            System.out.println("File saved to " + xmlFilePath);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }




    public static void main(String[] args) {
        // Usage Example
        ArrayList<NewContact> contactList = new ArrayList<>();
        NewContactListSAXHandler.loadFromXML("contacts.xml", contactList);
        for (NewContact contact : contactList) {
            System.out.println("Name: " + contact.getName());
            System.out.println("Surname: " + contact.getSurname());
            System.out.println("Emails: " + contact.getEmails());
            System.out.println("Numbers: " + contact.getNumbers());
            System.out.println("Description: " + contact.getDescription());
            System.out.println("--------------------------");
        }
        NewContactListSAXHandler.saveToXML("new_contacts.xml", contactList);
    }
}
