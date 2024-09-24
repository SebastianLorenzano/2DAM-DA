import java.io.Closeable;
import java.io.FileInputStream;
import java.io.IOException;

public class BMPFileReader
{
    final static int SIZEBYTES_OFFSET = 2;
    final static int SIZEBYTES_SIZE = 4;
    final static int WIDTHBYTES_OFFSET = 18;
    final static int WIDTHBYTES_SIZE = 4;
    final static int HEIGHTBYTES_OFFSET = 22;
    final static int HEIGHTBYTES_SIZE = 4;
    final static int BITSNUMBYTES_OFFSET = 28;
    final static int BITSNUMBYTES_SIZE = 4;



    public static void main(String[] args)
    {
        FileInputStream fIn = null;
        String filePath = "1.bmp";

        try
        {
            fIn = new FileInputStream(filePath);
            byte[] sizeBytes = new byte[SIZEBYTES_SIZE];
            fIn.read(sizeBytes, SIZEBYTES_OFFSET, SIZEBYTES_SIZE);


            byte[] widthBytes = new byte[SIZEBYTES_SIZE];
            fIn.read(sizeBytes, WIDTHBYTES_OFFSET, WIDTHBYTES_SIZE);

            byte[] heightBytes = new byte[SIZEBYTES_SIZE];
            fIn.read(sizeBytes, HEIGHTBYTES_OFFSET, HEIGHTBYTES_SIZE);

            byte[] bitsNumBytes = new byte[SIZEBYTES_SIZE];
            fIn.read(sizeBytes, BITSNUMBYTES_OFFSET, BITSNUMBYTES_SIZE);

            long size = readLittleEndian(sizeBytes);
            long width = readLittleEndian(widthBytes);
            long height = readLittleEndian(heightBytes);
            long bitsNum = readLittleEndian(bitsNumBytes);
            System.out.println();


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


    private static long readLittleEndian(byte[] b)
    {

        if (b == null)
            return -1;
        long result = (b[0] + 256 * (b[1] + 256 * (b[2] + 256 * b[3])));

        return result;
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