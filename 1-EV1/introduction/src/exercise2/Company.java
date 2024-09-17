package exercise2;

import java.util.HashSet;

public class Company
{
    private String name;
    private HashSet<Worker> workers = new HashSet<Worker>();
    private HashSet<Client> clients = new HashSet<Client>();

    public String getName()
    {
        return name;
    }

    public void setName(String value)
    {
        name = value;
    }

    public int getTotalClients()
    {
        return clients.size();
    }

    public int getTotalWorkers()
    {
        return workers.size();
    }

}
