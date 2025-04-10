package exercise2;

import java.util.HashSet;

public class Directive extends Worker
{
    private String category;
    private HashSet<Worker> subordinates = new HashSet<>();

    public String getCategory()
    {
        return category;
    }

    public void setCategory(String value)
    {
        category = value;
    }

    public int getSubordinatesCount()
    {
        return subordinates.size();
    }

}
