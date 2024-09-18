package exercise3;

public class Artwork
{
    private String title;
    private Room room;
    private Author author;

    public String getTitle()
    {
        return title;
    }

    public void setTitle(String value)
    {
        title = value;
    }

    public Room getRoom()
    {
        return room;
    }

    public Author getAuthor()
    {
        return author;
    }

    public void setRoom(Room room)
    {
        if (!room.containsArtwork(this))
            room.AddArtwork(this);
        else
        {
            if (this.room != null)
            {
                Room aux = this.room;
                this.room = null;
                aux.RemoveArtwork(this);
            }
            this.room = room;
        }

    }

    public void setAuthor(Author author)
    {
        if (this.author != null)
        {
            Author aux = this.author;
            this.author = null;
            aux.RemoveArtwork(this);
        }
        this.author = author;

    }
}


