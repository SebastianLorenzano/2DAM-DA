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
            {
                if (!outputFile.exists())
                    outputFile.createNewFile();
                outStream.writeObject(contactList);
            }
        }
        catch (FileNotFoundException e)
        {
            throw new RuntimeException(e);
        }
        catch (IOException e)
        {
            throw new RuntimeException(e);
        }
    }

    public static ContactList Deserialize(String filePath)
    {
        try (FileInputStream fileIn = new FileInputStream(filePath);
             ObjectInputStream inStream = new ObjectInputStream(fileIn)) {

            // Step 3: Read the object from the file
            return (ContactList)inStream.readObject();

        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
            System.err.println("Unable to found a Contact List. Creating one");
            return new ContactList();
        }


    }
}