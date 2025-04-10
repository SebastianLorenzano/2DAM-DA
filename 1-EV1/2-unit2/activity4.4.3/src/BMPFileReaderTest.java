import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import static org.junit.jupiter.api.Assertions.*;


public class BMPFileReaderTest {

    @ParameterizedTest
    @CsvSource({"1.bmp,818058,640,426, 24", "2.bmp,3275658,1280,853,24"})
    public void testValidResults(String filePath, long size, long width, long height, long bitsNum)
    {
        // Assuming a valid BMP file exists at this path
        BMPFileReader.Result result = BMPFileReader.run(filePath);
        assertEquals(size, result.size());
        assertEquals(width, result.width());
        assertEquals(height, result.height());
        assertEquals(bitsNum, result.bitsNum());


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
