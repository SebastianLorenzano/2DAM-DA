package exercise3;

import java.util.HashSet;

public class Author
{
    private String name;
    private String nacionality;
    private HashSet<Artwork> artworks = new HashSet<>();

    public String getName()
    {
        return name;
    }

    public void setName(String name)
    {
        this.name = name;
    }

    public String getNacionality()
    {
        return nacionality;
    }

    public void setNacionality(String nacionality)
    {
        this.nacionality = nacionality;
    }

    public int getArworksCount()
    {
        return artworks.size();
    }

    public void AddArtwork(Artwork artwork) throws IllegalArgumentException
    {
        if (artwork == null)
            throw new IllegalArgumentException(Utils.ARTWORK_NOT_VALID);
        artworks.add(artwork);
        artwork.setAuthor(this);

    }

    public void RemoveArtwork(Artwork artwork)
    {
        artworks.remove(artwork);
        artwork.setAuthor(null);
    }

    public boolean containsArtwork(Artwork artwork)
    {
        return artworks.contains(artwork);
    }
}
