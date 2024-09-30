import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.File;
import java.util.Scanner;

public class MyNotepad
{
    public static void main(String[] args)
    {
        main("here.txt");
    }
    public static void main(String path)
    {
        File file = new File(path);
        boolean append = true;
        Scanner scanner = new Scanner(System.in);
        System.out.println("Welcome to MyNotepad!\nDo you want to append into the file or overwrite it?" +
                "\nWrite 'Y' for YES and 'N' for NO");
        String line = scanner.nextLine();
        if (line.equalsIgnoreCase("n"))
            append = false;
        else if (!line.equalsIgnoreCase("y"))
        {
            System.out.println("Incorrect Option, try again.");
            clearConsole();
            main(path);
        }

        try(PrintWriter writer = new PrintWriter(new BufferedWriter(new FileWriter(file,append))))
        {
            if (!file.exists())
                if (file.createNewFile()) throw new IllegalArgumentException();
            String exitString = ":qa";
            System.out.println("To exit, write " + exitString + "\n File: " + path +
                    "\n-------------------------------------------------------------------------");
            for (boolean isRunning = true; isRunning;)
            {
                line = scanner.nextLine();
                if (!line.equals(exitString))
                    writer.println(line);
                else
                    isRunning = false;
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
    private static void clearConsole()
    {
        // Print ANSI escape code to clear the console
        System.out.print("\033[H\033[2J");
        System.out.flush();
    }
}
