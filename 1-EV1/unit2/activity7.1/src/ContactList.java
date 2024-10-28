import java.io.Serializable;
import java.util.ArrayList;
import java.util.function.Predicate;

public class ContactList implements Serializable {
    private final static String PATH = "contactList.obj";
    private ArrayList<Contact> contacts = new ArrayList<Contact>();

    public ContactList()
    {

    }

    public ContactList(ContactList contactList)
    {
        contacts = filter(contact -> true);
    }
    public int count() {
        return contacts.size();
    }

    public Contact getAt(int index) {
        return index >= 0 && index < contacts.size() ? contacts.get(index).clone() : null;
    }

    public boolean add(Contact contact) {
        if (contact != null && contact.isValid() &&
                filter(contact1 -> contact1.getEmail().equals(contact.getEmail())).isEmpty()) {
            contacts.add(contact.clone());
            return true;
        }
        return false;
    }

    public boolean contains(Contact contact) {
        return indexOf(contact) >= 0;
    }

    public int indexOf(Contact contact) {
        if (contact == null)
            return -1;
        for (int x = 0; x < contacts.size(); x++) {
            Contact c = contacts.get(x);
            if (contact.getEmail() != "")
                if (contact.getEmail().equals(c.getEmail()))
                    return x;
            else if ((c.getName() + c.getSurname()).contains((contact.getName() + contact.getSurname())))
                return x;
        }
        return -1;
    }

    public ArrayList<Contact> filter(Predicate<Contact> predicate) {
        ArrayList<Contact> result = new ArrayList<>();
        for (Contact contact : contacts)
            if (predicate.test(contact))
                result.add(contact.clone());
        return result;
    }

    public ArrayList<Contact> filterByFullName(String name, String surname) {
        return filter(contact -> (contact.getName() + contact.getSurname().toLowerCase())
                .contains((name + surname).toLowerCase()));
    }

    public ArrayList<Contact> filterByNumber(String number) {
        return filter(contact -> contact.getNumber().contains(number));
    }

    private void remove(int index) {
        if (index >= 0 && index < contacts.size())
            contacts.remove(index);
    }

    public void remove(Contact contact) {
        remove(indexOf(contact));
    }

    public void save() {
        ContactListParser.Serialize(this, PATH);
    }

    public void load()
    {
        contacts = ContactListParser.Deserialize(PATH).contacts;
    }

    public ContactList clone()
    {
        return new ContactList(this);
    }


}
