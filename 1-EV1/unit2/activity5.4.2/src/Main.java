import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class Main
{
    public static void main(String[] args)
    {


    }

    public static void main(String path1, String path2)
    {
        File file1 = new File(path1);
        File file2 = new File(path2);
        try(PrintWriter writer = new PrintWriter(new BufferedWriter(new FileWriter(file1,false)));
                BufferedReader reader1 = new BufferedReader(new FileReader(file1));
                BufferedReader reader2 = new BufferedReader(new FileReader(file2)))
        {
            if (!file1.exists())
                if (file1.createNewFile()) throw new IllegalArgumentException();
            List<String> content1 = new ArrayList<>();
            List<String> content2 = new ArrayList<>();
            for (String line1 = reader1.readLine(); line1 != null;)
            {
                content1.add(line1);
                line1 = reader1.readLine();
            }
            for (String line2 = reader1.readLine(); line2 != null;)
            {
                content2.add(line2);
                line2 = reader1.readLine();
            }

            List<String> result = new ArrayList<>();
            while



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