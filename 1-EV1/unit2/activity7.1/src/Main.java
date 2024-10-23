import java.util.Scanner;

public class Main {

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        ContactList contactList = new ContactList();
        boolean exit = false;

        while (!exit) {
            System.out.println("\n--- Contact List Menu ---");
            System.out.println("1. Add new contact");
            System.out.println("2. View all contacts");
            System.out.println("3. Search contact by full name");
            System.out.println("4. Search contact by phone number");
            System.out.println("5. Remove a contact");
            System.out.println("6. Save contacts");
            System.out.println("7. Load contacts");
            System.out.println("0. Exit");
            System.out.print("Choose an option: ");
            int choice = scanner.nextInt();
            scanner.nextLine();  // Consume the newline

            switch (choice) {
                case 1: // Add new contact
                    System.out.print("Enter contact's first name: ");
                    String name = scanner.nextLine();
                    System.out.print("Enter contact's last name: ");
                    String surname = scanner.nextLine();
                    System.out.print("Enter contact's phone number: ");
                    String number = scanner.nextLine();
                    System.out.print("Enter contact's email: ");
                    String email = scanner.nextLine();
                    System.out.print("Enter contact's description: ");
                    String description = scanner.nextLine();

                    Contact newContact = new Contact(name, surname, email, number, description);
                    if (contactList.add(newContact)) {
                        System.out.println("Contact added successfully!");
                    } else {
                        System.out.println("Failed to add contact. Make sure the contact details are valid.");
                    }
                    break;

                case 2: // View all contacts
                    System.out.println("\n--- Contact List ---");
                    for (int i = 0; i < contactList.count(); i++) {
                        Contact contact = contactList.getAt(i);
                        System.out.println((i + 1) + ". " + contact.getName() + " " + contact.getSurname() + " - " +
                                contact.getNumber() + " - " + contact.getEmail());
                    }
                    if (contactList.count() == 0) {
                        System.out.println("No contacts found.");
                    }
                    break;

                case 3: // Search by full name
                    System.out.print("Enter contact's first name: ");
                    String searchName = scanner.nextLine();
                    System.out.print("Enter contact's last name: ");
                    String searchSurname = scanner.nextLine();
                    var foundByName = contactList.filterByFullName(searchName, searchSurname);
                    if (foundByName.isEmpty()) {
                        System.out.println("No contacts found with the name " + searchName + " " + searchSurname);
                    } else {
                        for (Contact contact : foundByName) {
                            System.out.println(contact.getName() + " " + contact.getSurname() + " - " +
                                    contact.getNumber() + " - " + contact.getEmail());
                        }
                    }
                    break;

                case 4: // Search by phone number
                    System.out.print("Enter contact's phone number: ");
                    String searchNumber = scanner.nextLine();
                    var foundByNumber = contactList.filterByNumber(searchNumber);
                    if (foundByNumber.isEmpty()) {
                        System.out.println("No contacts found with the number " + searchNumber);
                    } else {
                        for (Contact contact : foundByNumber) {
                            System.out.println(contact.getName() + " " + contact.getSurname() + " - " +
                                    contact.getNumber() + " - " + contact.getEmail());
                        }
                    }
                    break;

                case 5: // Remove a contact

                    System.out.print("1. Remove with Email");
                    System.out.print("2. Remove with Full Name");
                    String removeEmail = scanner.nextLine();
                    /*contactList.remove(new Contact("", "", ""));
                    if (contactList.contains(contactToRemove)) {
                        contactList.remove(contactToRemove);
                        System.out.println("Contact removed successfully.");
                    } else {
                        System.out.println("No contact found with email: " + removeEmail);
                    }
                    */
                    break;

                case 6: // Save contacts
                    contactList.save();
                    System.out.println("Contacts saved successfully.");
                    break;

                case 7: // Load contacts
                    contactList.load();
                    System.out.println("Contacts loaded successfully.");
                    break;

                case 0: // Exit
                    exit = true;
                    System.out.println("Exiting...");
                    break;

                default:
                    System.out.println("Invalid option. Please try again.");
            }
        }

        scanner.close();
    }
}
