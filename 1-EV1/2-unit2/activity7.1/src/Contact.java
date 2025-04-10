import java.io.Serializable;

public class Contact implements Serializable
{
    private String name;
    private String surname;
    private String email;
    private String number;
    private String description;

    public Contact()
    {
        this("", "", "", "", "");
    }

    public Contact(String name, String surname, String email,
                   String number, String description)
    {
        this.name = name;
        this.surname = surname;
        this.email = email;
        this.number = number;
        this.description = description;
    }

    public String getName()
    {
        return name;
    }

    public void setName(String name)
    {
        this.name = name;
    }

    public String getSurname()
    {
        return surname;
    }

    public void setSurname(String surname)
    {
        this.surname = surname;
    }

    public String getEmail()
    {
        return email;
    }

    public void setEmail(String email)
    {
        this.email = email;
    }

    public String getNumber()
    {
        return number;
    }

    public void setNumber(String number)
    {
        this.number = number;
    }

    public String getDescription()
    {
        return description;
    }

    public void setDescription(String description)
    {
        this.description = description;
    }

    public boolean isValid()
    {
        return isValid(name, surname, email, number, description);
    }
    public static boolean isValid(Contact contact)
    {
        return isValid(contact.name, contact.surname, contact.email, contact.number, contact.description);
    }

    public static boolean isValid(String name, String surname, String email,
                                  String number, String description)
    {
        if (name == null || name.isEmpty() || surname == null || surname.isEmpty() ||
                email == null || email.isEmpty() || number == null || number.isEmpty())
            return false;
        String emailRegex = "^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+$";         // Regex to validate email
        if (!email.matches(emailRegex))
            return false;
        return true;
    }

    public Contact clone()
    {
        return new Contact(name, surname, email, number, description);
    }
}
