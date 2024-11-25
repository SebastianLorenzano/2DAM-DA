import java.io.File;
import java.util.InputMismatchException;
import java.util.Scanner;

public class XMLMenu extends Menu
{
    public XMLMenu(ContactList contactList, boolean isXML, File xmlSaveFile, File objectSaveFile)
    {
        super(contactList);
        this.isXML = isXML;
        this.xmlSaveFile = xmlSaveFile;
        this.objectSaveFile = objectSaveFile;
    }

    private boolean isXML;
    private File xmlSaveFile;
    private File objectSaveFile;

    @Override
    public void run()
    {
        Scanner scanner = new Scanner(System.in);

        boolean exit = false;
        while (!exit)
        {
            showOptions();
            int option;
            try
            {
                option = scanner.nextInt();
                scanner.nextLine();
            }
            catch (InputMismatchException e)
            {
                System.out.println("Invalid input. Please enter a number.");
                scanner.nextLine();
                continue;
            }
            switch (option)
            {
                case 1:
                    addContact(scanner);
                    pressEnterToContinue(scanner);
                    break;
                case 2:
                    removeContact(scanner);
                    pressEnterToContinue(scanner);
                    break;
                case 3:
                    showContacts();
                    pressEnterToContinue(scanner);
                    break;
                case 4:
                    searchContact(scanner);
                    pressEnterToContinue(scanner);
                    break;
                case 5:
                    switchToXML();
                    pressEnterToContinue(scanner);
                    break;
                case 6:
                    switchToObject();
                    pressEnterToContinue(scanner);
                    break;
                case 7:
                    exit = true;
                    break;
                default:
                    System.out.println("Invalid option");
                    break;
            }
        }
        scanner.close();
    }

    private void switchToObject()
    {
        if (!isXML)
        {
            System.out.println("Already in object mode");
            return;
        }
        Serializer.serialize(objectSaveFile, contactList);
        System.out.println("Switched to object mode");
        isXML = false;
        if (xmlSaveFile.delete())
            System.out.println("Deleted XML file");
        else
            System.err.println("Error deleting XML file: " + xmlSaveFile.getAbsolutePath());
    }

    private void switchToXML()
    {
        if (isXML)
        {
            System.out.println("Already in XML mode");
            return;
        }
        XMLUtils.writeXMLContactList(xmlSaveFile, contactList);
        System.out.println("Switched to XML mode");
        isXML = true;
        if (objectSaveFile.delete())
            System.out.println("Deleted object file");
        else
            System.err.println("Error deleting object file: " + objectSaveFile.getAbsolutePath());
    }

    @Override
    protected void showOptions()
    {
        System.out.println("1. Add contact");
        System.out.println("2. Remove contact");
        System.out.println("3. Show contacts");
        System.out.println("4. Search contact");
        System.out.println("5. To XML");
        System.out.println("6. To object");
        System.out.println("7. Exit");
        System.out.println("Choose an option:");
    }

    public boolean isXML()
    {
        return isXML;
    }
}
