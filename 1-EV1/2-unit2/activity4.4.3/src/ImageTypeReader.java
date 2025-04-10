import java.io.Closeable;
import java.io.FileInputStream;
import java.io.IOException;
public class ImageTypeReader
{
    final static int BYTES_LENGTH = 6;

    public static Utils.FileType run(String filePath)
    {
        FileInputStream fIn = null;

        try
        {
            fIn = new FileInputStream(filePath);
            byte[] bytes = new byte[BYTES_LENGTH];
            fIn.read(bytes, 0, BYTES_LENGTH);
            return getFiletype(bytes);
        }
        catch (Exception e)
        {
            System.err.println(e.toString());
        }
        finally
        {
            closeStream(fIn);
        }
        return null;
    }

    private static Utils.FileType getFiletype(byte[] a)
    {
        if (compareBytes(a, Utils.GIF_HEADER1) || compareBytes(a, Utils.GIF_HEADER2))
            return Utils.FileType.GIF;
        if (compareBytes(a, Utils.BMP_HEADER))
            return Utils.FileType.BMP;
        if (compareBytes(a, Utils.ICO_HEADER))
            return Utils.FileType.ICO;
        if (compareBytes(a, Utils.JPEG_HEADER))
            return Utils.FileType.JPEG;
        if (compareBytes(a, Utils.PNG_HEADER))
            return Utils.FileType.PNG;
        return Utils.FileType.UNKNOWN;
    }
    private static boolean compareBytes(byte[] a, byte[] b)
    {
        if (a == null || b == null)
            return false;

        for (int x = 0; x < b.length; x++)
        {
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