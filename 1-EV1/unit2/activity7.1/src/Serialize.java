import java.io.*;

public interface Serialize
{

        File outputFile = new File("contacts.obj");

        try (ObjectOutputStream objectsFile = new ObjectOutputStream(
                new FileOutputStream(outputFile)))
        {
            if (!outputFile.exists())
                outputFile.createNewFile();


        }
    }
}
