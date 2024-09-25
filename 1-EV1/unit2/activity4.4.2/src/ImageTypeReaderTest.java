import org.junit.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;


public class ImageTypeReaderTest
{
    @Test
    public void testGetFiletypeGIF() {
        // Test GIF header
        Utils.FileType result = ImageTypeReader.run("1.gif");
        assertEquals(Utils.FileType.GIF, result, "Expected GIF file type for first GIF header");
        result = ImageTypeReader.run("2.gif");
        assertEquals(Utils.FileType.GIF, result, "Expected GIF file type for second GIF header");
    }

    @Test
    public void testGetFiletypeBMP() {
        // Test BMP header
        Utils.FileType result = ImageTypeReader.run("1.bmp");
        assertEquals(Utils.FileType.BMP, result, "Expected BMP file type for BMP header");
    }

    @Test
    public void testGetFiletypeICO() {
        // Test ICO header
        Utils.FileType result = ImageTypeReader.run("1.ico");
        assertEquals(Utils.FileType.ICO, result, "Expected ICO file type for ICO header");
    }

    @Test
    public void testGetFiletypeJPEG() {
        // Test JPEG header
        Utils.FileType result = ImageTypeReader.run("1.jpeg");
        assertEquals(Utils.FileType.JPEG, result, "Expected JPEG file type for JPEG header");
    }

    @Test
    public void testGetFiletypePNG() {
        // Test PNG header
        Utils.FileType result = ImageTypeReader.run("1.png");
        assertEquals(Utils.FileType.PNG, result, "Expected PNG file type for PNG header");
    }

    @Test
    public void testGetFiletypeUnknown() {
        // Test unknown header
        Utils.FileType result = ImageTypeReader.run("1.txt");
        assertEquals(Utils.FileType.UNKNOWN, result, "Expected UNKNOWN file type for UNKNOWN header");
    }
}

