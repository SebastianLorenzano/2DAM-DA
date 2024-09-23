import java.io.Closeable;
import java.io.FileInputStream;
import java.io.IOException;

public class Main
{
    final static int BYTES_LENGTH = 6;

    public static void main(String[] args)
    {
        FileInputStream fIn = null;
        String fileToRead = "1.bmp";

        try
        {
            fIn = new FileInputStream(fileToRead);
            byte[] bytes = new byte[BYTES_LENGTH];
            fIn.read(bytes, 0, BYTES_LENGTH);
            Utils.FileType result = getFiletype(bytes);
            System.out.println("The type of the file is " + result.name());
        }
        catch (Exception e)
        {
            System.err.println(e.toString());
        }
        finally
        {
            closeStream(fIn);
        }
    }

    private static Utils.FileType getFiletype(byte[] a)
    {
        if (compareBytes(a, Utils.GIF_IDENTIFIER1) || compareBytes(a, Utils.GIF_IDENTIFIER2))
            return Utils.FileType.GIF;
        if (compareBytes(a, Utils.BMP_IDENTIFIER))
            return Utils.FileType.BMP;
        if (compareBytes(a, Utils.ICO_IDENTIFIER))
            return Utils.FileType.ICO;
        if (compareBytes(a, Utils.JPEG_IDENTIFIER))
            return Utils.FileType.JPEG;
        if (compareBytes(a, Utils.PNG_IDENTIFIER))
            return Utils.FileType.PNG;
        return Utils.FileType.UNKNOWN;
    }
    private static boolean compareBytes(byte[] a, byte[] b)
    {
        if (a == null || b == null)
            return false;

        for (int x = 0; x < b.length; x++)
        {
            System.out.println(a[x] + " | " + b[x]);
            if (a[x] != b[x])
                return false;
        }
        return true;
    }

    public static void closeStream(Closeable s){
        try
        {
            if(s!=null)
                s.close();
        }
        catch(IOException e)
        {
            System.err.println(e.getMessage());
        }
    }


}