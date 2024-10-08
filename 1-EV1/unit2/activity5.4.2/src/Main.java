import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class Main
{
    public static void main(String[] args)
    {
        main("file1.txt", "file2.txt", "output_sorted.txt");

    }

    public static void main(String path1, String path2, String output)
    {
        File file1 = new File(path1);
        File file2 = new File(path2);
        File outputFile = new File(output);
        try(PrintWriter writer = new PrintWriter(new BufferedWriter(new FileWriter(outputFile,false)));
                BufferedReader reader1 = new BufferedReader(new FileReader(file1));
                BufferedReader reader2 = new BufferedReader(new FileReader(file2)))
        {
            if (!outputFile.exists())
                outputFile.createNewFile();
            String line1 = reader1.readLine();
            String line2 = reader2.readLine();
            while (line1 != null || line2 != null)
            {
                if (line2 == null || line1 != null && line1.compareTo(line2) < 0)
                {
                    writer.println(line1);
                    line1 = reader1.readLine();
                }
                else if (line2 != null)  // if line1.compareTo(line2) > 0 is implied
                {
                    writer.println(line2);
                    line2 = reader2.readLine();
                }
            }
        }
        catch (FileNotFoundException e)
        {
            System.err.println(e.getMessage());
        }
        catch (IOException e)
        {
            e.printStackTrace();
        }
    }
}