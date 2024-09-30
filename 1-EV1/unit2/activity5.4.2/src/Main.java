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
        try(PrintWriter writer = new PrintWriter(new BufferedWriter(new FileWriter(file1,false))))
        {
            if (!file1.exists())
                if (file1.createNewFile()) throw new IllegalArgumentException();
            List<String> content1 = new ArrayList<>();
            List<String> content2 = new ArrayList<>();

            try (BufferedReader reader = new BufferedReader(new FileReader(file1)))
            {
                String line;
                while ((line = reader.readLine()) != null)
                    content1.add(line);
            }

            try (BufferedReader reader = new BufferedReader(new FileReader(file2)))
            {
                String line;
                while ((line = reader.readLine()) != null)
                    content2.add(line);
            }


        }
        catch (IOException e) {
            e.printStackTrace();
        }
        catch (IllegalArgumentException e)
        {
            System.err.println(e.getMessage());
        }
    }
}