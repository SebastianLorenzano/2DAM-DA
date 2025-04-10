package exercise3;

public class Sculpture extends Artwork
{
    private PaintType type;
    private String format;


    public PaintType getType()
    {
        return type;
    }

    public void setType(PaintType value)
    {
        type = value;
    }

    public String getFormat()
    {
        return format;
    }

    public void setFormat(String value)
    {
        format = value;
    }
}
