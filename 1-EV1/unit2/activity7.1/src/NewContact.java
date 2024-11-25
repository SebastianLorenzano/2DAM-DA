import java.util.ArrayList;

public class NewContact {
private String name;
private String surname;
private ArrayList<String> emails = new ArrayList<>();
private ArrayList<String> numbers = new ArrayList<>();
private String description;

// Constructor
public NewContact() {
    // Default constructor
}

public NewContact(String name, String surname, ArrayList<String> emails,
               ArrayList<String> numbers, String description) {
    this.name = name;
    this.surname = surname;
    this.emails = emails;
    this.numbers = numbers;
    this.description = description;
}

// Getters and Setters
public String getName() {
    return name;
}

public void setName(String name) {
    this.name = name;
}

public String getSurname() {
    return surname;
}

public void setSurname(String surname) {
    this.surname = surname;
}

public ArrayList<String> getEmails() {
    return emails;
}

public void setEmails(ArrayList<String> emails) {
    this.emails = emails;
}

public void addEmail(String email) {
    emails.add(email);
}

public void addNumber(String number) {
    numbers.add(number);
}

public ArrayList<String> getNumbers() {
    return numbers;
}

public void setNumbers(ArrayList<String> numbers) {
    this.numbers = numbers;
}

public String getDescription() {
    return description;
}

public void setDescription(String description) {
    this.description = description;
}

// Validation Methods
public boolean isValid() {
    return isValid(name, surname, emails, numbers, description);
}

public static boolean isValid(NewContact contact) {
    return isValid(contact.name, contact.surname, contact.emails, contact.numbers, contact.description);
}

public static boolean isValid(String name, String surname, ArrayList<String> emails,
                              ArrayList<String> numbers, String description) {
    if (name == null || name.isEmpty() || surname == null || surname.isEmpty()) {
        return false;
    }
    if (emails == null || emails.isEmpty() || numbers == null || numbers.isEmpty()) {
        return false;
    }
    String emailRegex = "^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+$"; // Regex to validate email
    for (String email : emails) {
        if (!email.matches(emailRegex)) {
            return false;
        }
    }
    return true;
}

// Cloning Method
public NewContact clone() {
    return new NewContact(name, surname, new ArrayList<>(emails), new ArrayList<>(numbers), description);
}
}
