import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;

public class MyNotepad
{
    public static void main(String path)
    {
        PrintWriter printWriter = null;
        try {
            printWriter = new PrintWriter(new BufferedWriter(
                    new FileWriter(path,true )));
            printWriter.

        }
        catch (IOException e) {
            e.printStackTrace();
        }
        finally {
            if ( printWriter != null ) {
                printWriter.close();
            }
        }
    }
}