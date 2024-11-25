import java.io.*;

public class ContactListParser
{
    public static void Serialize(ContactList contactList, String path)
    {
        if (path == null || contactList == null)
            throw new IllegalArgumentException();
        File outputFile = new File(path);

        try (FileOutputStream file = new FileOutputStream(outputFile);
             ObjectOutputStream outStream = new ObjectOutputStream(file))
        {
            outStream.writeObject(contactList);
        }
        catch (IOException e)
        {
            throw new RuntimeException("Error occurred during serialization", e);
        }
    }

    public static ContactList Deserialize(String filePath)
    {
        try (FileInputStream fileIn = new FileInputStream(filePath);
             ObjectInputStream inStream = new ObjectInputStream(fileIn)) {

            return (ContactList)inStream.readObject();

        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
            System.err.println("Unable to found a Contact List. Creating one");
            return new ContactList();
        }
    }
}