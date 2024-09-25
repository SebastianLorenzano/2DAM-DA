import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import static org.junit.jupiter.api.Assertions.*;


public class BMPFileReaderTest {

    @ParameterizedTest
    @CsvSource({"1.bmp,500,500,500", "2.bmp,500,500,500"})
    public void testRunValidFile(String filePath, long size, long width, long height, long bitsNum)
    {
        // Assuming a valid BMP file exists at this path
        BMPFileReader.Result result = BMPFileReader.run("valid.bmp");
        assertNotNull(result, "Result should not be null for valid BMP file");
        assertTrue(result.size() > 0, "Size should be greater than 0");
        assertTrue(result.width() > 0, "Width should be greater than 0");
        assertTrue(result.height() > 0, "Height should be greater than 0");
        assertTrue(result.bitsNum() > 0, "BitsNum should be greater than 0");
    }

    @Test
    public void testRunWithUnsupportedFormat()
    {
        // Test with a file that is not a BMP (e.g., a .txt file)
        BMPFileReader.Result result = BMPFileReader.run("1.png");
        assertNull(result, "Result should be null for unsupported file format");
    }

    @Test
    public void testRunNonExistentFile()
    {
        BMPFileReader.Result result = BMPFileReader.run("non_existent_file.bmp");
        assertNull(result, "Result should be null for non-existent BMP file");
    }

}
