import org.xml.sax.Attributes;
import org.xml.sax.helpers.DefaultHandler;

public class ContactListHandler extends DefaultHandler
{
    private ContactList contactList;
    private Contact currentContact;
    private StringBuilder content;

    @Override
    public void startElement(String uri, String localName, String qName, Attributes attributes)
    {
        content = new StringBuilder();
        if (qName.equals("contactList"))
        {
            contactList = new ContactList();
        }
        else if (qName.equals("contact"))
        {
            currentContact = new Contact();
        }
    }

    @Override
    public void characters(char[] ch, int start, int length)
    {
        content.append(ch, start, length);
    }

    @Override
    public void endElement(String uri, String localName, String qName)
    {
        if (currentContact != null)
        {
            switch (qName)
            {
                case "name":
                    currentContact.setName(content.toString());
                    break;
                case "surname":
                    currentContact.setSurname(content.toString());
                    break;
                case "phone":
                    currentContact.setPhoneNumber(content.toString());
                    break;
                case "email":
                    currentContact.setEmail(content.toString());
                    break;
                case "description":
                    currentContact.setDescription(content.toString());
                    break;
                case "contact":
                    contactList.addContact(currentContact);
                    currentContact = null;
                    break;
            }
        }
    }

    public ContactList getContactList()
    {
        return contactList;
    }
}