package exercise3;

import java.util.HashSet;

public class Museum
{
    private String name;
    private String direction;
    private String city;
    private String country;
    private HashSet<Room> rooms = new HashSet<>();

    public String getName()
    {
        return name;
    }
    public void setName(String value)
    {
        name = value;
    }

    public String getDirection()
    {
        return direction;
    }
    public void setDirection(String value)
    {
        direction = value;
    }

    public String getCity()
    {
        return city;
    }
    public void setCity(String value)
    {
        city = value;
    }

    public String getCountry()
    {
        return country;
    }

    public void setCountry(String value)
    {
        country = value;
    }

    public void AddRoom(Room room) throws IllegalArgumentException
    {
        if (room == null)
            throw new IllegalArgumentException(Utils.ROOM_NOT_VALID);
        rooms.add(room);
        room.setMuseum(this);
    }

    public void RemoveRoom(Room room)
    {
        rooms.remove(room);
        if (room != null)
            room.setMuseum(null);
    }

    public boolean containsRoom(Room room)
    {
        return rooms.contains(room);
    }
}
