public class Utils
{
    public enum FileType
    {
        BMP,
        GIF,
        ICO,
        JPEG,
        PNG,
        UNKNOWN,
    }
    final static byte[] GIF_HEADER1 = { (byte) 0x47, (byte) 0x49, (byte) 0x46, (byte) 0x38, (byte) 0x39, (byte) 0x61 };
    final static byte[] GIF_HEADER2 = { (byte) 0x47, (byte) 0x49, (byte) 0x46, (byte) 0x38, (byte) 0x37, (byte) 0x61 };
    final static byte[] BMP_HEADER = { (byte) 0x47, (byte) 0x4D };
    final static byte[] ICO_HEADER = { (byte) 0x00, (byte) 0x00, (byte) 0x01, (byte) 0x00 };
    final static byte[] JPEG_HEADER = { (byte) 0xFF, (byte) 0xD8, (byte) 0xFF };
    final static byte[] PNG_HEADER = { (byte) 0x89, (byte) 0x50, (byte) 0x4E, (byte) 0x47 };
}
