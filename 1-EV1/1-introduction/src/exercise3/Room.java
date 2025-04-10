package exercise3;

import java.util.HashSet;

public class Room
{
    private String name;
    private Museum museum;
    private HashSet<Artwork> artworks = new HashSet<>();

    public String getName()
    {
        return name;
    }

    public void setName(String name)
    {
        this.name = name;
    }

    public Museum getMuseum()
    {
        return museum;
    }

    public void setMuseum(Museum museum)
    {
        if (!museum.containsRoom(this))
            museum.AddRoom(this);
        else
        {
            if (this.museum != null)
            {
                Museum aux = this.museum;
                this.museum = null;
                aux.RemoveRoom(this);
            }
            this.museum = museum;
        }
    }

    public void AddArtwork(Artwork artwork) throws IllegalArgumentException
    {
        if (artwork == null)
            throw new IllegalArgumentException(Utils.ARTWORK_NOT_VALID);
        artworks.add(artwork);
        artwork.setRoom(this);

    }

    public void RemoveArtwork(Artwork artwork)
    {
        artworks.remove(artwork);
        artwork.setRoom(null);
    }

    public boolean containsArtwork(Artwork artwork)
    {
        return artworks.contains(artwork);
    }
}
