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
    final static int BITSNUMBYTES_SIZE = 2;

    public record Result(long size, long width, long height, long bitsNum)
    {
        public void printInfo()
        {
            System.out.println("Size: " + size);
            System.out.println("Width: " + width);
            System.out.println("Height: " + height);
            System.out.println("BitsNum: " + bitsNum);
        }
    }

    public static void main(String[] args)
    {
        run("1.bmp").printInfo();
    }

    public static Result run(String filePath)
    {
        FileInputStream fIn = null;
        if (ImageTypeReader.run(filePath) != Utils.FileType.BMP)
        {
            System.err.println("Format type is not supported.");
            return null;
        }

        try
        {
            fIn = new FileInputStream(filePath);
            System.out.println("Checkpoint 1.");
            long size = readLittleEndian(readBytesFromFile(SIZEBYTES_OFFSET, SIZEBYTES_SIZE, fIn), SIZEBYTES_SIZE);
            System.out.println("Checkpoint 2.");
            long width = readLittleEndian(readBytesFromFile(WIDTHBYTES_OFFSET, WIDTHBYTES_SIZE, fIn), WIDTHBYTES_SIZE);
            System.out.println("Checkpoint 3.");
            long height = readLittleEndian(readBytesFromFile(HEIGHTBYTES_OFFSET, HEIGHTBYTES_SIZE, fIn), HEIGHTBYTES_SIZE);
            System.out.println("Checkpoint 4.");
            long bitsNum = readLittleEndian(readBytesFromFile(BITSNUMBYTES_OFFSET, BITSNUMBYTES_SIZE, fIn), BITSNUMBYTES_SIZE);
            System.out.println("Checkpoint 5.");
            return new Result(size, width, height, bitsNum);
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

    private static byte[] readBytesFromFile(int offset, int size, FileInputStream file) throws IOException
    {
        file.skip(offset);
        byte[] result = new byte[size];
        file.read(result, 0, size);
        return result;
    }

    private static long readLittleEndian(byte[] b, int size)
    {
        if (b == null)
            return -1;
        if (size == 4)
        {
            return (Byte.toUnsignedInt(b[0]) +
                    256L * (Byte.toUnsignedInt(b[1]) +
                            256L * (Byte.toUnsignedInt(b[2]) +
                                    256L * Byte.toUnsignedInt(b[3]))));

        }
        else
            return (Byte.toUnsignedInt(b[0]) + 256 * (Byte.toUnsignedInt(b[1])));
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